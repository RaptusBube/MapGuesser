package de.raptusbube.mapguesser.utils;

import de.raptusbube.mapguesser.*;
import net.kyori.adventure.text.*;
import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.*;
import org.bukkit.util.*;

import java.io.*;
import java.util.*;

public class FileManager {

    private static final String ROOT_KEY = "worlds.";
    private final HashMap<String, Location> spawnLocations;
    private final HashMap<String, Location> buttonLocations;
    private final HashMap<String, Integer> time;
    private final HashMap<String, Boolean> canMove;

    public FileManager() {
        spawnLocations = new HashMap<>();
        buttonLocations = new HashMap<>();
        time = new HashMap<>();
        canMove = new HashMap<>();
    }

    public File getConfigFile() {
        return new File(JavaPlugin.getProvidingPlugin(MapGuesser.class).getDataFolder(), "config.yml");
    }

    public FileConfiguration getConfigFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());

    }

    public void setStandardConfig() {
        FileConfiguration cfg = getConfigFileConfiguration();
        cfg.options().copyDefaults(true);

        var defaultWorld = Bukkit.getWorlds().getFirst();
        cfg.addDefault(ROOT_KEY + "lobby.spawn", new Location(defaultWorld, -54.5, 110, -25.5, -90,0));
        cfg.addDefault(ROOT_KEY + "map1.spawn", new Location(defaultWorld, -47.5, 110, -33.5, -90, 0));
        cfg.addDefault(ROOT_KEY + "map1.button", new BlockVector(-50, 110, -34));
        cfg.addDefault(ROOT_KEY + "map1.time", 20);
        cfg.addDefault(ROOT_KEY + "map1.move", true);
        cfg.addDefault(ROOT_KEY + "map2.spawn", new Location(defaultWorld, 11, 10, 19, -90, 0));
        cfg.addDefault(ROOT_KEY + "map2.button", new BlockVector(11, 1, 19));
        cfg.addDefault(ROOT_KEY + "map2.time", 20);
        cfg.addDefault(ROOT_KEY + "map2.move", true);
        cfg.addDefault(ROOT_KEY + "map3.spawn", new Location(defaultWorld, 6, 10, 20, -90, 0));
        cfg.addDefault(ROOT_KEY + "map3.button", new BlockVector(6, 1, 20));
        cfg.addDefault(ROOT_KEY + "map3.time", 20);
        cfg.addDefault(ROOT_KEY + "map3.move", true);
        cfg.addDefault(ROOT_KEY + "map4.spawn", new Location(defaultWorld, 1, 10, 20, -90, 0));
        cfg.addDefault(ROOT_KEY + "map4.button", new BlockVector(1, 1, 20));
        cfg.addDefault(ROOT_KEY + "map4.time", 20);
        cfg.addDefault(ROOT_KEY + "map4.move", true);
        cfg.addDefault(ROOT_KEY + "map5.spawn", new Location(defaultWorld, -4, 10, 20, -90, 0));
        cfg.addDefault(ROOT_KEY + "map5.button", new BlockVector(-4, 1, 20));
        cfg.addDefault(ROOT_KEY + "map5.time", 20);
        cfg.addDefault(ROOT_KEY + "map5.move", true);
        cfg.addDefault(ROOT_KEY + "map6.spawn", new Location(defaultWorld, -9, 10, 20, -90, 0));
        cfg.addDefault(ROOT_KEY + "map6.button", new BlockVector(-9, 1, 20));
        cfg.addDefault(ROOT_KEY + "map6.time", 20);
        cfg.addDefault(ROOT_KEY + "map6.move", true);
        cfg.addDefault(ROOT_KEY + "map7.spawn", new Location(defaultWorld, -14, 10, 19, -90, 0));
        cfg.addDefault(ROOT_KEY + "map7.button", new BlockVector(-14, 1, 19));
        cfg.addDefault(ROOT_KEY + "map7.time", 20);
        cfg.addDefault(ROOT_KEY + "map7.move", true);
        cfg.addDefault(ROOT_KEY + "map8.spawn", new Location(Bukkit.getWorlds().getLast(), -19, 10, 18, -90, 0));
        cfg.addDefault(ROOT_KEY + "map8.button", new BlockVector(-19, 1, 18));
        cfg.addDefault(ROOT_KEY + "map8.time", 20);
        cfg.addDefault(ROOT_KEY + "map8.move", false);
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            JavaPlugin.getProvidingPlugin(MapGuesser.class).getComponentLogger().error(Component.text("Error while saving config file"), e);
        }
    }

    public void readConfig() {
        FileConfiguration cfg = getConfigFileConfiguration();
        World defaultWorld = Objects.requireNonNull(cfg.getLocation(ROOT_KEY + "lobby.spawn")).getWorld();
        spawnLocations.put("lobby", cfg.getLocation(ROOT_KEY + "lobby.spawn"));
        spawnLocations.put("map1", cfg.getLocation(ROOT_KEY + "map1.spawn"));
        spawnLocations.put("map2", cfg.getLocation(ROOT_KEY + "map2.spawn"));
        spawnLocations.put("map3", cfg.getLocation(ROOT_KEY + "map3.spawn"));
        spawnLocations.put("map4", cfg.getLocation(ROOT_KEY + "map4.spawn"));
        spawnLocations.put("map5", cfg.getLocation(ROOT_KEY + "map5.spawn"));
        spawnLocations.put("map6", cfg.getLocation(ROOT_KEY + "map6.spawn"));
        spawnLocations.put("map7", cfg.getLocation(ROOT_KEY + "map7.spawn"));
        spawnLocations.put("map8", cfg.getLocation(ROOT_KEY + "map8.spawn"));

        buttonLocations.put("map1", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map1.button")).toLocation(defaultWorld));
        buttonLocations.put("map2", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map2.button")).toLocation(defaultWorld));
        buttonLocations.put("map3", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map3.button")).toLocation(defaultWorld));
        buttonLocations.put("map4", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map4.button")).toLocation(defaultWorld));
        buttonLocations.put("map5", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map5.button")).toLocation(defaultWorld));
        buttonLocations.put("map6", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map6.button")).toLocation(defaultWorld));
        buttonLocations.put("map7", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map7.button")).toLocation(defaultWorld));
        buttonLocations.put("map8", Objects.requireNonNull(cfg.getVector(ROOT_KEY + "map8.button")).toLocation(defaultWorld));

        time.put("map1", cfg.getInt(ROOT_KEY + "map1.time"));
        time.put("map2", cfg.getInt(ROOT_KEY + "map2.time"));
        time.put("map3", cfg.getInt(ROOT_KEY + "map3.time"));
        time.put("map4", cfg.getInt(ROOT_KEY + "map4.time"));
        time.put("map5", cfg.getInt(ROOT_KEY + "map5.time"));
        time.put("map6", cfg.getInt(ROOT_KEY + "map6.time"));
        time.put("map7", cfg.getInt(ROOT_KEY + "map7.time"));
        time.put("map8", cfg.getInt(ROOT_KEY + "map8.time"));

        canMove.put("map1", cfg.getBoolean(ROOT_KEY + "map1.move"));
        canMove.put("map2", cfg.getBoolean(ROOT_KEY + "map2.move"));
        canMove.put("map3", cfg.getBoolean(ROOT_KEY + "map3.move"));
        canMove.put("map4", cfg.getBoolean(ROOT_KEY + "map4.move"));
        canMove.put("map5", cfg.getBoolean(ROOT_KEY + "map5.move"));
        canMove.put("map6", cfg.getBoolean(ROOT_KEY + "map6.move"));
        canMove.put("map7", cfg.getBoolean(ROOT_KEY + "map7.move"));
        canMove.put("map8", cfg.getBoolean(ROOT_KEY + "map8.move"));

    }

    public Map<String, Location> getSpawnLocations() {
        return spawnLocations;
    }

    public Map<String, Location> getButtonLocations() {
        return buttonLocations;
    }

    public Map<String, Integer> getTime() {
        return time;
    }

    public Map<String, Boolean> getCanMove() {
        return canMove;
    }
}
