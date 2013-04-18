package pro.homiecraft;


import org.bukkit.ChatColor;
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
public class CommandDeleteWarp implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("pdelwarp")){
            Player player = (Player) s;
            if (args.length == 1){
                String warpName = args[0];

                if (WarpConfig.getWarpConfig(player.getName()).getString(warpName) == null){
                    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " does not exist!");
                }else{
                    WarpConfig.reloadWarpConfig(player.getName());

                    int count = WarpConfig.getWarpConfig(player.getName()).getInt("count");
                    count--;

                    WarpConfig.getWarpConfig(player.getName()).set(warpName, null);
                    WarpConfig.getWarpConfig(player.getName()).set("count", count);
                    WarpConfig.saveWarpConfig(player.getName());
                    WarpConfig.reloadWarpConfig(player.getName());

                    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " has ben deleted!");
                }
            }else{
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pdelwarp WarpName");
            }
        }
        return true;
    }
}
