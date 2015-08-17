package com.tatsunow.supply.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.tatsunow.supply.manager.Team;

public class PlayerChat implements Listener {

	
	@EventHandler
	public void onchat(AsyncPlayerChatEvent e){
		Player p = e.getPlayer();
		if(!p.isOp()){
			Team team = Team.getPlayerTeam(p);
			ChatColor c = ChatColor.WHITE;
			if(team.getName().equalsIgnoreCase("Azul")){
				c = ChatColor.BLUE;
			} else {
				c = ChatColor.RED;
			}
			String msg = e.getMessage();
			if(p.hasPermission("chat.color")){
				msg = ChatColor.translateAlternateColorCodes('&', msg);
			}
			e.setFormat(c + p.getName() + ": §f" + msg);
		} else {
			ChatColor c = ChatColor.DARK_RED;
			String msg = e.getMessage();
			if(p.hasPermission("chat.color")){
				msg = ChatColor.translateAlternateColorCodes('&', msg);
			}
			e.setFormat(c + p.getName() + ": §f" + msg);
		}
	}
}
