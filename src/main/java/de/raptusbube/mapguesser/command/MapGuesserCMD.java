package de.raptusbube.mapguesser.command;

import de.raptusbube.mapguesser.*;
import de.raptusbube.mapguesser.utils.*;
import io.papermc.paper.command.brigadier.*;
import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MapGuesserCMD implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] strings) {
        if (stack.getSender() instanceof Player player) {
            if (strings.length == 0) {
                player.sendMessage(MapGuesser.getInstance()
                    .getPrefix()
                    .append(Component.text("/tg join <ID> ", NamedTextColor.GRAY))
                    .append(Component.text("Du " + "betrittst ein Team (ID Optional)", NamedTextColor.DARK_AQUA))
                    .append(Component.newline())
                    .append(MapGuesser.getInstance().getPrefix().append(Component.text("/tg sjoin <ID> ", NamedTextColor.GRAY)))
                    .append(Component.text("Du betrittst ein Team als Spectator (ID Optional)", NamedTextColor.DARK_AQUA)));
                player.sendMessage(MapGuesser.getInstance()
                    .getPrefix()
                    .append(Component.text("/tg leave ", NamedTextColor.GRAY))
                    .append(Component.text(" Du verlässt " + "ein Team.", NamedTextColor.DARK_AQUA)));
            } else {
                if (strings[0].equalsIgnoreCase("join")) {
                    if (MapGuesser.getInstance().getTeamManager().isPlayerInTeam(player)) {
                        player.sendMessage(MapGuesser.getInstance()
                            .getPrefix()
                            .append(Component.text("Du bist bereits in einem Team!", NamedTextColor.GRAY)));
                        return;
                    }
                    if (strings.length == 2) {
                        int i;
                        try {
                            i = Integer.parseInt(strings[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("ID muss eine Ganzzahl sein!", NamedTextColor.GRAY)));
                            return;
                        }
                        if (MapGuesser.getInstance().getTeamManager().addPlayerToTeam(player, i)) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun im Team Nr." + i, NamedTextColor.GRAY)));
                        } else {
                            if (i > 0) {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), i);
                                team.addPlayer(player);
                                player.sendMessage(MapGuesser.getInstance()
                                    .getPrefix()
                                    .append(Component.text("Du bist nun im Team Nr." + i, NamedTextColor.GRAY)));
                            } else {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                                team.addPlayer(player);
                                player.sendMessage(MapGuesser.getInstance()
                                    .getPrefix()
                                    .append(Component.text("Du bist nun im default Team", NamedTextColor.GRAY)));
                            }
                        }
                    } else {
                        if (MapGuesser.getInstance().getTeamManager().addPlayerToTeam(player, 0)) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun im default Team", NamedTextColor.GRAY)));
                        } else {
                            Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                            team.addPlayer(player);
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun im default Team", NamedTextColor.GRAY)));
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("sjoin")) {
                    if (strings.length == 2) {
                        int i;
                        try {
                            i = Integer.parseInt(strings[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("ID muss eine Ganzzahl sein!", NamedTextColor.GRAY)));
                            return;
                        }
                        if (MapGuesser.getInstance().getTeamManager().addSpectatorToTeam(player, i)) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun als Spectator im Team Nr." + i, NamedTextColor.GRAY)));
                        } else {
                            if (i > 0) {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), i);
                                team.addSpectator(player);
                                player.sendMessage(MapGuesser.getInstance()
                                    .getPrefix()
                                    .append(Component.text("Du bist nun als Spectator im Team Nr" + "." + i, NamedTextColor.GRAY)));
                            } else {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                                team.addSpectator(player);
                                player.sendMessage(MapGuesser.getInstance()
                                    .getPrefix()
                                    .append(Component.text("Du bist nun als Spectator im default" + " Team", NamedTextColor.GRAY)));
                            }
                        }
                    } else {
                        if (MapGuesser.getInstance().getTeamManager().addSpectatorToTeam(player, 0)) {
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun als Spectator im default Team", NamedTextColor.GRAY)));
                        } else {
                            Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                            team.addSpectator(player);
                            player.sendMessage(MapGuesser.getInstance()
                                .getPrefix()
                                .append(Component.text("Du bist nun als Spectator im default Team", NamedTextColor.GRAY)));
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("leave")) {
                    if (MapGuesser.getInstance().getTeamManager().isPlayerInTeam(player)) {
                        MapGuesser.getInstance().getTeamManager().removePlayerFromTeam(player);
                        player.sendMessage(MapGuesser.getInstance()
                            .getPrefix()
                            .append(Component.text("Du hast dein Team verlassen", NamedTextColor.GRAY)));
                    } else {
                        player.sendMessage(MapGuesser.getInstance()
                            .getPrefix()
                            .append(Component.text("Du bist in keinem Team", NamedTextColor.GRAY)));
                    }
                }
            }
        }
    }
}
