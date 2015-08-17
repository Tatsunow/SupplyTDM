package com.tatsunow.supply.handler;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ServerEvents implements Listener {
	
	@EventHandler
	public void onchange(WeatherChangeEvent e){
		if(e.toWeatherState()){
			e.setCancelled(true);
		} else {
			return;
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onping(ServerListPingEvent e){
		if(Bukkit.getServer().hasWhitelist()){
			e.setMotd("§b[§cTDM§b] §a§lSupply§c§lT§4§lD§c§lM \n§f§l§m---------[§4§l Em Manutenção §f§l§m]---------");
		} else {
			e.setMotd("§b[§cTDM§b] §a§lSupply§c§lT§4§lD§c§lM \n§f§l§m---------[§a§l Aberto, Entre §f§l§m]---------");
		}
		
	}

}
