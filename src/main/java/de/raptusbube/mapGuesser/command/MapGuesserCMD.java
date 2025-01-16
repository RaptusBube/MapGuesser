package de.raptusbube.mapGuesser.command;

import de.raptusbube.mapGuesser.MapGuesser;
import de.raptusbube.mapGuesser.utils.Team;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MapGuesserCMD implements BasicCommand {

    private final MapGuesser instance;


    public MapGuesserCMD(MapGuesser instance) {
        this.instance = instance;
    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] strings) {
        if (stack.getSender() instanceof Player player) {
            if(strings.length == 0) {
                player.sendMessage(instance.getPrefix()+ "/mg join <ID>"+ ChatColor.DARK_AQUA + " Du betrittst ein Team (ID Optional)");
                player.sendMessage(instance.getPrefix()+ "/mg sjoin <ID>"+ ChatColor.DARK_AQUA + " Du betrittst ein Team als Spectator (ID Optional)");
                player.sendMessage(instance.getPrefix()+ "/mg leave "+ ChatColor.DARK_AQUA + " Du verlässt ein Team");
            }else {
                if(strings[0].equalsIgnoreCase("join")) {
                    if(instance.getTeamManager().isPlayerInTeam(player)) {
                        player.sendMessage(instance.getPrefix()+ "Du bist bereits in einem Team!");
                        return;
                    }
                    if (strings.length == 2) {
                        int i = 0;
                        try {
                            i = Integer.parseInt(strings[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(instance.getPrefix() + "ID muss eine Ganzzahl sein!");
                            return;
                        }
                        if (instance.getTeamManager().addPlayerToTeam(player, i)) {
                            player.sendMessage(instance.getPrefix() + "Du bist nun im Team Nr." + i);
                        } else {
                            if (i > 0) {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), i);
                                team.addPlayer(player);
                                player.sendMessage(instance.getPrefix() + "Du bist nun im Team Nr." + i);
                            } else {
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                                team.addPlayer(player);
                                player.sendMessage(instance.getPrefix() + "Du bist nun im default Team");
                            }
                        }
                    } else {
                        if (instance.getTeamManager().addPlayerToTeam(player, 0)) {
                            player.sendMessage(instance.getPrefix() + "Du bist nun im default Team");
                        } else {
                            Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                            team.addPlayer(player);
                            player.sendMessage(instance.getPrefix() + "Du bist nun im default Team");
                        }
                    }
                }else if (strings[0].equalsIgnoreCase("sjoin")) {
                    if(strings.length == 2) {
                        int i = 0;
                        try{
                            i = Integer.parseInt(strings[1]);
                        }catch(NumberFormatException e){
                            player.sendMessage(instance.getPrefix()+ "ID muss eine Ganzzahl sein!");
                            return;
                        }
                        if(instance.getTeamManager().addSpectatorToTeam(player, i)){
                            player.sendMessage(instance.getPrefix()+ "Du bist nun als Spectator im Team Nr."+ i);
                        }else{
                            if(i > 0){
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), i);
                                team.addSpectator(player);
                                player.sendMessage(instance.getPrefix()+ "Du bist nun als Spectator im Team Nr."+ i);
                            }else{
                                Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                                team.addSpectator(player);
                                player.sendMessage(instance.getPrefix()+ "Du bist nun als Spectator im default Team");
                            }
                        }
                    }else{
                        if(instance.getTeamManager().addSpectatorToTeam(player, 0)){
                            player.sendMessage(instance.getPrefix()+ "Du bist nun als Spectator im default Team");
                        }else{
                            Team team = new Team(new ArrayList<>(), new ArrayList<>(), 0);
                            team.addSpectator(player);
                            player.sendMessage(instance.getPrefix()+ "Du bist nun als Spectator im default Team");
                        }
                    }
                }else if(strings[0].equalsIgnoreCase("leave")) {
                    if(instance.getTeamManager().isPlayerInTeam(player)) {
                        instance.getTeamManager().removePlayerFromTeam(player);
                        player.sendMessage(instance.getPrefix()+ "Du hast dein Team verlassen");
                    }else{
                        player.sendMessage(instance.getPrefix()+ "Du bist in keinem Team");
                    }
                }
            }
        }
    }
}
