package pro.homiecraft.pw;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 17-04-13
 * Time: 22:04
 * To change this template use File | Settings | File Templates.
 */
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
        boolean pMove = PrivateWarps.pluginST.getConfig().getBoolean("PrivateWarps.settings.Cancel-Warp-On-Player-Move", true);

        double locX = player.getLocation().getX();
        double locY = player.getLocation().getY();
        double locZ = player.getLocation().getZ();

        if (CommandWarp.pLoc.containsKey(player.getName())) {

            Location playerLoc = CommandWarp.pLoc.get(player.getName());
            double  plocX = playerLoc.getX();
            double  plocY = playerLoc.getY();
            double  plocZ = playerLoc.getZ();

            if (!(locX == plocX)) {
                if (pMove) {
                    if (CommandWarp.taskIDs.containsKey(player.getName())) {
                        if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                            CommandWarp.taskIDs.remove(player.getName());
                            player.sendMessage(ChatColor.DARK_GRAY + "You moved! Warp canceled!");
                        }
                    }
                }
            }

            if (!(locY == plocY)) {
                if (pMove) {
                    if (CommandWarp.taskIDs.containsKey(player.getName())) {
                        if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                            CommandWarp.taskIDs.remove(player.getName());
                            player.sendMessage(ChatColor.DARK_GRAY + "You moved! Warp canceled!");
                        }
                    }
                }
            }

            if (!(locZ == plocZ)) {
                if (pMove) {
                    if (CommandWarp.taskIDs.containsKey(player.getName())) {
                        if (task.isQueued(CommandWarp.taskIDs.get(player.getName()))) {
                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                            CommandWarp.taskIDs.remove(player.getName());
                            player.sendMessage(ChatColor.DARK_GRAY + "You moved! Warp canceled!");
                        }
                    }
                }
            }
        }
    }
}
