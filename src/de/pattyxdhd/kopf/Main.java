package de.pattyxdhd.kopf;

import de.pattyxdhd.kopf.Utils.KopfPlayer;
import de.pattyxdhd.kopf.commands.kopf_command;
import de.pattyxdhd.kopf.commands.kreload_command;
import de.pattyxdhd.kopf.data.Data;
import de.pattyxdhd.kopf.data.file.FileManager;
import de.pattyxdhd.kopf.data.mysql.MySQL;
import de.pattyxdhd.kopf.listener.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    public static HashMap<Player, KopfPlayer> players = new HashMap<>();

    private static Main inctance;
    public static Main getInctance(){
        return inctance;
    }

    @Override
    public void onEnable() {
        inctance = this;
        try {
            loadFile();
            loadCommands();
            loadListener(Bukkit.getPluginManager());
            if(Data.useMySQL){
                loadMySQL();
                Bukkit.getOnlinePlayers().forEach(p -> p.kickPlayer("§cServer reload!"));
            }
        }catch (Exception e1){
            e1.printStackTrace();
            log("§4Fehler: §cPlugin konnte nicht geladen werden.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }finally{
            log("§aPlugin geladen.");
        }
    }

    @Override
    public void onDisable() {
        log("§cPlugin entladen.");
    }

    private void loadCommands(){
        getCommand("kopf").setExecutor(new kopf_command());
        getCommand("kreload").setExecutor(new kreload_command());
    }

    private void loadListener(PluginManager pm){
        pm.registerEvents(new JoinListener(),this);
    }

    private void loadFile(){
        FileManager.loadFile();
        FileManager.readFile();
    }

    private void loadMySQL(){
        MySQL.connect();
        MySQL.update("CREATE TABLE IF NOT EXISTS Kopf (UUID VARCHAR(255),Time LONG)");
    }

    public static void log(String msg){
        Bukkit.getConsoleSender().sendMessage(Data.Prefix + msg);
    }

}
