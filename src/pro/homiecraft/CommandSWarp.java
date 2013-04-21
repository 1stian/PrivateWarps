package pro.homiecraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created with IntelliJ IDEA.
 * User: Ellen Thing
 * Date: 19-04-13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class CommandSWarp implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String commandLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("pswarp")){
            if (args.length < 2){

            }else{
                String warpName = args[0].toLowerCase();
                String pName = args[1].toLowerCase();


            }
        }
        return true;
    }

}
