package fr.xephi.authme.listener;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.settings.properties.SecuritySettings;
import fr.xephi.authme.util.TeleportUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;
import java.lang.reflect.Method;


public class LoginLocationFixListener implements Listener {
    @Inject
    private AuthMe plugin;
    @Inject
    private Messages messages;
    @Inject
    private Settings settings;
    private final AuthMeApi authmeApi = AuthMeApi.getInstance();

    public LoginLocationFixListener() {
    }

    private static Material materialPortal = Material.matchMaterial("PORTAL");

    private static boolean isAvailable; // false: unchecked/method not available   true: method is available
    BlockFace[] faces = {BlockFace.WEST, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST};

    static {
        if (materialPortal == null) {
            materialPortal = Material.matchMaterial("PORTAL_BLOCK");
            if (materialPortal == null) {
                materialPortal = Material.matchMaterial("NETHER_PORTAL");
            }
        }
        try {
            Method getMinHeightMethod = World.class.getMethod("getMinHeight");
            isAvailable = true;
        } catch (NoSuchMethodException e) {
            isAvailable = false;
        }
    }

    private int getMinHeight(World world) {
        //This keeps compatibility of 1.16.x and lower
        if (isAvailable) {
            return world.getMinHeight();
        } else {
            return 0;
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location JoinLocation = player.getLocation();
        if (settings.getProperty(SecuritySettings.LOGIN_LOC_FIX_SUB_PORTAL)) {
            if (!JoinLocation.getBlock().getType().equals(materialPortal) && !JoinLocation.getBlock().getRelative(BlockFace.UP).getType().equals(materialPortal)) {
                return;
            }
            Block JoinBlock = JoinLocation.getBlock();
            boolean solved = false;
            for (BlockFace face : faces) {
                if (JoinBlock.getRelative(face).getType().equals(Material.AIR) && JoinBlock.getRelative(face).getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                    TeleportUtils.teleport(player, JoinBlock.getRelative(face).getLocation().add(0.5, 0.1, 0.5));
                    solved = true;
                    break;
                }
            }
            if (!solved) {
                JoinBlock.getRelative(BlockFace.UP).breakNaturally();
                JoinBlock.breakNaturally();
            }
            messages.send(player, MessageKey.LOCATION_FIX_PORTAL);
        }
        if (settings.getProperty(SecuritySettings.LOGIN_LOC_FIX_SUB_UNDERGROUND)) {
            Material UpType = JoinLocation.getBlock().getRelative(BlockFace.UP).getType();
            World world = player.getWorld();
            int MaxHeight = world.getMaxHeight();
            int MinHeight = getMinHeight(world);
            if (!UpType.isOccluding() && !UpType.equals(Material.LAVA)) {
                return;
            }
            for (int i = MinHeight; i <= MaxHeight; i++) {
                JoinLocation.setY(i);
                Block JoinBlock = JoinLocation.getBlock();
                if ((JoinBlock.getRelative(BlockFace.DOWN).getType().isBlock())
                    && JoinBlock.getType().equals(Material.AIR)
                    && JoinBlock.getRelative(BlockFace.UP).getType().equals(Material.AIR)) {
                    if (JoinBlock.getRelative(BlockFace.DOWN).getType().equals(Material.LAVA)) {
                        JoinBlock.getRelative(BlockFace.DOWN).setType(Material.DIRT);
                    }
                    TeleportUtils.teleport(player, JoinBlock.getLocation().add(0.5, 0.1, 0.5));
                    messages.send(player, MessageKey.LOCATION_FIX_UNDERGROUND);
                    break;
                }
                if (i == MaxHeight) {
                    TeleportUtils.teleport(player, JoinBlock.getLocation().add(0.5, 1.1, 0.5));
                    messages.send(player, MessageKey.LOCATION_FIX_UNDERGROUND_CANT_FIX);
                }
            }
        }

    }
}
