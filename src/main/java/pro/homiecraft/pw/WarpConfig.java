package pro.homiecraft.pw;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 11-04-13
 * Time: 21:33
 * To change this template use File | Settings | File Templates.
 */
public class WarpConfig {

    static PrivateWarps plugin;
    public WarpConfig(PrivateWarps plugin) {
        WarpConfig.plugin = plugin;
    }

    public static FileConfiguration customConfig = null;
    public static File customConfigFile = null;

    public static void reloadWarpConfig(String Warp) {

        customConfigFile = new File(PrivateWarps.pluginST.getDataFolder() + "/data/" + Warp + ".yml");

        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        InputStream defConfigStream = PrivateWarps.pluginST.getResource("/data/" + Warp + ".yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            customConfig.setDefaults(defConfig);
        }
    }

    public static FileConfiguration getWarpConfig(String Warp) {
        if (customConfig == null) {
            reloadWarpConfig(Warp);
        }
        return customConfig;
    }

    public static void saveWarpConfig(String Warp) {
        if (customConfig == null || customConfigFile == null) {
            return;
        }

        try {
            customConfig.save(customConfigFile);
        } catch (IOException e) {
            Logger.getLogger("Minecraft").severe("Could not save " + Warp);
        }
    }

}
