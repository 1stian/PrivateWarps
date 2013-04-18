package pro.homiecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 11-04-13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class CommandWarp implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("pwarp")){
            final Player player = (Player) s;
            PrivateWarps.pluginST.reloadConfig();
            int rawdelay = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Delay");
            final int delay = rawdelay * 20;
            if (args.length == 1){
                final String warpName = args[0];
                if(WarpConfig.getWarpConfig(player.getName()).getString(warpName) == null){
                    s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                }else{
                    if (!(rawdelay == 0)){
                        player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay);
                    }

                    int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                        public void run() {

                            WarpConfig.reloadWarpConfig(player.getName());
                            double xLoc = WarpConfig.getWarpConfig(player.getName()).getDouble(warpName + ".x");
                            double yLoc = WarpConfig.getWarpConfig(player.getName()).getDouble(warpName + ".y");
                            double zLoc = WarpConfig.getWarpConfig(player.getName()).getDouble(warpName + ".z");

                            String yaw = WarpConfig.getWarpConfig(player.getName()).getString(warpName + ".yaw");
                            String pitch = WarpConfig.getWarpConfig(player.getName()).getString(warpName + ".pitch");
                            String world = WarpConfig.getWarpConfig(player.getName()).getString(warpName + ".world");

                            Float fYaw = Float.parseFloat(yaw);
                            Float fPitch = Float.parseFloat(pitch);

                            Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                            player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                            player.teleport(targetLoc);
                        }
                    }, delay);
                    PlayerListener.taskIDs.put(player.getName(), taskID);
                }
            }else{
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pwarp WarpName");
            }
        }
        return true;
    }
}
