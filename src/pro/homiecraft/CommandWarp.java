package pro.homiecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 11-04-13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class CommandWarp implements CommandExecutor {

    static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
    static HashMap<String, Double> pLoc = new HashMap<String, Double>();
    HashMap<String, Long> warpCooldown = new HashMap<String, Long>();

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if (cmd.getName().equalsIgnoreCase("pwarp")){
            final Player player = (Player) s;
            PrivateWarps.pluginST.reloadConfig();
            int rawdelay = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Delay");
            final int delay = rawdelay * 20;
            if (args.length == 1){
                final String warpName = args[0].toLowerCase();
                if(WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName) == null){
                    s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                }else{
                    int configCD = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");

                    pLoc.put(player.getName() + "x", player.getLocation().getX());
                    pLoc.put(player.getName() + "y", player.getLocation().getY());
                    pLoc.put(player.getName() + "z", player.getLocation().getZ());

                    if (!(configCD == 0)){
                        if(warpCooldown.containsKey(player.getName())){
                            if(System.currentTimeMillis() < warpCooldown.get(player.getName())){
                                Long timeLeft = warpCooldown.get(player.getName()) - System.currentTimeMillis();
                                //int seconds = (int) (timeLeft / 1000) % 100 ;
                                double seconds = (int) (timeLeft / 1000);
                                String SoonToBeDone = Double.valueOf(seconds).toString();
                                String[] SoontimeLeft = SoonToBeDone.split(",");
                                String StimeLeft = SoontimeLeft[0];
                                player.sendMessage(ChatColor.DARK_GRAY + "Warp is on cooldown! You have to wait: " + StimeLeft + " seconds more!");
                            }else{
                                if (!(rawdelay == 0)){
                                    player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                                }
                                BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                                if(taskIDs.containsKey(player.getName())){
                                    if(task.isQueued(taskIDs.get(player.getName()))){
                                        task.cancelTask(taskIDs.get(player.getName()));
                                    }
                                }
                                int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                    public void run() {
                                        WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                                        double xLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".x");
                                        double yLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".y");
                                        double zLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".z");

                                        String yaw = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".yaw");
                                        String pitch = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".pitch");
                                        String world = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".world");

                                        Float fYaw = Float.parseFloat(yaw);
                                        Float fPitch = Float.parseFloat(pitch);

                                        Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                        player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                        player.teleport(targetLoc);
                                        Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                        //String formatConfig = null;
                                        //if(configCD2 > 9){
                                        //    formatConfig = StringUtils.rightPad(configCD2.toString(), 5, "0");
                                        //}else if(configCD2 < 10){
                                        //    formatConfig = StringUtils.rightPad(configCD2.toString(), 4, "0");
                                        //}else if(configCD2 > 99){
                                        //    formatConfig = StringUtils.rightPad(configCD2.toString(), 6, "0");
                                        //}
                                        int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                        //Integer FinalSum =  Integer.parseInt(formatConfig);
                                        long systemTime = System.currentTimeMillis() + k;
                                        warpCooldown.put(player.getName(), systemTime);
                                        //player.sendMessage(formatConfig);
                                    }
                                }, delay);
                                //PlayerListener.taskIDs.remove(player.getName());
                                //PlayerListener.taskIDs.put(player.getName(), taskID);
                                taskIDs.put(player.getName(), taskID);
                            }
                        }else{
                            if (!(rawdelay == 0)){
                                player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                            }
                            BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                            if(taskIDs.containsKey(player.getName())){
                                if(task.isQueued(taskIDs.get(player.getName()))){
                                    task.cancelTask(taskIDs.get(player.getName()));
                                }
                            }
                            int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                public void run() {
                                    WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                                    double xLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".x");
                                    double yLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".y");
                                    double zLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".z");

                                    String yaw = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".yaw");
                                    String pitch = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".pitch");
                                    String world = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".world");

                                    Float fYaw = Float.parseFloat(yaw);
                                    Float fPitch = Float.parseFloat(pitch);

                                    Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                    player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                    player.teleport(targetLoc);
                                    Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                    //String formatConfig = null;
                                    //if(configCD2 > 9){
                                    //    formatConfig = StringUtils.rightPad(configCD2.toString(), 5, "0");
                                    //}else if(configCD2 < 10){
                                    //    formatConfig = StringUtils.rightPad(configCD2.toString(), 4, "0");
                                    //}else if(configCD2 > 99){
                                    //    formatConfig = StringUtils.rightPad(configCD2.toString(), 6, "0");
                                    //}
                                    int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                    //Integer FinalSum =  Integer.parseInt(formatConfig);
                                    long systemTime = System.currentTimeMillis() + k;
                                    warpCooldown.put(player.getName(), systemTime);
                                    //player.sendMessage(formatConfig);
                                }
                            }, delay);
                            //PlayerListener.taskIDs.remove(player.getName());
                            //PlayerListener.taskIDs.put(player.getName(), taskID);
                            taskIDs.put(player.getName(), taskID);
                        }
                    }else{
                        if (!(rawdelay == 0)){
                            player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                        }
                        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                        if(taskIDs.containsKey(player.getName())){
                            if(task.isQueued(taskIDs.get(player.getName()))){
                                task.cancelTask(taskIDs.get(player.getName()));
                            }
                        }
                        int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                            public void run() {
                                WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
                                double xLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".x");
                                double yLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".y");
                                double zLoc = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getDouble(warpName + ".z");

                                String yaw = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".yaw");
                                String pitch = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".pitch");
                                String world = WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName + ".world");

                                Float fYaw = Float.parseFloat(yaw);
                                Float fPitch = Float.parseFloat(pitch);

                                Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

                                player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
                                player.teleport(targetLoc);
                                //int configCD = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                //Long systemTime = System.currentTimeMillis() + configCD + Integer.parseInt("000");
                                //warpCooldown.put(player.getName(), systemTime); Not using warp cooldown
                            }
                        }, delay);
                        //PlayerListener.taskIDs.remove(player.getName());
                        //PlayerListener.taskIDs.put(player.getName(), taskID);
                        taskIDs.put(player.getName(), taskID);
                    }
                }
            }else{
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pwarp WarpName");
            }
        }
        return true;
    }
}
