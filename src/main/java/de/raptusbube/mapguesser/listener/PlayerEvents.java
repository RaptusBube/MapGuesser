package de.raptusbube.mapguesser.listener;

import de.raptusbube.mapguesser.*;
import de.raptusbube.mapguesser.utils.*;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PlayerEvents implements Listener {

    public static final String SELECTOR = "Map Selector";
    private final MapGuesser instance;

    public PlayerEvents(MapGuesser instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        player.clearActivePotionEffects();
        player.teleport(instance.getFileManager().getSpawnLocations().get("lobby"));
        player.setGameMode(GameMode.ADVENTURE);
        event.joinMessage(null);
        player.sendMessage(instance.getPrefix()
            .append(Component.text("Willkommen bei ", NamedTextColor.GRAY))
            .append(Component.text("TerraGuessr ", NamedTextColor.YELLOW))
            .append(Component.text(player.getName() + "!", NamedTextColor.GRAY)));
        ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(SELECTOR, NamedTextColor.GOLD));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(0, itemStack);
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        if (instance.getTeamManager().isPlayerInTeam(event.getPlayer()) && instance.getTeamManager().getTeamFromPlayer(event.getPlayer()).isGameRunning()) {
            instance.getTeamManager().getTeamFromPlayer(event.getPlayer()).cancelGame();
        }
        event.quitMessage(Component.empty());
    }

    @EventHandler
    public void onHunger(@NotNull FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.getEntity().setFoodLevel(20);
        e.getEntity().setHealth(20);
    }

    @EventHandler
    public void onDamage(@NotNull EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Team team;
        if ((team = instance.getTeamManager().getTeamFromPlayer(player)) != null && !team.isCanMove() &&
            (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY() || e.getFrom().getZ() != e.getTo().getZ())) {
            Location loc = new Location(e.getPlayer().getWorld(),
                e.getFrom().getX(),
                e.getFrom().getY(),
                e.getFrom().getZ(),
                e.getTo().getYaw(),
                e.getTo().getPitch());
            e.getPlayer().teleport(loc);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null) {
            Location location = event.getClickedBlock().getLocation();
            for (String key : instance.getFileManager().getButtonLocations().keySet()) {
                if (instance.getFileManager().getButtonLocations().get(key).equals(location)) {
                    Team team;
                    if ((team = instance.getTeamManager().getTeamFromPlayer(player)) != null) {
                        if (team.isGameRunning()) {
                            team.cancelGame();
                        }
                        team.setCanMove(instance.getFileManager().getCanMove().get(key));
                        team.startGame(instance.getFileManager().getTime().get(key), instance.getFileManager().getSpawnLocations().get(key));
                    } else {
                        if (instance.getTeamManager().isPlayerSpectator(player)) {
                            player.sendMessage(instance.getPrefix()
                                .append(Component.text("Als Spectator kannst du kein Game starten!", NamedTextColor.RED)));
                        } else {
                            player.sendMessage(instance.getPrefix()
                                .append(Component.text("Du musst zunächst einem Team beitreten!", NamedTextColor.RED)));
                        }
                    }
                    return;
                }
            }
        }
        if (event.getItem() == null) return;
        if (event.getItem().getItemMeta() == null) return;
        Component displayName = event.getItem().getItemMeta().displayName();
        if (displayName == null) {
            displayName = Component.empty();
        }
        if (displayName.equals(Component.text(SELECTOR, NamedTextColor.GOLD))) {
            Inventory inv = Bukkit.createInventory(null, 9, Component.text("Wähle eine Map aus!"));
            List<String> sortedMaps = instance.getFileManager().getSpawnLocations().keySet().stream().sorted((map1, map2) -> {
                String numPart1 = map1.replaceAll("\\D", "");
                String numPart2 = map2.replaceAll("\\D", "");
                int num1 = numPart1.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(numPart1);
                int num2 = numPart2.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(numPart2);
                return Integer.compare(num1, num2);
            }).toList();
            for (String maps : sortedMaps) {
                if (maps.equals("lobby")) continue;
                ItemStack item = new ItemStack(Material.GRASS_BLOCK);
                ItemMeta meta = item.getItemMeta();
                meta.displayName(Component.text(maps));
                item.setItemMeta(meta);
                inv.addItem(item);
            }
            player.openInventory(inv);
            event.setCancelled(true);
        } else if (displayName.equals(Component.text("Lobby (Game abbrechen)", NamedTextColor.RED))) {
            if (instance.getTeamManager().isPlayerInTeam(player) && instance.getTeamManager().getTeamFromPlayer(player).isGameRunning()) {
                instance.getTeamManager().getTeamFromPlayer(player).cancelGame();
            }
        } else if (displayName.equals(Component.text("Zurück zum Anfang", NamedTextColor.GREEN)) && // TODO Fix bug in warup phase when you
                // cannot move anymore
                instance.getTeamManager().isPlayerInTeam(player)) {
            player.teleport(instance.getTeamManager().getTeamFromPlayer(player).getSpawn());
        }
    }

    @EventHandler
    public void onInventoryClick(@NotNull InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        String displayName = ((TextComponent) Objects.requireNonNull(event.getCurrentItem().getItemMeta().displayName())).content();
        if (instance.getFileManager().getSpawnLocations().containsKey(displayName)) {
            Team team;
            if ((team = instance.getTeamManager().getTeamFromPlayer(player)) != null) {
                team.setCanMove(instance.getFileManager().getCanMove().get(displayName));
                team.startGame(instance.getFileManager().getTime().get(displayName),
                    instance.getFileManager().getSpawnLocations().get(displayName));
            } else {
                if (instance.getTeamManager().isPlayerSpectator(player)) {
                    player.sendMessage(instance.getPrefix().append(Component.text("Als Spectator kannst du kein Game starten!", NamedTextColor.RED)));
                } else {
                    player.sendMessage(instance.getPrefix().append(Component.text("Du musst zunächst einem Team beitreten!", NamedTextColor.RED)));
                }
            }
        }
    }

    @EventHandler
    public void onItemDrop(@NotNull PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().displayName() instanceof TranslatableComponent c && c.arguments().getFirst().asComponent().children().getFirst()   instanceof TextComponent component &&
            List.of(SELECTOR, "Lobby (Game abbrechen)", "Zurück zum Anfang").contains(component.content())) {
                event.getPlayer().sendMessage(instance.getPrefix().append(Component.text("Du kannst dieses Item nicht droppen!", NamedTextColor.RED)));
                event.setCancelled(true);
        }
    }
}
