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

    //public static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player player = e.getPlayer();

        BukkitScheduler task = PrivateWarps.pluginST.getServer().getScheduler();
        boolean pMove = PrivateWarps.pluginST.getConfig().getBoolean("PrivateWarps.settings.Cancel-Warp-On-Player-Move", true);
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
