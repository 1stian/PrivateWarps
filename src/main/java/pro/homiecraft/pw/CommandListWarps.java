package pro.homiecraft.pw;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 13-04-13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
public class CommandListWarps implements CommandExecutor {
    HashMap<String, Boolean> pNamesToSend = new HashMap<String, Boolean>();
    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("pwarps")){
            Player player = (Player) s;
            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
            String warpList = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getKeys(false).toString();
            String rawList;
            rawList = warpList.replace("count", "");
            player.sendMessage("Warps: " + ChatColor.AQUA + rawList);


            if (PrivateWarps.pluginST.getConfig().getBoolean("PrivateWarps.settings.List-Shared-Warps-To-Players", true)){
                File dir = new File(PrivateWarps.pluginST.getDataFolder() + "/data/");
                for(File fs : dir.listFiles()){
                    String sfs = fs.toString();
                    String[] fileName = null;
                    if (sfs.contains("/")){
                        fileName = sfs.split("/");
                    }else{
                        fileName = sfs.split("\\\\");
                    }
                    String playerFile = fileName[3].toLowerCase();
                    String[] ArrplayerName = playerFile.split("\\.");
                    String playerName = ArrplayerName[0];
                    WarpConfig.reloadWarpConfig(playerName);
                    Set<String> sharedWarps = WarpConfig.getWarpConfig(playerName).getKeys(false);

                    ArrayList<String> WarpsToPrint = new ArrayList<String>();
                    for (String sw : sharedWarps){
                        List<?> sharedList = WarpConfig.getWarpConfig(playerName).getList(sw + ".Shared");
                        if (WarpConfig.getWarpConfig(playerName).getList(sw + ".Shared") == null){

                        }else{
                            if (sharedList.contains(player.getName().toLowerCase())){
                                pNamesToSend.put(playerName, true);
                                WarpsToPrint.add(sw);
                            }
                        }
                    }

                    if(!player.getName().equalsIgnoreCase(playerName)){
                        if (!(pNamesToSend.containsKey(playerName))){

                        }else{
                            player.sendMessage(playerName + "'s Shared warps for you: " + WarpsToPrint.toString());
                            pNamesToSend.clear();
                        }
                    }
                    WarpsToPrint.clear();
                }
            }
        }
        return true;
    }

}
