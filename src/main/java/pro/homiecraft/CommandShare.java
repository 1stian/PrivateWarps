package pro.homiecraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 19-04-13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public class CommandShare implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pshare")) {
            Player player = (Player) s;
            if (args.length < 3) {
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pshare add|remove <playerName> <warpName>");
            } else {
                if (args[0].equalsIgnoreCase("add")) {
                    String warpName = args[2].toLowerCase();
                    String pName = args[1].toLowerCase();

                    if (!(WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName) == null)){
                        if (!WarpConfig.getWarpConfig(player.getName().toLowerCase()).contains(warpName + ".Shared")) {
                            ArrayList<String> shared = new ArrayList<String>();
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".Shared", shared);
                            WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                        }

                        ArrayList<String> shareCheck = (ArrayList<String>) WarpConfig.getWarpConfig(player.getName().toLowerCase()).getList(warpName + ".Shared");
                        if (shareCheck.contains(pName)) {
                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " You're already sharing warp: " + warpName + " with " + pName);
                        } else {
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                            ArrayList<String> shared = new ArrayList<String>();
                            shared.add(pName);

                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".Shared", shared);
                            WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " You're now sharing warp: " + warpName + " with " + pName);
                        }
                    }else{
                        player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                    }
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    String warpName = args[2].toLowerCase();
                    String pName = args[1].toLowerCase();

                    if (!(WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName) == null)){
                        if (!WarpConfig.getWarpConfig(player.getName().toLowerCase()).contains(warpName + ".Shared")) {
                            ArrayList<String> shared = new ArrayList<String>();
                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".Shared", shared);
                            WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                        }

                        ArrayList<String> shareCheck = (ArrayList<String>) WarpConfig.getWarpConfig(player.getName().toLowerCase()).getList(warpName + ".Shared");
                        if (!shareCheck.contains(pName)) {
                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " You're not sharing warp: " + warpName + " with " + pName);
                        } else {
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                            ArrayList<String> shared = (ArrayList<String>) WarpConfig.getWarpConfig(player.getName().toLowerCase()).getList(warpName + ".Shared");
                            shared.remove(pName);

                            WarpConfig.getWarpConfig(player.getName().toLowerCase()).set(warpName + ".Shared", shared);
                            WarpConfig.saveWarpConfig(player.getName().toLowerCase());
                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());

                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " You're no longer sharing warp: " + warpName + " with " + pName);
                        }
                    }else{
                        player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                    }
                }
            }
        }
        return true;
    }

}
