package de.raptusbube.mapguesser.utils;

import de.raptusbube.mapguesser.*;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;

import java.util.*;

public class Team {

    private final List<Player> players;
    private final List<Player> spectators;
    private final int teamId;
    private boolean canMove;
    private int taskId;
    private boolean isGameRunning;
    private Location spawn;

    public Team(List<Player> players, List<Player> spectators, int teamId) {
        this.players = players;
        this.spectators = spectators;
        this.teamId = teamId;
        canMove = true;
        MapGuesser.getInstance().addTeam(this);
        isGameRunning = false;
    }

    public void startGame(int duration, Location location) {
        isGameRunning = true;
        spawn = location;
        ItemStack itemStack = new ItemStack(Material.RED_BED);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Lobby (Game abbrechen)", NamedTextColor.RED));
        itemStack.setItemMeta(itemMeta);
        ItemStack itemStack1 = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta1 = itemStack1.getItemMeta();
        itemMeta1.displayName(Component.text("Zurück zum Anfang", NamedTextColor.GREEN));
        itemStack1.setItemMeta(itemMeta1);
        players.forEach(player -> {
            player.getInventory().clear(0);
            player.teleport(location);
        });
        Bukkit.getScheduler().runTaskLater(MapGuesser.getInstance(), () -> players.forEach(player -> {
            player.getInventory().setItem(8, itemStack);
            player.getInventory().setItem(4, itemStack1);
        }), 5);

        if (canMove) {
            players.forEach(player -> hidePlayersNotInTeam(player, this));
        } else {
            players.forEach(this::hidePlayers);
        }
        spectators.forEach(player -> {
            player.teleport(location);
            Bukkit.getScheduler().runTaskLater(MapGuesser.getInstance(), () -> player.setGameMode(GameMode.SPECTATOR), 1);
        });
        taskId = new GameTask(duration, this).runTaskTimer(MapGuesser.getInstance(), 0, 20).getTaskId();
    }

    public void cancelGame() {
        isGameRunning = false;
        Location lobby = MapGuesser.getInstance().getFileManager().getSpawnLocations().get("lobby");
        players.forEach(player -> {
            showPlayers(player);
            player.setFlying(false);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
            player.sendMessage(MapGuesser.getInstance()
                .getPrefix()
                .append(Component.text("Das Spiel wurde vorzeitig " + "beendet!", NamedTextColor.RED)));
            player.teleport(lobby);
            player.getInventory().clear();
        });
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text("Map Selector", NamedTextColor.GOLD));
        itemStack.setItemMeta(itemMeta);
        players.forEach(player -> {
            player.getInventory().clear(8);
            player.getInventory().setItem(0, itemStack);
        });
        canMove = true;
        spectators.forEach(player -> {
            player.teleport(lobby);
            player.setGameMode(GameMode.ADVENTURE);
        });
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addSpectator(Player player) {
        spectators.add(player);
    }

    public void removeSpectator(Player player) {
        spectators.remove(player);
    }

    public List<Player> getSpectators() {
        return spectators;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public void hidePlayersNotInTeam(Player currentPlayer, Team currentTeam) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!currentTeam.getPlayers().contains(player)) {
                currentPlayer.hidePlayer(MapGuesser.getInstance(), player);
            }
        }
    }

    public void hidePlayers(Player currentPlayer) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            currentPlayer.hidePlayer(MapGuesser.getInstance(), player);
        }
    }

    public void showPlayers(Player currentPlayer) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            currentPlayer.showPlayer(MapGuesser.getInstance(), player);
        }
    }
}
