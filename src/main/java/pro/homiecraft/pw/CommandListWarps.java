package pro.homiecraft.pw;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 13-04-13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public class CommandListWarps implements CommandExecutor {
    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("pwarps")){
            Player player = (Player) s;
            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
            String warpList = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getKeys(false).toString();
            String rawList;
            rawList = warpList.replace("count", "");
            player.sendMessage("Warps: " + ChatColor.AQUA + rawList);
        }
        return true;
    }

}
