package com.tatsunow.supply.handler;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import com.tatsunow.supply.manager.GameManager;
import com.tatsunow.supply.manager.Team;

public class PlayerHit implements Listener{
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onhit(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			Player damager = (Player)e.getDamager();
			Player p = (Player)e.getEntity();
			if(Team.getPlayerTeam(damager) == GameManager.blueTeam && Team.getPlayerTeam(p) == GameManager.blueTeam){
				e.setDamage(0);
				e.setCancelled(true);
			}
			if(Team.getPlayerTeam(damager) == GameManager.redTeam && Team.getPlayerTeam(p) == GameManager.redTeam){
				e.setDamage(0);
				e.setCancelled(true);
			}
			
		
			
			
		}
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			Arrow arr = (Arrow)e.getDamager();
			Player damager = (Player)arr.getShooter();
			if(Team.getPlayerTeam(damager) == GameManager.blueTeam && Team.getPlayerTeam(p) == GameManager.blueTeam){
				e.setDamage(0);
				e.setCancelled(true);
				
			}
			if(Team.getPlayerTeam(damager) == GameManager.redTeam && Team.getPlayerTeam(p) == GameManager.redTeam){
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
		
			
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void ondamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player)e.getEntity();
			if(GameManager.invencibility.containsKey(p.getName())){
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
	}

}
