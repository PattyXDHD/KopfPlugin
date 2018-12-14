package de.pattyxdhd.kopf.listener;

import de.pattyxdhd.kopf.Main;
import de.pattyxdhd.kopf.data.Data;
import de.pattyxdhd.kopf.data.file.FileManager;
import de.pattyxdhd.kopf.data.mysql.SQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(Data.useMySQL){
            String uuid = e.getPlayer().getUniqueId().toString();
            if(SQLManager.inKopf(uuid)){
                Main.players.put(e.getPlayer(), SQLManager.getData(uuid));
            }else{
                SQLManager.addToKopf(uuid, 0);
                Main.players.put(e.getPlayer(), SQLManager.getData(uuid));
                Main.log("§6" + e.getPlayer().getName() + " §awurde zur Datenbank hinzugefügt!");
            }
        }else{
            if(!FileManager.getFile().valueExist("Users." + e.getPlayer().getUniqueId().toString() + ".Time")){
                savePlayerToFile(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(e.getMessage().equalsIgnoreCase("#patty")){
            e.setCancelled(true);
            e.getPlayer().sendMessage(Data.Prefix + "§dSystem by PattyXDHD");
            e.getPlayer().sendMessage(Data.Prefix + " §8» §byoutube.com/pattyxdhd");
        }
    }

    private void savePlayerToFile(Player p ){
        FileManager.relaodFile();
        FileManager.getFile().setValue("Users." + p.getUniqueId().toString() + ".Time", 0);
        FileManager.getFile().save();
        Main.log("§6" + p.getName() + " §awurde zur Config hinzugefügt!");
    }

}
