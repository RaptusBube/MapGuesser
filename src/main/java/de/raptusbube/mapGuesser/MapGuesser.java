package de.raptusbube.mapGuesser;

import de.raptusbube.mapGuesser.command.MapGuesserCMD;
import de.raptusbube.mapGuesser.listener.PlayerEvents;
import de.raptusbube.mapGuesser.utils.FileManager;
import de.raptusbube.mapGuesser.utils.Team;
import de.raptusbube.mapGuesser.utils.TeamManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class MapGuesser extends JavaPlugin {

    public static MapGuesser instance;

    public static Logger logger;

    public static FileManager fileManager;

    public TeamManager teamManager;

    private final String prefix = ChatColor.DARK_GREEN + "[TerraGuessr] "+ ChatColor.GRAY;

    private boolean canMoveOnCurrentMap = true;

    private List<Team> teams;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        fileManager = new FileManager();
        fileManager.setStandardConfig();
        teams = new ArrayList<>();
        teamManager = new TeamManager(this);

        registerEvents();
        registerCommands();

        fileManager.readConfig();
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

    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team){
        teams.add(team);
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvents(this), this);
    }

    private void registerCommands(){
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("tg", "TerraGuessr Command", List.of("mg"), new MapGuesserCMD(this));
        });
    }
}
