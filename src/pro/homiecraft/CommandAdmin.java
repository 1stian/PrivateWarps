package pro.homiecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 13-04-13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class CommandAdmin implements CommandExecutor {
    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("padmin")){
            if (args.length == 0){
                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin delwarp|setwarp|warp|listwarps|warplimit|delay|cooldown");
            }else if(args.length > 1){
                if (args[0].equalsIgnoreCase("delwarp")){
                    if (args.length < 3){
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin delwarp PlayerName warpName");
                    }
                    if(args.length == 3){
                        String pName = args[1];
                        String warpName = args[2];

                        File playerConfig = new File(PrivateWarps.pluginST.getDataFolder() + "/data/" + pName + ".yml");
                        if (!playerConfig.exists()){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Player file could not be found!");
                        }else{
                            WarpConfig.reloadWarpConfig(pName);
                            if(WarpConfig.getWarpConfig(pName).getString(warpName) == null){
                                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp could not be found!");
                            }else{
                                int count = WarpConfig.getWarpConfig(pName).getInt("count");
                                count--;

                                WarpConfig.getWarpConfig(pName).set(warpName, null);
                                WarpConfig.getWarpConfig(pName).set("count", count);
                                WarpConfig.saveWarpConfig(pName);
                                WarpConfig.reloadWarpConfig(pName);

                                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " has ben deleted for " + pName);
                            }
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("setwarp")){
                    if (args.length < 3){
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin setwarp PlayerName warpName");
                    }
                    if(args.length == 3){
                        String pName = args[1];
                        String warpName = args[2];

                        File playerConfig = new File(PrivateWarps.pluginST.getDataFolder() + "/data/" + pName + ".yml");
                        if (!playerConfig.exists()){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Player file could not be found!");
                        }else{
                            WarpConfig.reloadWarpConfig(pName);
                            if(WarpConfig.getWarpConfig(pName).getString(warpName) != null){
                                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp already exist!");
                            }else{
                                Player player = (Player) s;
                                double xLoc = player.getLocation().getX();
                                double yLoc = player.getLocation().getY();
                                double zLoc = player.getLocation().getZ();
                                float yaw = player.getLocation().getYaw();
                                float pitch = player.getLocation().getPitch();

                                int count = WarpConfig.getWarpConfig(pName).getInt("count");
                                count++;

                                WarpConfig.getWarpConfig(pName).set(warpName + ".x", xLoc);
                                WarpConfig.getWarpConfig(pName).set(warpName + ".y", yLoc);
                                WarpConfig.getWarpConfig(pName).set(warpName + ".z", zLoc);
                                WarpConfig.getWarpConfig(pName).set(warpName + ".yaw", yaw);
                                WarpConfig.getWarpConfig(pName).set(warpName + ".pitch", pitch);
                                WarpConfig.getWarpConfig(pName).set("count", count);
                                WarpConfig.saveWarpConfig(pName);
                                WarpConfig.reloadWarpConfig(pName);

                                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp: " + warpName + " has ben set for " + pName);
                            }
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("warp")){
                    if (args.length < 3){
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin warp PlayerName warpName");
                    }
                    if(args.length == 3){
                        String pName = args[1];
                        String warpName = args[2];

                        File playerConfig = new File(PrivateWarps.pluginST.getDataFolder() + "/data/" + pName + ".yml");
                        if (!playerConfig.exists()){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Player file could not be found!");
                        }else{
                            WarpConfig.reloadWarpConfig(pName);
                            if(WarpConfig.getWarpConfig(pName).getString(warpName) == null){
                                s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                            }else{
                                Player player = (Player) s;
                                WarpConfig.reloadWarpConfig(pName);
                                double xLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".x");
                                double yLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".y");
                                double zLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".z");

                                String yaw = WarpConfig.getWarpConfig(pName).getString(warpName + ".yaw");
                                String pitch = WarpConfig.getWarpConfig(pName).getString(warpName + ".pitch");
                                String world = WarpConfig.getWarpConfig(player.getName()).getString(warpName + ".world");

                                Float fYaw = Float.parseFloat(yaw);
                                Float fPitch = Float.parseFloat(pitch);

                                Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                player.sendMessage(ChatColor.DARK_GRAY + "Warping to " + warpName + " owned by " + pName);
                                player.teleport(targetLoc);
                            }
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("listwarps")){
                    if (args.length < 2){
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin listwarps PlayerName");
                    }
                    if(args.length == 2){
                        String pName = args[1];

                        File playerConfig = new File(PrivateWarps.pluginST.getDataFolder() + "/data/" + pName + ".yml");
                        if (!playerConfig.exists()){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Player file could not be found!");
                        }else{
                            WarpConfig.reloadWarpConfig(pName);
                            Player player = (Player) s;
                            String warpList = WarpConfig.getWarpConfig(pName).getKeys(false).toString();
                            String rawList;
                            rawList = warpList.replace("count", "");
                            player.sendMessage(pName + "'s warps: " + ChatColor.AQUA + rawList);
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("warplimit")){
                    if (args.length == 2){
                        String arg = args[1];
                        if (Pattern.matches("[a-zA-Z]+", arg)){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin warplimit <number>");
                        }else{
                            PrivateWarps.pluginST.reloadConfig();
                            PrivateWarps.pluginST.getConfig().set("PrivateWarps.Warps.Maximum-Allowed-Warps", Integer.parseInt(arg));
                            PrivateWarps.pluginST.saveConfig();
                            PrivateWarps.pluginST.reloadConfig();
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warplimit is now set to " + args[1]);
                        }
                    }else{
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin warplimit <number>");
                    }
                }

                if(args[0].equalsIgnoreCase("delay")){
                    if (args.length == 2){
                        String arg = args[1];
                        if (Pattern.matches("[a-zA-Z]+", arg)){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin delay <number>");
                        }else{
                            PrivateWarps.pluginST.reloadConfig();
                            PrivateWarps.pluginST.getConfig().set("PrivateWarps.Warps.Warp-Delay", Integer.parseInt(arg));
                            PrivateWarps.pluginST.saveConfig();
                            PrivateWarps.pluginST.reloadConfig();
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp Delay is now set to " + args[1] + " seconds");
                        }
                    }else{
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin delay <number>");
                    }
                }

                if(args[0].equalsIgnoreCase("cooldown")){
                    if (args.length == 2){
                        String arg = args[1];
                        if (Pattern.matches("[a-zA-Z]+", arg)){
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin cooldown <number>");
                        }else{
                            PrivateWarps.pluginST.reloadConfig();
                            PrivateWarps.pluginST.getConfig().set("PrivateWarps.Warps.Warp-Cooldown", Integer.parseInt(arg));
                            PrivateWarps.pluginST.saveConfig();
                            PrivateWarps.pluginST.reloadConfig();
                            s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp Cooldown is now set to " + args[1] + " seconds");
                        }
                    }else{
                        s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /padmin cooldown <number>");
                    }
                }
            }
        }
        return true;
    }

}
