package de.raptusbube.mapGuesser;

import de.raptusbube.mapGuesser.listener.PlayerEvents;
import de.raptusbube.mapGuesser.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Logger;

public final class MapGuesser extends JavaPlugin {


    public static MapGuesser instance;

    public static Logger logger;

    public static FileManager fileManager;

    private Thread countdownThread;

    private final String prefix = ChatColor.DARK_GREEN + "[MapGuesser] "+ ChatColor.GRAY;

    private boolean canMoveOnCurrentMap = true;





    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        fileManager = new FileManager();
        fileManager.setStandardConfig();


        registerEvents();

        fileManager.readConfig();


    }

    @Override
    public void onDisable() {

    }


    public static MapGuesser getInstance() {
        return instance;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isCanMoveOnCurrentMap() {
        return canMoveOnCurrentMap;
    }

    public void setCanMoveOnCurrentMap(boolean canMoveOnCurrentMap) {
        this.canMoveOnCurrentMap = canMoveOnCurrentMap;
    }

    public void newCountdown(int seconds, Player player, Location lobbyLocation) {
        player.sendMessage(prefix + "Du hast nun "+ChatColor.GOLD + seconds +ChatColor.GRAY+ " Sekunden die Map zu erkunden!");
        if(countdownThread != null) {
            canMoveOnCurrentMap = true;
            countdownThread.interrupt();
        }
        countdownThread = new Thread(() -> {
            try {
                Thread.sleep(1000L *(seconds-15));
            } catch (InterruptedException ignored) {
                return;
            }
            player.sendMessage(prefix + "Du hast noch "+ChatColor.DARK_RED + "15" +ChatColor.GRAY+ " Sekunden!");
            try {
                Thread.sleep(1000*15);
            } catch (InterruptedException ignored) {
                return;
            }
            player.sendMessage(prefix +ChatColor.RED+ "Die Zeit ist um!");

            Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                public void run() {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                    player.teleport(lobbyLocation);
                }
            });
            canMoveOnCurrentMap = true;

            countdownThread = null;
        });
        countdownThread.start();
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvents(this), this);

    }

}
