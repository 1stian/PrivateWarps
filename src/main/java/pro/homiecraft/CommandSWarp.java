package pro.homiecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 19-04-13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class CommandSWarp implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pswarp")) {
            final Player player = (Player) s;
            PrivateWarps.pluginST.reloadConfig();
            int rawdelay = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Delay");
            final int delay = rawdelay * 20;

            if (args.length < 2) {
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pswarp <playerName> <warpName>");
            } else {
                final String pName = args[0].toLowerCase();
                final String warpName = args[1].toLowerCase();
                WarpConfig.reloadWarpConfig(pName);
                if (WarpConfig.getWarpConfig(pName).getString(warpName) == null || WarpConfig.getWarpConfig(pName) == null) {
                    s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp/player does not exist!");
                } else {
                    if (WarpConfig.getWarpConfig(pName).getList(warpName + ".Shared") == null) {
                        player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " " + pName + " is not sharing warp: " + warpName + " with you!");
                    }else{
                        List<?> sharedList = WarpConfig.getWarpConfig(pName).getList(warpName + ".Shared");
                        if (sharedList.contains(pName)){
                            int configCD = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");

                            CommandWarp.pLoc.put(player.getName(), player.getLocation());

                            if (!(configCD == 0)) {
                                if (CommandWarp.warpCooldown.containsKey(player.getName())) {
                                    if (System.currentTimeMillis() < CommandWarp.warpCooldown.get(player.getName())) {
                                        Long timeLeft = CommandWarp.warpCooldown.get(player.getName()) - System.currentTimeMillis();
                                        double seconds = (int) (timeLeft / 1000);
                                        String SoonToBeDone = Double.valueOf(seconds).toString();
                                        String[] SoontimeLeft = SoonToBeDone.split(",");
                                        String StimeLeft = SoontimeLeft[0];
                                        player.sendMessage(ChatColor.DARK_GRAY + "Warp is on cooldown! You have to wait: " + StimeLeft + " seconds more!");
                                    } else {
                                        if (!(rawdelay == 0)) {
                                            player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                                        }
                                        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                                        if (CommandWarp.taskIDs.containsKey(player.getName())) {
                                            if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                                                task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                                            }
                                        }
                                        int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                            public void run() {
                                                WarpConfig.reloadWarpConfig(pName);
                                                double xLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".x");
                                                double yLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".y");
                                                double zLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".z");

                                                String yaw = WarpConfig.getWarpConfig(pName).getString(warpName + ".yaw");
                                                String pitch = WarpConfig.getWarpConfig(pName).getString(warpName + ".pitch");
                                                String world = WarpConfig.getWarpConfig(pName).getString(warpName + ".world");

                                                Float fYaw = Float.parseFloat(yaw);
                                                Float fPitch = Float.parseFloat(pitch);

                                                Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                                player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                                player.teleport(targetLoc);
                                                Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                                int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                                long systemTime = System.currentTimeMillis() + k;
                                                CommandWarp.warpCooldown.put(player.getName(), systemTime);
                                            }
                                        }, delay);
                                        CommandWarp.taskIDs.put(player.getName(), taskID);
                                    }
                                } else {
                                    if (!(rawdelay == 0)) {
                                        player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                                    }
                                    BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                                    if (CommandWarp.taskIDs.containsKey(player.getName())) {
                                        if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                                        }
                                    }
                                    int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                        public void run() {
                                            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                                            double xLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".x");
                                            double yLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".y");
                                            double zLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".z");

                                            String yaw = WarpConfig.getWarpConfig(pName).getString(warpName + ".yaw");
                                            String pitch = WarpConfig.getWarpConfig(pName).getString(warpName + ".pitch");
                                            String world = WarpConfig.getWarpConfig(pName).getString(warpName + ".world");

                                            Float fYaw = Float.parseFloat(yaw);
                                            Float fPitch = Float.parseFloat(pitch);

                                            Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                            player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                            player.teleport(targetLoc);
                                            Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                            int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                            ;
                                            long systemTime = System.currentTimeMillis() + k;
                                            CommandWarp.warpCooldown.put(player.getName(), systemTime);
                                        }
                                    }, delay);
                                    CommandWarp.taskIDs.put(player.getName(), taskID);
                                }
                            } else {
                                if (!(rawdelay == 0)) {
                                    player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                                }
                                BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                                if (CommandWarp.taskIDs.containsKey(player.getName())) {
                                    if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                                        task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                                    }
                                }
                                int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                    public void run() {
                                        WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                                        double xLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".x");
                                        double yLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".y");
                                        double zLoc = WarpConfig.getWarpConfig(pName).getDouble(warpName + ".z");

                                        String yaw = WarpConfig.getWarpConfig(pName).getString(warpName + ".yaw");
                                        String pitch = WarpConfig.getWarpConfig(pName).getString(warpName + ".pitch");
                                        String world = WarpConfig.getWarpConfig(pName).getString(warpName + ".world");

                                        Float fYaw = Float.parseFloat(yaw);
                                        Float fPitch = Float.parseFloat(pitch);

                                        Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                        player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                        player.teleport(targetLoc);
                                    }
                                }, delay);
                                CommandWarp.taskIDs.put(player.getName(), taskID);
                            }
                        }else{
                            player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " " + pName + " is not sharing warp: " + warpName + " with you!");
                        }
                    }
                }
            }
        }
        return true;
    }
}
