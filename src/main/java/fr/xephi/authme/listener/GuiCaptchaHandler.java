package fr.xephi.authme.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.api.v3.AuthMeApi;
import fr.xephi.authme.settings.properties.HooksSettings;
import fr.xephi.authme.settings.properties.RestrictionSettings;
import fr.xephi.authme.settings.properties.SecuritySettings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class GuiCaptchaHandler implements Listener {

    //define AuthMeApi
    private final AuthMeApi authmeApi = AuthMeApi.getInstance();
    private final Plugin plugin;
    //define global enabled
    public static boolean enabled=true;
    //define timesLeft
    private int timesLeft = 3;
    //Use ConcurrentHashMap to store player and their close reason
    protected static ConcurrentHashMap<Player, String> closeReasonMap = new ConcurrentHashMap<>();
    //define randomStringSet
    String randomSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz!@#%&*()_+";
    String randomString = "";
    Random randomItemSet = new Random();
    Random howManyRandom = new Random();



    int howLongIsRandomString = (howManyRandom.nextInt(3)+1);
    public GuiCaptchaHandler(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            // 获取点击事件的容器
            Inventory inventory = event.getInventory();
            if (!authmeApi.isRegistered(player.getName()) && !closeReasonMap.containsKey(player)) {
                if(AuthMe.settings.getProperty(HooksSettings.HOOK_FLOODGATE_PLAYER) && AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_BE_COMPATIBILITY) && org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgateId(event.getWhoClicked().getUniqueId()) && ( getServer().getPluginManager().isPluginEnabled("floodgate") || getServer().getPluginManager().getPlugin("floodgate") != null)){
                    return;
                }
                if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.REDSTONE_BLOCK)) {
                    event.setCancelled(true);
                    closeReasonMap.put(player, "verified");
                    player.closeInventory();
                    player.sendMessage("§a验证完成");
                } else {
                    player.sendMessage("§c验证失败,你还有" + timesLeft + "§c次机会");
                    timesLeft--;
                }
                    //force to string
            }
        }
    }


    @EventHandler(ignoreCancelled = true,priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
//        if (settings.getProperty(HooksSettings.HOOK_FLOODGATE_PLAYER)) {
//            if (getServer().getPluginManager().getPlugin("floodgate") != null) {
//                if (org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgateId(event.getUniqueId())) return;
//            }
//        }
        randomString="";
        Player playerunreg = event.getPlayer();
        String name = playerunreg.getName();
        if (!authmeApi.isRegistered(name)) {
            if(AuthMe.settings.getProperty(HooksSettings.HOOK_FLOODGATE_PLAYER) && AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_BE_COMPATIBILITY) && org.geysermc.floodgate.api.FloodgateApi.getInstance().isFloodgateId(event.getPlayer().getUniqueId()) && (getServer().getPluginManager().isPluginEnabled("floodgate") || getServer().getPluginManager().getPlugin("floodgate") != null)){
                closeReasonMap.put(playerunreg, "verified");
                playerunreg.sendMessage("§a基岩版自动验证完成");
                return;
            }
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                StringBuilder sb = new StringBuilder();
                howLongIsRandomString = (howManyRandom.nextInt(3)+1);
                for (int i = 0; i < howLongIsRandomString; i++) {
                    //生成随机索引号
                    int index = randomItemSet.nextInt(randomSet.length());

                    // 从字符串中获取由索引 index 指定的字符
                    char randomChar = randomSet.charAt(index);

                    // 将字符追加到字符串生成器
                    sb.append(randomChar);
                }

                Bukkit.getScheduler().runTask(this.plugin, () -> {
                    randomString = sb.toString();
                    Random random_blockpos = new Random();
                    AtomicInteger random_num = new AtomicInteger(random_blockpos.nextInt(26));
                    Inventory menu = Bukkit.createInventory(null, 27, randomString+"请验证你是真人");
                    ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
//                    ItemStack goldBlock = new ItemStack(Material.GOLD_BLOCK);
                    ItemMeta meta = item.getItemMeta();
                    try {
                        if (meta != null) {
                            meta.setDisplayName("§a我是"  + "§a真人");
                            item.setItemMeta(meta);
//                            goldBlock.setItemMeta(meta);
                        }
                    } catch (NullPointerException e) {
                        getLogger().log(Level.WARNING, "Unexpected error occurred while setting item meta.");
                    }
                    Bukkit.getScheduler().runTask(this.plugin,()-> {
                        menu.setItem(random_num.get(), item);
//                        for (int i = 0; i < 27; i++) {
//                            if (i == random_num.get()) {
//                                int finalI1 = i;
//                                Bukkit.getScheduler().runTask(this.plugin, () -> {
//                                    menu.setItem(finalI1, item);
//                                });
//                            } else {
//                                int finalI = i;
//                                Bukkit.getScheduler().runTask(this.plugin, () -> {
//                                    menu.setItem(finalI, goldBlock);
//                                });
//                            }
//                        }
                    });
                    menu.setItem(random_num.get(), item);
                    Bukkit.getScheduler().runTask(this.plugin, () -> {
                        playerunreg.openInventory(menu);
                    });
                    if (AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_TIMEOUT)>0) {
                        long timeOut = AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_TIMEOUT);
                        if (AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_TIMEOUT) > AuthMe.settings.getProperty(RestrictionSettings.TIMEOUT)){
                            Bukkit.getScheduler().runTask(this.plugin,() -> {
                                Bukkit.getLogger().warning("AuthMe detected that your GUI captcha timeout seconds(" + AuthMe.settings.getProperty(SecuritySettings.GUI_CAPTCHA_TIMEOUT) + ") is bigger than the Login timeout seconds(" + AuthMe.settings.getProperty(RestrictionSettings.TIMEOUT) + "). To prevent issues, we will make the GUI captcha follow the Login timeout seconds, please check and modify your config.");
                            });
                            timeOut = AuthMe.settings.getProperty(RestrictionSettings.TIMEOUT);
                        }
                        long finalTimeOut = timeOut;
                        Bukkit.getScheduler().runTask(this.plugin, () -> {
                            Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
                                if (!closeReasonMap.containsKey(playerunreg) && !authmeApi.isRegistered(playerunreg.getName())) {
                                    playerunreg.kickPlayer("§c验证超时");
                                    timesLeft = 3; // Reset the attempt counter
                                }
                            }, finalTimeOut * 20L);
                        });
                    }

                    Bukkit.getScheduler().runTask(this.plugin,()-> {
                        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.CLOSE_WINDOW) {
                            @Override
                            public void onPacketReceiving(PacketEvent event) {
                                if (event.getPlayer() == playerunreg && !closeReasonMap.containsKey(playerunreg) && !authmeApi.isRegistered(playerunreg.getName())) {
                                    if (timesLeft <= 0) {
                                        Bukkit.getScheduler().runTask(this.plugin, () -> {
                                            playerunreg.kickPlayer("§c请先完成人机验证!");
                                        });
                                        timesLeft = 3;
                                        return;
                                    } else {
                                        timesLeft--;
                                        playerunreg.sendMessage("§c请先完成验证!,你还有" + timesLeft + "次机会");

                                    }
                                    event.setCancelled(true);
                                    random_num.set(random_blockpos.nextInt(26));
                                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                                        Bukkit.getScheduler().runTask(plugin, () -> {
                                            menu.clear();
                                            menu.setItem(random_num.get(), item);
                                        });


//                                        for (int i = 0; i < 27; i++) {
//                                            if (i == random_num.get()) {
//                                                int finalI = i;
//                                                Bukkit.getScheduler().runTask(plugin, () -> {
//                                                    menu.setItem(finalI, item);
//                                                });
//                                            } else {
//                                                int finalI1 = i;
//                                                Bukkit.getScheduler().runTask(plugin, () -> {
//                                                    menu.setItem(finalI1, goldBlock); // 其他位置填充为金块
//                                                });
//                                            }
//                                        }
                                        Bukkit.getScheduler().runTask(plugin, () -> {
                                            playerunreg.openInventory(menu);
                                        });
                                    });
                                }
                            }
                        });
                    });
                    Bukkit.getScheduler().runTask(this.plugin, () -> {

                        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.CHAT) {
                            @Override
                            public void onPacketReceiving(PacketEvent event) {
                                if (event.getPlayer() == playerunreg && !closeReasonMap.containsKey(playerunreg) && !authmeApi.isRegistered(playerunreg.getName())) {
                                    playerunreg.sendMessage("§c请先完成验证!");
                                    event.setCancelled(true);
                                }
                            }
                        });
                    });

                });
            });



        }
    }
/*
            chatListener = new PacketAdapter(this.plugin, ListenerPriority.HIGHEST, PacketType.Play.Client.CHAT) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    if (event.getPlayer() == player) {
                        event.setCancelled(true);

                    }
                }
            };
*/

//            getLogger().log(Level.INFO, "TESTTEST");

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        if (!authmeApi.isRegistered(name)){closeReasonMap.remove(player);}
    }


}

