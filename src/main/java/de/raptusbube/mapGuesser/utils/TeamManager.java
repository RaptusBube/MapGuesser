package de.raptusbube.mapGuesser.utils;

import de.raptusbube.mapGuesser.MapGuesser;
import org.bukkit.entity.Player;

public class TeamManager {

    public MapGuesser instance;

    public TeamManager(MapGuesser instance){
        this.instance = instance;
    }

    public boolean addPlayerToTeam(Player player, int teamID){
        for(Team team : instance.getTeams()){
            if(team.getTeamId() == teamID){
                team.addPlayer(player);
                return true;
            }
        }
        return false;
    }

    public boolean addSpectatorToTeam(Player player, int teamID){
        for(Team team : instance.getTeams()){
            if(team.getTeamId() == teamID){
                team.addSpectator(player);
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInTeam(Player player, int teamID){
        for(Team team : instance.getTeams()){
            if(team.getTeamId() == teamID){
                return team.getPlayers().contains(player);
            }
        }
        return false;
    }

    public boolean isPlayerInTeam(Player player){
        for(Team team : instance.getTeams()){
            if(team.getPlayers().contains(player) || team.getSpectators().contains(player)){
                return true;
            }

        }
        return false;
    }

    public Team getTeamFromPlayer(Player player){
        for(Team team : instance.getTeams()){
            if(team.getPlayers().contains(player)){
                return team;
            }
        }
        return null;
    }

    public void removePlayerFromTeam(Player player){
        for(Team team : instance.getTeams()){
            if(team.getPlayers().contains(player)){
                team.removePlayer(player);
            }
            if(team.getSpectators().contains(player)){
                team.removeSpectator(player);
            }

        }
    }

    public boolean isPlayerSpectator(Player player){
        for(Team team : instance.getTeams()){
            if(team.getSpectators().contains(player)){
                return true;
            }
        }
        return false;
    }
}
