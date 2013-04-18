package pro.homiecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 11-04-13
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public class PrivateWarps extends JavaPlugin {

    public static PrivateWarps pluginST;

    public void onDisable(){

    }

    public void onEnable(){
        pluginST = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();

        initMetrics();

        PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new PlayerListener(), this);

        getCommands();
    }

    public void getCommands(){
        this.getCommand("privatewarps").setExecutor(new CommandPrivateWarps());
        this.getCommand("padmin").setExecutor(new CommandAdmin());
        this.getCommand("psetwarp").setExecutor(new CommandSetWarp());
        this.getCommand("pwarp").setExecutor(new CommandWarp());
        this.getCommand("pdelwarp").setExecutor(new CommandDeleteWarp());
        this.getCommand("pwarps").setExecutor(new CommandListWarps());
    }

    public void initMetrics(){
        try {
            Metrics metrics = new Metrics(this);

            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
    }
}
