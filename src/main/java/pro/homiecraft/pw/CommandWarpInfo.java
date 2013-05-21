package pro.homiecraft.pw;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * User: Stian
 * Date: 21.04.13
 * Time: 13:54
 */
public class CommandWarpInfo implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("pwarpinfo")){
            Player player = (Player) s;
            if (args.length < 0){
                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "] Missing arguments! Usage:");
                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "] /pwarpinfo <warpName>");
            }else{
                String pName = player.getName().toLowerCase();
                WarpConfig.reloadWarpConfig(pName);
                String warpName = args[0];
                if (!(WarpConfig.getWarpConfig(pName).getString(warpName) == null)){
                    List<?> sharedList = WarpConfig.getWarpConfig(pName).getList(warpName + ".Shared");
                    Double warpLocX = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".x");
                    Double warpLocY = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".y");
                    Double warpLocZ = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".z");
                    String world = WarpConfig.getWarpConfig(pName).getString(warpName + ".world");

                    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "] Warp Info: " + ChatColor.RED + warpName);
                    player.sendMessage("Location: X: " + warpLocX + "  Y: " + warpLocY + "  Z: " + warpLocZ);
                    player.sendMessage("World: " + world);
                    player.sendMessage("Shared with: " + sharedList.toString());
                }else{
                    player.sendMessage("Warp was not found! Check your spelling.. Or if it exist!");
                }
            }
        }

        return true;
    }
}
