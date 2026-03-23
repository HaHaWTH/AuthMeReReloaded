package fr.xephi.authme.datasource.converter;

import de.luricos.bukkit.xAuth.database.DatabaseTables;
import de.luricos.bukkit.xAuth.utils.xAuthLog;
import de.luricos.bukkit.xAuth.xAuth;
import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.initialization.DataFolder;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.util.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static fr.xephi.authme.util.FileUtils.makePath;

public class XAuthConverter implements Converter {

    @Inject
    @DataFolder
    private File dataFolder;
    @Inject
    private DataSource database;
    @Inject
    private PluginManager pluginManager;

    @Inject
    private Messages messages;

    XAuthConverter() {
    }

    @Override
    public void execute(CommandSender sender) {
        try {
            Class.forName("de.luricos.bukkit.xAuth.xAuth");
            convert(sender);
        } catch (ClassNotFoundException ce) {
            messages.send(sender, MessageKey.CONVERTER_XAUTH_CLASS_NOT_FOUND);
        }
    }

    private void convert(CommandSender sender) {
        if (pluginManager.getPlugin("xAuth") == null) {
            messages.send(sender, MessageKey.CONVERTER_XAUTH_PLUGIN_NOT_FOUND);
            return;
        }
        //TODO ljacqu 20160702: xAuthDb is not used except for the existence check -- is this intended?
        File xAuthDb = new File(dataFolder.getParent(), makePath("xAuth", "xAuth.h2.db"));
        if (!xAuthDb.exists()) {
            messages.send(sender, MessageKey.CONVERTER_XAUTH_H2_MISSING);
        }
        List<Integer> players = getXAuthPlayers();
        if (Utils.isCollectionEmpty(players)) {
            messages.send(sender, MessageKey.CONVERTER_XAUTH_NO_PLAYERS);
            return;
        }
        messages.send(sender, MessageKey.CONVERTER_XAUTH_STARTING);

        for (int id : players) {
            String pl = getIdPlayer(id);
            String psw = getPassword(id);
            if (psw != null && !psw.isEmpty() && pl != null) {
                PlayerAuth auth = PlayerAuth.builder()
                    .name(pl.toLowerCase(Locale.ROOT))
                    .realName(pl)
                    .password(psw, null).build();
                database.saveAuth(auth);
            }
        }
        messages.send(sender, MessageKey.CONVERTER_XAUTH_SUCCESS);
    }

    private String getIdPlayer(int id) {
        String realPass = "";
        Connection conn = xAuth.getPlugin().getDatabaseController().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = String.format("SELECT `playername` FROM `%s` WHERE `id` = ?",
                xAuth.getPlugin().getDatabaseController().getTable(DatabaseTables.ACCOUNT));
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            realPass = rs.getString("playername").toLowerCase(Locale.ROOT);
        } catch (SQLException e) {
            xAuthLog.severe("Failed to retrieve name for account: " + id, e);
            return null;
        } finally {
            xAuth.getPlugin().getDatabaseController().close(conn, ps, rs);
        }
        return realPass;
    }

    private List<Integer> getXAuthPlayers() {
        List<Integer> xP = new ArrayList<>();
        Connection conn = xAuth.getPlugin().getDatabaseController().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = String.format("SELECT * FROM `%s`",
                xAuth.getPlugin().getDatabaseController().getTable(DatabaseTables.ACCOUNT));
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                xP.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            xAuthLog.severe("Cannot import xAuthPlayers", e);
            return new ArrayList<>();
        } finally {
            xAuth.getPlugin().getDatabaseController().close(conn, ps, rs);
        }
        return xP;
    }

    private String getPassword(int accountId) {
        String realPass = "";
        Connection conn = xAuth.getPlugin().getDatabaseController().getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = String.format("SELECT `password`, `pwtype` FROM `%s` WHERE `id` = ?",
                xAuth.getPlugin().getDatabaseController().getTable(DatabaseTables.ACCOUNT));
            ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            realPass = rs.getString("password");
        } catch (SQLException e) {
            xAuthLog.severe("Failed to retrieve password hash for account: " + accountId, e);
            return null;
        } finally {
            xAuth.getPlugin().getDatabaseController().close(conn, ps, rs);
        }
        return realPass;
    }

}
