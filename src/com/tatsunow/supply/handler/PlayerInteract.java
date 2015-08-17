package com.tatsunow.supply.handler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.tatsunow.supply.manager.GameManager;

public class PlayerInteract implements Listener{
	
	@EventHandler
	public void oninteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(p.getItemInHand().getType() == Material.GOLD_INGOT){
				p.openInventory(GameManager.economiainv);
			}
		}
		if(p.getItemInHand().getType().name().contains("SWORD") || p.getItemInHand().getType().name().contains("BOW")){
			p.getItemInHand().setDurability((short) 0);
		}
	}

}
