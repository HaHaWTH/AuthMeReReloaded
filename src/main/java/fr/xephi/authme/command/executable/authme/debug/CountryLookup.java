package fr.xephi.authme.command.executable.authme.debug;

import fr.xephi.authme.data.auth.PlayerAuth;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.message.MessageKey;
import fr.xephi.authme.message.Messages;
import fr.xephi.authme.permission.DebugSectionPermissions;
import fr.xephi.authme.permission.PermissionNode;
import fr.xephi.authme.service.GeoIpService;
import fr.xephi.authme.service.ValidationService;
import fr.xephi.authme.settings.properties.ProtectionSettings;
import org.bukkit.command.CommandSender;

import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Shows the GeoIP information as returned by the geoIpService.
 */
class CountryLookup implements DebugSection {

    private static final Pattern IS_IP_ADDR = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");

    @Inject
    private GeoIpService geoIpService;

    @Inject
    private DataSource dataSource;

    @Inject
    private ValidationService validationService;

    @Inject
    private Messages messages;

    @Override
    public String getName() {
        return "cty";
    }

    @Override
    public String getDescription() {
        return "Check country protection / country data";
    }

    @Override
    public void execute(CommandSender sender, List<String> arguments) {
        messages.send(sender, MessageKey.DEBUG_CTY_TITLE);
        if (arguments.isEmpty()) {
            messages.send(sender, MessageKey.DEBUG_CTY_USAGE_PLAYER);
            messages.send(sender, MessageKey.DEBUG_CTY_USAGE_IP);
            return;
        }

        String argument = arguments.get(0);
        if (IS_IP_ADDR.matcher(argument).matches()) {
            outputInfoForIpAddr(sender, argument);
        } else {
            outputInfoForPlayer(sender, argument);
        }
    }

    @Override
    public PermissionNode getRequiredPermission() {
        return DebugSectionPermissions.COUNTRY_LOOKUP;
    }

    private void outputInfoForIpAddr(CommandSender sender, String ipAddr) {
        messages.send(sender, MessageKey.DEBUG_CTY_IP_INFO, ipAddr,
            geoIpService.getCountryCode(ipAddr), geoIpService.getCountryName(ipAddr));
        if (validationService.isCountryAdmitted(ipAddr)) {
            messages.send(sender, MessageKey.DEBUG_CTY_NOT_BLOCKED);
        } else {
            messages.send(sender, MessageKey.DEBUG_CTY_BLOCKED);
        }
        messages.send(sender, MessageKey.DEBUG_CTY_NOTE, ProtectionSettings.ENABLE_PROTECTION.getPath());
    }

    // TODO #1366: Extend with registration IP?
    private void outputInfoForPlayer(CommandSender sender, String name) {
        PlayerAuth auth = dataSource.getAuth(name);
        if (auth == null) {
            messages.send(sender, MessageKey.DEBUG_CTY_NO_PLAYER, name);
        } else if (auth.getLastIp() == null) {
            messages.send(sender, MessageKey.DEBUG_CTY_NO_IP, name);
        } else {
            messages.send(sender, MessageKey.DEBUG_CTY_PLAYER_IP, name, auth.getLastIp());
            outputInfoForIpAddr(sender, auth.getLastIp());
        }
    }
}
