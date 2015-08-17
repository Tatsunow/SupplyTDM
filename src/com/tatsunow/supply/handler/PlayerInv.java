package com.tatsunow.supply.handler;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerInv implements Listener{
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void ondrop(PlayerDropItemEvent e){
		e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onpega(PlayerPickupItemEvent e){
		e.getItem().remove();
		e.setCancelled(true);
		e.getPlayer().updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void oninvclick(InventoryClickEvent e){
		Player p = (Player)e.getWhoClicked();
		if(p.isOp()){
			if(p.getGameMode() != GameMode.CREATIVE){
				e.setCursor(null);
				e.setCancelled(true);
				return;
			}
		} else {
			if(p.getGameMode() != GameMode.CREATIVE){
				e.setCancelled(true);
				e.setCursor(null);
				return;
			}
		}

	}
	
	
	

}
