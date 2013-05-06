package pro.homiecraft.pw;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 11-04-13
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */
public class CommandSetWarp implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        Player player = (Player) s;
        if (cmd.getName().equalsIgnoreCase("psetwarp")){
            if (args.length == 1){
                World cWorld = player.getWorld();
                String warpName = args[0].toLowerCase();

                if (WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName) == null){
                    if (s.hasPermission("PrivateWarps.unlimited")){
                        double xLoc = player.getLocation().getX();
                        double yLoc = player.getLocation().getY();
                        double zLoc = player.getLocation().getZ();
                        float yaw = player.getLocation().getYaw();
                        float pitch = player.getLocation().getPitch();


                        WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                        int count = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getInt("count");
                        count++;

                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".x", xLoc);
                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".y", yLoc);
                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".z", zLoc);
                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".yaw", yaw);
                  WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".pitch", pitch);
                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".world", player.getWorld().getName());
                        WarpConfig.getWarpConfig(player.getName().toLowerCase()).set("count", count);
                        WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                        WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                        player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " has ben set!");
                    }else{
                        WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                        int maxcount = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Maximum-Allowed-Warps");
                        if (WarpConfig.getWarpConfig(player.getName().toLowerCase()).getInt("count") == maxcount || WarpConfig.getWarpConfig(player.getName()).getInt("count") > maxcount){
                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " You have reached the maximum allowed warps!");
                        }else{
                            double xLoc = player.getLocation().getX();
                            double yLoc = player.getLocation().getY();
                            double zLoc = player.getLocation().getZ();
                            float yaw = player.getLocation().getYaw();
                            float pitch = player.getLocation().getPitch();


                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                            int count = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getInt("count");
                            count++;

                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".x", xLoc);
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".y", yLoc);
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".z", zLoc);
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".yaw", yaw);
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".pitch", pitch);
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".world", player.getWorld().getName());
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set("count", count);
                            WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " has ben set!");
                        }
                    }
                }else{
                    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " already exist! Delete it first.");
                }
            }else{
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /psetwarp WarpName");
            }
        }
        return true;
    }
}
