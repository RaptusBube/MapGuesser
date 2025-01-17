package de.raptusbube.mapguesser.utils;

import de.raptusbube.mapguesser.*;
import org.bukkit.entity.*;

public class TeamManager {

    public boolean addPlayerToTeam(Player player, int teamID) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getTeamId() == teamID) {
                team.addPlayer(player);
                return true;
            }
        }
        return false;
    }

    public boolean addSpectatorToTeam(Player player, int teamID) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getTeamId() == teamID) {
                team.addSpectator(player);
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInTeam(Player player) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getPlayers().contains(player) || team.getSpectators().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public Team getTeamFromPlayer(Player player) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public void removePlayerFromTeam(Player player) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getPlayers().contains(player)) {
                team.removePlayer(player);
            }
            if (team.getSpectators().contains(player)) {
                team.removeSpectator(player);
            }
        }
    }

    public boolean isPlayerSpectator(Player player) {
        for (Team team : MapGuesser.getInstance().getTeams()) {
            if (team.getSpectators().contains(player)) {
                return true;
            }
        }
        return false;
    }
}
