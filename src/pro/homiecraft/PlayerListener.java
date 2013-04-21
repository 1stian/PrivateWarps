package pro.homiecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player player = e.getPlayer();

        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
        boolean pMove = PrivateWarps.pluginST.getConfig().getBoolean("PrivateWarps.settings.Cancel-Warp-On-Player-Move", true);

        double locX = player.getLocation().getX();
        double locY = player.getLocation().getY();
        double locZ = player.getLocation().getZ();

        if (CommandWarp.pLoc.containsKey(player.getName() + "x") || CommandWarp.pLoc.containsKey(player.getName() + "y" ) || CommandWarp.pLoc.containsKey(player.getName() + "z" )){
            double plocX = CommandWarp.pLoc.get(player.getName() + "x");
            double plocY = CommandWarp.pLoc.get(player.getName() + "y");
            double plocZ = CommandWarp.pLoc.get(player.getName() + "z");

            if (!(locX == plocX)){
                if(pMove){
                    if(CommandWarp.taskIDs.containsKey(player.getName())){
                        if(task.isQueued(CommandWarp.taskIDs.get(player.getName()))){
                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                            CommandWarp.taskIDs.remove(player.getName());
                            player.sendMessage(ChatColor.DARK_GRAY + "You moved! Warp canceled!");
                        }
                    }
                }
            }

            if (!(locY == plocY)){
                if(pMove){
                    if(CommandWarp.taskIDs.containsKey(player.getName())){
                        if(task.isQueued(CommandWarp.taskIDs.get(player.getName()))){
                            task.cancelTask(CommandWarp.taskIDs.get(player.getName()));
                            CommandWarp.taskIDs.remove(player.getName());
                            player.sendMessage(ChatColor.DARK_GRAY + "You moved! Warp canceled!");
                        }
                    }
                }
            }

            if (!(locZ == plocZ)){
                if(pMove){
                    if(CommandWarp.taskIDs.containsKey(player.getName())){
                        if(task.isQueued(CommandWarp.taskIDs.get(player.getName()))){
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
