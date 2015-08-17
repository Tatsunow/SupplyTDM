package com.tatsunow.supply.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.tatsunow.supply.manager.GameManager;

public class PlayerBlock implements Listener{
	
	@EventHandler
	public void onbreak(BlockBreakEvent e){
		if(!GameManager.canbuild.contains(e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onplace(BlockPlaceEvent e){
		if(!GameManager.canbuild.contains(e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}

}
