package com.tatsunow.supply.handler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.tatsunow.supply.Main;
import com.tatsunow.supply.manager.GameManager;
import com.tatsunow.supply.manager.Team;

public class PlayerEntrou implements Listener {
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onjoin(PlayerJoinEvent e){
		Player player =e.getPlayer();
		if(e.getPlayer().isOp()){
			e.getPlayer().setPlayerListName("§4" + e.getPlayer().getName());
			e.getPlayer().setGameMode(GameMode.CREATIVE);
			e.setJoinMessage("§b[§cTDM§b] §bO Admin " + e.getPlayer().getName() + " Entrou!");
			e.getPlayer().sendMessage("§b[§cTDM§b] Olá, como você faz parte da administração do servidor\nVocê tem que"
					+ " digitar /tdm forcejointeam <nome da equipe> para poder entrar em uma equipe, obrigado.");
			GameManager.updateScoreboard();
		} else {
		  GameManager.givePlayerTeam(e.getPlayer());
		 Team team = Team.getPlayerTeam(e.getPlayer());
			e.setJoinMessage("§b[§cTDM§b] §aO Jogador " + e.getPlayer().getName() + " entrou na equipe: §6" + team.getName() + ".");
			if(!player.isOp()){
				org.bukkit.scoreboard.Team pteam = Main.sb.getPlayerTeam(player);
		
				if(pteam != null){
					pteam.removePlayer(player);
					Main.sb.getTeam(team.getName()).addPlayer(player);
					player.setScoreboard(Main.sb);
				    GameManager.updateScoreboard();
				} else {
					Main.sb.getTeam(team.getName()).addPlayer(player);
					player.setScoreboard(Main.sb);
					GameManager.updateScoreboard();
				}
			}
		}
		
	}
	
	@EventHandler
	public void onquit(PlayerQuitEvent e){
		
		if(!e.getPlayer().isOp()){
		if(Team.getPlayerTeam(e.getPlayer()) == null)return;
		Team.getPlayerTeam(e.getPlayer()).getPlayers().remove(e.getPlayer().getName());
		GameManager.pkills.remove(e.getPlayer().getName());
		GameManager.pdeaths.remove(e.getPlayer().getName());
		e.setQuitMessage("§b[§cTDM§b] §cO Jogador " + e.getPlayer().getName() + " saiu!");
		} else {
			if(Team.getPlayerTeam(e.getPlayer()) != null){
				Team cteam = Team.getPlayerTeam(e.getPlayer());
				cteam.removePlayer(e.getPlayer());
				e.getPlayer().getInventory().clear();
			}
			e.setQuitMessage("§b[§cTDM§b] §cO §4Admin §c" + e.getPlayer().getName() + " saiu!");
		}
	}
	
	@EventHandler
	public void onkick(PlayerKickEvent e){
		if(!e.getPlayer().isOp()){
		Team.getPlayerTeam(e.getPlayer()).getPlayers().remove(e.getPlayer().getName());
		GameManager.pkills.remove(e.getPlayer().getName());
		GameManager.pdeaths.remove(e.getPlayer().getName());
		e.setLeaveMessage("§b[§cTDM§b] §cO Jogador " + e.getPlayer().getName() + " saiu!");
		} else {
			if(Team.getPlayerTeam(e.getPlayer()) != null){
				Team cteam = Team.getPlayerTeam(e.getPlayer());
				cteam.removePlayer(e.getPlayer());
				e.getPlayer().getInventory().clear();
			}
			e.setLeaveMessage("§b[§cTDM§b] §cO §4Admin §c" + e.getPlayer().getName() + " saiu!");
		}
	}
	
	@EventHandler
	public void onprelogin(AsyncPlayerPreLoginEvent e){
		if(e.getLoginResult() == Result.KICK_FULL){
			if(Bukkit.getServer().getOperators().contains(e.getName())){
				e.setLoginResult(Result.ALLOWED);
				return;
			}
			e.setLoginResult(Result.KICK_FULL);
			e.setKickMessage("§b[§cTDM§b] §cO Servidor está cheio :/ aguarde...");
			return;
		}
	}
	

}
