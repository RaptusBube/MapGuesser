package de.raptusbube.mapguesser;

import de.raptusbube.mapguesser.command.*;
import de.raptusbube.mapguesser.listener.*;
import de.raptusbube.mapguesser.utils.*;
import io.papermc.paper.command.brigadier.*;
import io.papermc.paper.plugin.lifecycle.event.*;
import io.papermc.paper.plugin.lifecycle.event.types.*;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

import java.util.*;

public final class MapGuesser extends JavaPlugin {

    public static FileManager fileManager;

    public TeamManager teamManager;

    private static final Component PREFIX = Component.text("[TerraGuessr] ", NamedTextColor.DARK_GREEN);

    private List<Team> teams;

    @Override
    public void onEnable() {
        fileManager = new FileManager(); // TODO make it a singleton
        fileManager.setStandardConfig();
        teams = new ArrayList<>();
        teamManager = new TeamManager();

        registerEvents();
        registerCommands();

        fileManager.readConfig();
    }

    public static MapGuesser getInstance() {
        return JavaPlugin.getPlugin(MapGuesser.class);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public Component getPrefix() {
        return PREFIX;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    private void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvents(this), this);
    }

    private void registerCommands() {
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("tg", "TerraGuessr Command", List.of("mg"), new MapGuesserCMD());
        });
    }
}
