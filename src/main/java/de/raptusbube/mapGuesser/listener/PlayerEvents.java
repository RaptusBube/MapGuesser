package de.raptusbube.mapGuesser.listener;

import de.raptusbube.mapGuesser.MapGuesser;
import de.raptusbube.mapGuesser.utils.Team;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class PlayerEvents implements Listener {

    private final MapGuesser instance;

    public PlayerEvents(MapGuesser instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(instance.getFileManager().getSpawnLocations().get("lobby"));
        player.setGameMode(GameMode.ADVENTURE);
        event.joinMessage(null);
        player.sendMessage(instance.getPrefix() + "Willkommen bei "+ChatColor.YELLOW+"MapGuesser"+ChatColor.GRAY+" "+player.getName()+"!");
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Map Selector");
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.getEntity().setFoodLevel(20);
        e.getEntity().setHealth(20);
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Team team;
        if((team = instance.getTeamManager().getTeamFromPlayer(player)) != null){
            if(!team.isCanMove() && (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ())){
                Location loc = new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ(), e.getFrom().getYaw(), e.getFrom().getPitch());
                e.getPlayer().teleport(loc);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getClickedBlock() != null){
            Location location = event.getClickedBlock().getLocation();
            for(String key : instance.getFileManager().getButtonLocations().keySet()){
                if(instance.getFileManager().getButtonLocations().get(key).equals(location)){
                    Team team;
                    if((team = instance.getTeamManager().getTeamFromPlayer(player)) != null){
                        if(team.isGameRunning()){
                            team.cancelGame();
                        }
                        team.setCanMove(instance.getFileManager().getCanMove().get(key));
                        team.startGame(instance.getFileManager().getTime().get(key), instance.getFileManager().getSpawnLocations().get(key));
                        break;
                    }else{
                        if(instance.getTeamManager().isPlayerSpectator(player)){
                            player.sendMessage(instance.getPrefix() + ChatColor.RED + "Als Spectator kannst du kein Game starten!");
                        }else{
                            player.sendMessage(instance.getPrefix() + ChatColor.RED + "Du musst zunächst einem Team beitreten!");
                        }
                        return;
                    }
                }
            }
        }else{
            if(event.getItem() == null) return;
            if(event.getItem().getItemMeta() == null) return;
            String displayName = event.getItem().getItemMeta().getDisplayName();
            if(displayName.equals(ChatColor.GOLD + "Map Selector")){
                Inventory inv = Bukkit.createInventory(null, 1*9, "Wähle eine Map aus!");
                List<String> sortedMaps = instance.getFileManager().getSpawnLocations().keySet()
                        .stream()
                        .sorted((map1, map2) -> {
                            String numPart1 = map1.replaceAll("[^0-9]", "");
                            String numPart2 = map2.replaceAll("[^0-9]", "");
                            int num1 = numPart1.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(numPart1);
                            int num2 = numPart2.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(numPart2);
                            return Integer.compare(num1, num2);
                        })
                        .toList();
                for(String maps : sortedMaps){
                    if(maps.equals("lobby")) continue;
                    ItemStack item = new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(maps.toString());
                    item.setItemMeta(meta);
                    inv.addItem(item);
                }
                player.openInventory(inv);
                event.setCancelled(true);
            }else if(displayName.equals(ChatColor.RED + "Lobby (Game abbrechen)")){
                if(instance.getTeamManager().isPlayerInTeam(player)){
                    if(instance.getTeamManager().getTeamFromPlayer(player).isGameRunning()){
                        instance.getTeamManager().getTeamFromPlayer(player).cancelGame();
                    }
                }
            }else if(displayName.equals(ChatColor.GREEN + "Zurück zum Anfang")){
                if(instance.getTeamManager().isPlayerInTeam(player)){
                    player.teleport(instance.getTeamManager().getTeamFromPlayer(player).getSpawn());
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
        if(instance.getFileManager().getSpawnLocations().keySet().contains(displayName)){
            Team team;
            if((team = instance.getTeamManager().getTeamFromPlayer(player)) != null){
                team.setCanMove(instance.getFileManager().getCanMove().get(displayName));
                team.startGame(instance.getFileManager().getTime().get(displayName), instance.getFileManager().getSpawnLocations().get(displayName));
            }else{
                if(instance.getTeamManager().isPlayerSpectator(player)){
                    player.sendMessage(instance.getPrefix() + ChatColor.RED + "Als Spectator kannst du kein Game starten!");
                }else{
                    player.sendMessage(instance.getPrefix() + ChatColor.RED + "Du musst zunächst einem Team beitreten!");
                }
            }
        }
    }
}
