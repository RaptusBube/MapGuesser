package de.raptusbube.mapGuesser.utils;

import de.raptusbube.mapGuesser.MapGuesser;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Team {

    private List<Player> players;
    private List<Player> spectators;
    private final int teamId;
    private boolean canMove;
    private final MapGuesser instance;
    private int taskId;
    private boolean isGameRunning;

    public Team(List<Player> players, List<Player> spectators, int teamId) {
        this.players = players;
        this.spectators = spectators;
        this.teamId = teamId;
        canMove = true;
        MapGuesser.getInstance().addTeam(this);
        instance = MapGuesser.getInstance();
        isGameRunning = false;
    }

    public void startGame(int duration, Location location) {
        isGameRunning = true;
        ItemStack itemStack = new ItemStack(Material.RED_BED);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Lobby");
        itemStack.setItemMeta(itemMeta);
        players.forEach(player -> player.getInventory().setItem(0, itemStack));
        players.forEach(player -> player.teleport(location));
        players.forEach(player -> hidePlayersNotInTeam(player, this));
        spectators.forEach(player -> player.setGameMode(GameMode.SPECTATOR));
        spectators.forEach(player -> player.teleport(location));
         taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            int remainingTime = duration+5;
            final boolean origCanMove = canMove;
            public void run(){
                if(remainingTime > duration) {
                    canMove = false;
                    players.forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*3, 0)));
                    players.forEach(player -> player.sendTitle("§c" + (remainingTime - duration), "§6Get ready!", 10, 20, 10));
                    players.forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f));
                    remainingTime--;
                }else if(remainingTime == duration){
                    players.forEach(player -> player.sendTitle("§aGo!", "", 10, 40, 10));
                    players.forEach(player -> player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType())));
                    players.forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f));
                    players.forEach(player -> player.sendMessage(instance.getPrefix() + "Du hast nun "+ChatColor.GOLD + duration +ChatColor.GRAY+ " Sekunden die Map zu erkunden!"));
                    remainingTime--;
                }else{
                    canMove = origCanMove;
                    if(remainingTime == 1){
                        isGameRunning = false;
                        Location lobby = instance.getFileManager().getSpawnLocations().get("lobby");
                        players.forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0)));
                        players.forEach(player -> player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f));
                        players.forEach(player -> player.sendMessage(instance.getPrefix() + ChatColor.RED+ "Die Zeit ist um!"));
                        players.forEach(player -> player.teleport(lobby));
                        spectators.forEach(player -> player.teleport(lobby));
                        players.forEach(player -> showPlayers(player));
                        spectators.forEach(player -> player.setGameMode(GameMode.ADVENTURE));
                        ItemStack itemStack = new ItemStack(Material.COMPASS);
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(ChatColor.GOLD + "Map Selector");
                        itemStack.setItemMeta(itemMeta);
                        players.forEach(player -> player.getInventory().setItem(0, itemStack));
                        Bukkit.getScheduler().cancelTask(taskId);
                    }else{
                        remainingTime--;
                        if(remainingTime % 30 == 0){
                            players.forEach(player -> player.sendMessage(instance.getPrefix() + "Du hast noch "+ChatColor.GREEN + remainingTime +ChatColor.GRAY+ " Sekunden!"));
                        }else if(remainingTime < 6){
                            players.forEach(player -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f));
                            players.forEach(player -> player.sendMessage(instance.getPrefix() + "Du hast noch "+ChatColor.DARK_RED + remainingTime +ChatColor.GRAY+ " Sekunden!"));
                        }
                    }
                }
            }
        }, 0, 20);
    }

    public void cancelGame(){
        isGameRunning = false;
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Map Selector");
        itemStack.setItemMeta(itemMeta);
        players.forEach(this::showPlayers);
        players.forEach(player -> player.getInventory().setItem(0, itemStack));
        Location lobby = instance.getFileManager().getSpawnLocations().get("lobby");
        players.forEach(player -> player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0)));
        players.forEach(player -> player.sendMessage(instance.getPrefix() + ChatColor.RED+ "Das Spiel wurde vorzeitig beendet!"));
        players.forEach(player -> player.teleport(lobby));
        spectators.forEach(player -> player.teleport(lobby));
        spectators.forEach(player -> player.setGameMode(GameMode.ADVENTURE));
        Bukkit.getScheduler().cancelTask(taskId);
        canMove = true;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setSpectators(List<Player> spectators) {
        this.spectators = spectators;
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

    public void hidePlayersNotInTeam(Player currentPlayer, Team currentTeam) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (!currentTeam.getPlayers().contains(player)) {
                currentPlayer.hidePlayer(instance, player);
            }
        }
    }

    public void showPlayers(Player currentPlayer) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            currentPlayer.showPlayer(instance, player);
        }
    }
}
