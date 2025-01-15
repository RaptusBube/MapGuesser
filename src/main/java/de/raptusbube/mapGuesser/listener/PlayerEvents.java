package de.raptusbube.mapGuesser.listener;

import de.raptusbube.mapGuesser.MapGuesser;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        if(!instance.isCanMoveOnCurrentMap() && (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ())) {
            Location loc = new Location(e.getPlayer().getWorld(), e.getFrom().getX(), e.getFrom().getY(), e.getFrom().getZ(), e.getFrom().getYaw(), e.getFrom().getPitch());
            e.getPlayer().teleport(loc);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();

        if(event.getClickedBlock() != null){
            Location location = event.getClickedBlock().getLocation();
            for(String key : instance.getFileManager().getButtonLocations().keySet()){
                if(instance.getFileManager().getButtonLocations().get(key).equals(location)){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                    player.sendMessage(instance.getPrefix() +ChatColor.GREEN+ "Es geht los!");
                    player.teleport(instance.getFileManager().getSpawnLocations().get(key));
                    instance.newCountdown(instance.getFileManager().getTime().get(key), player, instance.getFileManager().getSpawnLocations().get("lobby"));
                    instance.setCanMoveOnCurrentMap(instance.getFileManager().getCanMove().get(key));
                    break;
                }
            }
        }
    }
}
