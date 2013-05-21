package pro.homiecraft.pw;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
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
    static HashMap<String, Location> pLoc = new HashMap<String, Location>();
    static HashMap<String, Long> warpCooldown = new HashMap<String, Long>();

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pwarp")) {
            final Player player = (Player) s;
            PrivateWarps.pluginST.reloadConfig();
            Integer rawdelay = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Delay");
            final int delay = rawdelay * 20;
            WarpConfig.reloadWarpConfig(player.getName().toLowerCase());
            if (args.length == 1) {
                final String warpName = args[0].toLowerCase();
                if (WarpConfig.getWarpConfig(player.getName().toLowerCase()).getString(warpName) == null) {
                    s.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Warp does not exist!");
                } else {
                    int configCD = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");

                    pLoc.put(player.getName(), player.getLocation());

                    if (!(configCD == 0)) {
                        if (warpCooldown.containsKey(player.getName())) {
                            if (System.currentTimeMillis() < warpCooldown.get(player.getName())) {
                                Long timeLeft = warpCooldown.get(player.getName()) - System.currentTimeMillis();
                                //int seconds = (int) (timeLeft / 1000) % 100 ;
                                double seconds = (int) (timeLeft / 1000);
                                String SoonToBeDone = Double.valueOf(seconds).toString();
                                //String[] SoontimeLeft = SoonToBeDone.split(".");
                                //String StimeLeft = SoontimeLeft[0];
                                player.sendMessage(ChatColor.DARK_GRAY + "Warp is on cooldown! You have to wait: " + SoonToBeDone + " seconds more!");
                            } else {
                                if (!(rawdelay == 0)) {
                                    player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                                }
                                BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                                if (taskIDs.containsKey(player.getName())) {
                                    if (task.isQueued(taskIDs.get(player.getName()))) {
                                        task.cancelTask(taskIDs.get(player.getName()));
                                    }
                                }
                                int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                    public void run() {
                                        tpPlayer(player.getName().toLowerCase(), warpName);
                                        Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                        int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                        long systemTime = System.currentTimeMillis() + k;
                                        warpCooldown.put(player.getName(), systemTime);
                                    }
                                }, delay);
                                taskIDs.put(player.getName(), taskID);
                            }
                        } else {
                            if (!(rawdelay == 0)) {
                                player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                            }
                            BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                            if (taskIDs.containsKey(player.getName())) {
                                if (task.isQueued(taskIDs.get(player.getName()))) {
                                    task.cancelTask(taskIDs.get(player.getName()));
                                }
                            }
                            int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                                public void run() {
                                    tpPlayer(player.getName().toLowerCase(), warpName);
                                    Integer configCD2 = PrivateWarps.pluginST.getConfig().getInt("PrivateWarps.Warps.Warp-Cooldown");
                                    int k = Integer.valueOf(String.valueOf(configCD2) + String.valueOf("000"));
                                    ;
                                    long systemTime = System.currentTimeMillis() + k;
                                    warpCooldown.put(player.getName(), systemTime);
                                }
                            }, delay);
                            taskIDs.put(player.getName(), taskID);
                        }
                    } else {
                        if (!(rawdelay == 0)) {
                            player.sendMessage(ChatColor.DARK_GRAY + "Warping in: " + rawdelay + " seconds");
                        }
                        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
                        if (taskIDs.containsKey(player.getName())) {
                            if (task.isQueued(taskIDs.get(player.getName()))) {
                                task.cancelTask(taskIDs.get(player.getName()));
                            }
                        }
                        int taskID = PrivateWarps.pluginST.getServer().getScheduler().scheduleSyncDelayedTask(PrivateWarps.pluginST, new Runnable() {

                            public void run() {
                                tpPlayer(player.getName().toLowerCase(), warpName);
                            }
                        }, delay);
                        taskIDs.put(player.getName(), taskID);
                    }
                }
            } else {
                player.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "PrivateWarps" + ChatColor.AQUA + "]" + ChatColor.WHITE + " Usage: /pwarp WarpName");
            }
        }
        return true;
    }

    public void loadDestChunk(Location destChunk){
          Chunk c = destChunk.getChunk();
        if(!c.isLoaded()){
            c.load();
        }
    }

    public void tpPlayer(String pName, String warpNameRaw){
        String pl = pName.toLowerCase();
        String warpName = warpNameRaw.toLowerCase();
        Player player = Bukkit.getPlayer(pl);

        WarpConfig.reloadWarpConfig(pl);
        double xLoc = WarpConfig.getWarpConfig(pl).getDouble(warpName + ".x");
        double yLoc = WarpConfig.getWarpConfig(pl).getDouble(warpName + ".y");
        double zLoc = WarpConfig.getWarpConfig(pl).getDouble(warpName + ".z");

        String yaw = WarpConfig.getWarpConfig(pl).getString(warpName + ".yaw");
        String pitch = WarpConfig.getWarpConfig(pl).getString(warpName + ".pitch");
        String world = WarpConfig.getWarpConfig(pl).getString(warpName + ".world");

        Float fYaw = Float.parseFloat(yaw);
        Float fPitch = Float.parseFloat(pitch);

        Location targetLoc = new Location(Bukkit.getWorld(world), xLoc, yLoc, zLoc, fYaw, fPitch);

        loadDestChunk(targetLoc);

        player.sendMessage(ChatColor.DARK_GRAY + "Warping...");
        player.teleport(targetLoc);
    }
}
