package de.raptusbube.mapguesser.utils;

import de.raptusbube.mapguesser.*;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import net.kyori.adventure.title.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.time.*;

public class GameTask extends BukkitRunnable {
    final boolean origCanMove;
    final Team team;
    private final int duration;
    int remainingTime;

    public GameTask(int duration, Team team) {
        this.duration = duration;
        remainingTime = duration + 5;
        origCanMove = team.isCanMove();
        this.team = team;
    }

    public void run() {
        if (remainingTime > duration) {
            team.setCanMove(false);
            team.getPlayers().forEach(player -> {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 0));
                player.showTitle(Title.title(Component.text(remainingTime - duration, NamedTextColor.RED),
                    Component.text("Get ready!", NamedTextColor.GOLD),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(1), Duration.ofMillis(500))));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
            });
            remainingTime--;
        } else if (remainingTime == duration) {
            team.getPlayers().forEach(player -> {
                player.showTitle(Title.title(Component.text("Go!", NamedTextColor.GREEN),
                    Component.empty(),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(2), Duration.ofMillis(500))));
                player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                player.sendMessage(Component.text("Du hast nun ", NamedTextColor.GRAY)
                    .append(Component.text(duration, NamedTextColor.GOLD))
                    .append(Component.text(" Sekunden die Map zu erkunden!", NamedTextColor.GRAY)));
            });
            remainingTime--;
        } else {
            team.setCanMove(origCanMove);
            if (remainingTime == 1) {
                team.setGameRunning(false);
                Location lobby = MapGuesser.getInstance().getFileManager().getSpawnLocations().get("lobby");
                team.getPlayers().forEach(player -> {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                    player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
                    player.sendMessage(MapGuesser.getInstance().getPrefix().append(Component.text("Die Zeit ist um!", NamedTextColor.RED)));
                    player.teleport(lobby);
                    player.setFlying(false);
                    team.showPlayers(player);
                    player.getInventory().clear();
                });
                team.getSpectators().forEach(player -> {
                    player.teleport(lobby);
                    player.setGameMode(GameMode.ADVENTURE);
                });
                ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.displayName(Component.text("Map Selector", NamedTextColor.GOLD));
                team.setCanMove(true);
                itemStack.setItemMeta(itemMeta);
                team.getPlayers().forEach(player -> {
                    player.getInventory().setItem(0, itemStack);
                    player.getInventory().clear(8);
                });
                this.cancel();
            } else {
                remainingTime--;
                if (remainingTime % 30 == 0) {
                    team.getPlayers().forEach(player -> remainingTimeMessage(player, NamedTextColor.GREEN));
                    team.getSpectators().forEach(player -> remainingTimeMessage(player, NamedTextColor.GREEN));
                } else if (remainingTime < 6) {
                    team.getPlayers().forEach(player -> {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        remainingTimeMessage(player, NamedTextColor.DARK_RED);
                    });
                    team.getSpectators().forEach(player -> {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        remainingTimeMessage(player, NamedTextColor.DARK_RED);
                    });
                }
            }
        }
    }

    private void remainingTimeMessage(@NotNull Player player, NamedTextColor color) {
        player.sendMessage(MapGuesser.getInstance()
            .getPrefix()
            .append(Component.text("Du hast noch ", NamedTextColor.GRAY)
                .append(Component.text(remainingTime, color)
                    .append(Component.text(" Sekunden!", NamedTextColor.GRAY)))));
    }
}
