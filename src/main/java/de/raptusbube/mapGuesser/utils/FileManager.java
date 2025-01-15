package de.raptusbube.mapGuesser.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class FileManager {
    
    
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
        return new File("plugins/MapGuesser", "config.yml");
    }

    public FileConfiguration getConfigFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());

    }

    public void setStandardConfig() {
        FileConfiguration cfg = getConfigFileConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("lobby/spawn", "world|-54.5|110|-26.5|-90|0");
        cfg.addDefault("map1/spawn", "world|-47.5|110|-33.5|-90|0");
        cfg.addDefault("map1/button", "world|-50|110|-34");
        cfg.addDefault("map1/time", 20);
        cfg.addDefault("map1/move", true);
        cfg.addDefault("map2/spawn", "world|-47.5|110|-31.5|-90|0");
        cfg.addDefault("map2/button", "world|-50|110|-32");
        cfg.addDefault("map2/time", 20);
        cfg.addDefault("map2/move", true);
        cfg.addDefault("map3/spawn", "world|-47.5|110|-29.5|-90|0");
        cfg.addDefault("map3/button", "world|-50|110|-30");
        cfg.addDefault("map3/time", 20);
        cfg.addDefault("map3/move", true);
        cfg.addDefault("map4/spawn", "world|-47.5|110|-27.5|-90|0");
        cfg.addDefault("map4/button", "world|-50|110|-28");
        cfg.addDefault("map4/time", 20);
        cfg.addDefault("map4/move", true);
        cfg.addDefault("map5/spawn", "world|-47.5|110|-25.5|-90|0");
        cfg.addDefault("map5/button", "world|-50|110|-26");
        cfg.addDefault("map5/time", 20);
        cfg.addDefault("map5/move", true);
        cfg.addDefault("map6/spawn", "world|-47.5|110|-23.5|-90|0");
        cfg.addDefault("map6/button", "world|-50|110|-24");
        cfg.addDefault("map6/time", 20);
        cfg.addDefault("map6/move", true);
        cfg.addDefault("map7/spawn", "world|-47.5|110|-21.5|-90|0");
        cfg.addDefault("map7/button", "world|-50|110|-22");
        cfg.addDefault("map7/time", 20);
        cfg.addDefault("map7/move", true);
        cfg.addDefault("map8/spawn", "world|-47.5|110|-19.5|-90|0");
        cfg.addDefault("map8/button", "world|-50|110|-20");
        cfg.addDefault("map8/time", 20);
        cfg.addDefault("map8/move", false);
        try {
            cfg.save(getConfigFile());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void readConfig(){
        FileConfiguration cfg = getConfigFileConfiguration();
        String[] cfg1 = Objects.requireNonNull(cfg.getString("lobby/spawn")).split("\\|");
        spawnLocations.put("lobby", new Location(Bukkit.getWorld(cfg1[0]), Double.parseDouble(cfg1[1]), Double.parseDouble(cfg1[2]), Double.parseDouble(cfg1[3]), Float.parseFloat(cfg1[4]), Float.parseFloat(cfg1[5])));
        String[] cfg2 = Objects.requireNonNull(cfg.getString("map1/spawn")).split("\\|");
        spawnLocations.put("map1", new Location(Bukkit.getWorld(cfg2[0]), Double.parseDouble(cfg2[1]), Double.parseDouble(cfg2[2]), Double.parseDouble(cfg2[3]), Float.parseFloat(cfg2[4]), Float.parseFloat(cfg2[5])));
        String[] cfg3 = Objects.requireNonNull(cfg.getString("map2/spawn")).split("\\|");
        spawnLocations.put("map2", new Location(Bukkit.getWorld(cfg3[0]), Double.parseDouble(cfg3[1]), Double.parseDouble(cfg3[2]), Double.parseDouble(cfg3[3]), Float.parseFloat(cfg3[4]), Float.parseFloat(cfg3[5])));
        String[] cfg4 = Objects.requireNonNull(cfg.getString("map3/spawn")).split("\\|");
        spawnLocations.put("map3", new Location(Bukkit.getWorld(cfg4[0]), Double.parseDouble(cfg4[1]), Double.parseDouble(cfg4[2]), Double.parseDouble(cfg4[3]), Float.parseFloat(cfg4[4]), Float.parseFloat(cfg4[5])));
        String[] cfg5 = Objects.requireNonNull(cfg.getString("map4/spawn")).split("\\|");
        spawnLocations.put("map4", new Location(Bukkit.getWorld(cfg5[0]), Double.parseDouble(cfg5[1]), Double.parseDouble(cfg5[2]), Double.parseDouble(cfg5[3]), Float.parseFloat(cfg5[4]), Float.parseFloat(cfg5[5])));
        String[] cfg6 = Objects.requireNonNull(cfg.getString("map5/spawn")).split("\\|");
        spawnLocations.put("map5", new Location(Bukkit.getWorld(cfg6[0]), Double.parseDouble(cfg6[1]), Double.parseDouble(cfg6[2]), Double.parseDouble(cfg6[3]), Float.parseFloat(cfg6[4]), Float.parseFloat(cfg6[5])));
        String[] cfg7 = Objects.requireNonNull(cfg.getString("map6/spawn")).split("\\|");
        spawnLocations.put("map6", new Location(Bukkit.getWorld(cfg7[0]), Double.parseDouble(cfg7[1]), Double.parseDouble(cfg7[2]), Double.parseDouble(cfg7[3]), Float.parseFloat(cfg7[4]), Float.parseFloat(cfg7[5])));
        String[] cfg8 = Objects.requireNonNull(cfg.getString("map7/spawn")).split("\\|");
        spawnLocations.put("map7", new Location(Bukkit.getWorld(cfg8[0]), Double.parseDouble(cfg8[1]), Double.parseDouble(cfg8[2]), Double.parseDouble(cfg8[3]), Float.parseFloat(cfg8[4]), Float.parseFloat(cfg8[5])));
        String[] cfg9 = Objects.requireNonNull(cfg.getString("map8/spawn")).split("\\|");
        spawnLocations.put("map8", new Location(Bukkit.getWorld(cfg9[0]), Double.parseDouble(cfg9[1]), Double.parseDouble(cfg9[2]), Double.parseDouble(cfg9[3]), Float.parseFloat(cfg9[4]), Float.parseFloat(cfg9[5])));
        
        String[] cfg10 = Objects.requireNonNull(cfg.getString("map1/button")).split("\\|");
        buttonLocations.put("map1", new Location(Bukkit.getWorld(cfg10[0]), Double.parseDouble(cfg10[1]), Double.parseDouble(cfg10[2]), Double.parseDouble(cfg10[3])));
        String[] cfg11 = Objects.requireNonNull(cfg.getString("map2/button")).split("\\|");
        buttonLocations.put("map2", new Location(Bukkit.getWorld(cfg11[0]), Double.parseDouble(cfg11[1]), Double.parseDouble(cfg11[2]), Double.parseDouble(cfg11[3])));
        String[] cfg12 = Objects.requireNonNull(cfg.getString("map3/button")).split("\\|");
        buttonLocations.put("map3", new Location(Bukkit.getWorld(cfg12[0]), Double.parseDouble(cfg12[1]), Double.parseDouble(cfg12[2]), Double.parseDouble(cfg12[3])));
        String[] cfg13 = Objects.requireNonNull(cfg.getString("map4/button")).split("\\|");
        buttonLocations.put("map4", new Location(Bukkit.getWorld(cfg13[0]), Double.parseDouble(cfg13[1]), Double.parseDouble(cfg13[2]), Double.parseDouble(cfg13[3])));
        String[] cfg14 = Objects.requireNonNull(cfg.getString("map5/button")).split("\\|");
        buttonLocations.put("map5", new Location(Bukkit.getWorld(cfg14[0]), Double.parseDouble(cfg14[1]), Double.parseDouble(cfg14[2]), Double.parseDouble(cfg14[3])));
        String[] cfg15 = Objects.requireNonNull(cfg.getString("map6/button")).split("\\|");
        buttonLocations.put("map6", new Location(Bukkit.getWorld(cfg15[0]), Double.parseDouble(cfg15[1]), Double.parseDouble(cfg15[2]), Double.parseDouble(cfg15[3])));
        String[] cfg16 = Objects.requireNonNull(cfg.getString("map7/button")).split("\\|");
        buttonLocations.put("map7", new Location(Bukkit.getWorld(cfg16[0]), Double.parseDouble(cfg16[1]), Double.parseDouble(cfg16[2]), Double.parseDouble(cfg16[3])));
        String[] cfg17 = Objects.requireNonNull(cfg.getString("map8/button")).split("\\|");
        buttonLocations.put("map8", new Location(Bukkit.getWorld(cfg17[0]), Double.parseDouble(cfg17[1]), Double.parseDouble(cfg17[2]), Double.parseDouble(cfg17[3])));

        
        
        time.put("map1", cfg.getInt("map1/time"));
        time.put("map2", cfg.getInt("map2/time"));
        time.put("map3", cfg.getInt("map3/time"));
        time.put("map4", cfg.getInt("map4/time"));
        time.put("map5", cfg.getInt("map5/time"));
        time.put("map6", cfg.getInt("map6/time"));
        time.put("map7", cfg.getInt("map7/time"));
        time.put("map8", cfg.getInt("map8/time"));

        canMove.put("map1", cfg.getBoolean("map1/move"));
        canMove.put("map2", cfg.getBoolean("map2/move"));
        canMove.put("map3", cfg.getBoolean("map3/move"));
        canMove.put("map4", cfg.getBoolean("map4/move"));
        canMove.put("map5", cfg.getBoolean("map5/move"));
        canMove.put("map6", cfg.getBoolean("map6/move"));
        canMove.put("map7", cfg.getBoolean("map7/move"));
        canMove.put("map8", cfg.getBoolean("map8/move"));

        
    }


    public HashMap<String, Location> getSpawnLocations() {
        return spawnLocations;
    }

    public HashMap<String, Location> getButtonLocations() {
        return buttonLocations;
    }

    public HashMap<String, Integer> getTime() {
        return time;
    }

    public HashMap<String, Boolean> getCanMove() {
        return canMove;
    }
}
