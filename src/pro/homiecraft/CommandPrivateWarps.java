package pro.homiecraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 13-04-13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class CommandPrivateWarps implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("privatewarps")){
            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Information!");
            s.sendMessage(ChatColor.AQUA + "Name: " + ChatColor.WHITE + PrivateWarps.pluginST.getDescription().getName());
            s.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.WHITE + PrivateWarps.pluginST.getDescription().getVersion());
            s.sendMessage(ChatColor.AQUA + "Author(s): " + ChatColor.WHITE + PrivateWarps.pluginST.getDescription().getAuthors());
            s.sendMessage(ChatColor.AQUA + "Website: " + ChatColor.WHITE + PrivateWarps.pluginST.getDescription().getWebsite());
            s.sendMessage(ChatColor.AQUA + "Description: " + ChatColor.WHITE + PrivateWarps.pluginST.getDescription().getDescription());
        }
        return true;
    }
}
