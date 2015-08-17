package com.tatsunow.supply.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.tatsunow.supply.manager.GameManager;
import com.tatsunow.supply.manager.Team;

public class PlayerDeath implements Listener{ 
	
	
	@EventHandler
	public void onfoodl(FoodLevelChangeEvent e){
		e.setCancelled(true);
		e.setFoodLevel(20);
	}
	
	@EventHandler
	public void ondeath(PlayerDeathEvent e){
		
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player){
			e.getDrops().clear();
			e.setDeathMessage("§a" + e.getEntity().getName() + " §cfaliceu por §4" + e.getEntity().getKiller().getName());
			Player killer = e.getEntity().getKiller();
			GameManager.addKill(killer.getName());
			GameManager.addDeath(e.getEntity().getName());
			if(GameManager.getKills(killer.getName()) - 1 == 10){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 10 KILLS!");
			}
			if(GameManager.getKills(killer.getName()) - 1 == 20){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 20 KILLS!");
			}
			if(GameManager.getKills(killer.getName()) - 1 == 30){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 30 KILLS!");
			}
			if(GameManager.getKills(killer.getName()) - 1 == 40){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 40 KILLS!");
			}
			if(GameManager.getKills(killer.getName()) - 1 == 50){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 50 KILLS!");
			}
			if(GameManager.getKills(killer.getName()) - 1 == 60){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 60 KILLS!");
			}
			if(GameManager.getKills(killer.getName())- 1 == 70){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 70 KILLS!");
			}
			if(GameManager.getKills(killer.getName())- 1 == 80){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 80 KILLS!");
			}
			if(GameManager.getKills(killer.getName())- 1 == 90){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 90 KILLS!");
			}
			if(GameManager.getKills(killer.getName())- 1 == 100){
				Bukkit.getServer().broadcastMessage("§b[§cTDM§b] §fO PLAYER " + killer.getName() + " COMPLETOU UM §c§nKILLSTREAK §fDE 100 KILLS!");
				killer.getItemInHand().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
			}
		}
		 
	}
	
	@EventHandler
	public void onrespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
	    Team team = Team.getPlayerTeam(p);
	    if(e.getPlayer().isOp() && team == null){
	    	Location loc = new Location(Bukkit.getServer().getWorld("world"), -11, 51, 5);
	    	e.setRespawnLocation(loc);
	    	return;
	    }
	    e.setRespawnLocation(team.getSpawn());
	    GameManager.setupPlayer(p, team);
	    GameManager.invencivelpor(p, 5);
	}
	

}
