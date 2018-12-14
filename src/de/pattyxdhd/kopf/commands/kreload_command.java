package de.pattyxdhd.kopf.commands;

import de.pattyxdhd.kopf.data.Data;
import de.pattyxdhd.kopf.data.file.FileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class kreload_command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if(commandSender.hasPermission("kopf.reload") || commandSender.hasPermission("kopf.admin")){
            FileManager.relaodFile();
            commandSender.sendMessage(Data.Prefix + "§aDie Config wurde erneut eingelesen.");
        }else{
            commandSender.sendMessage(Data.noPerm);
        }


        return false;
    }
}
