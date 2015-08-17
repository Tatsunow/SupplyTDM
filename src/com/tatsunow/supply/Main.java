package com.tatsunow.supply;

import java.sql.Connection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Vector;

import com.tatsunow.supply.commands.CommandMsg;
import com.tatsunow.supply.commands.CommandTdm;
import com.tatsunow.supply.db.ConnectionFactory;
import com.tatsunow.supply.handler.PlayerBlock;
import com.tatsunow.supply.handler.PlayerChat;
import com.tatsunow.supply.handler.PlayerDeath;
import com.tatsunow.supply.handler.PlayerEntrou;
import com.tatsunow.supply.handler.PlayerHit;
import com.tatsunow.supply.handler.PlayerInv;
import com.tatsunow.supply.handler.ServerEvents;
import com.tatsunow.supply.manager.GameManager;
import com.tatsunow.supply.manager.Team;

public class Main extends JavaPlugin {
	
	public static ScoreboardManager sm;
	public static Scoreboard sb;
	public static org.bukkit.scoreboard.Team azul;
	public static org.bukkit.scoreboard.Team vermelha;
	public static Connection c;
	@Override
	public void onEnable() {
		System.out.println("[SupplyTDM] Plugin Iniciado.");
		init();
	}
	
	@SuppressWarnings("deprecation")
	private void init(){
		saveDefaultConfig();
		reloadConfig();
		sm = this.getServer().getScoreboardManager();
		sb = sm.getNewScoreboard();
		azul = sb.registerNewTeam("Azul");
		vermelha = sb.registerNewTeam("Vermelha");
		azul.setPrefix(ChatColor.BLUE.toString());
		vermelha.setPrefix(ChatColor.RED.toString());
		c = new ConnectionFactory().getConnection();
		GameManager.blueSpawn = new Location(Bukkit.getServer().getWorld("world"), -8, 51, 43);
		GameManager.redSpawn = new Location(Bukkit.getServer().getWorld("world"), -18, 51, 9);
		GameManager.blueTeam = new Team("Azul");
		GameManager.redTeam = new Team("Vermelha");
		GameManager.blueTeam.setSpawn(GameManager.blueSpawn);
		GameManager.redTeam.setSpawn(GameManager.redSpawn);
		Player[] players = this.getServer().getOnlinePlayers();
		for(Player p : players){
			if(p.isOp())continue;
			GameManager.givePlayerTeam(p);
		}
		for(Entity en : Bukkit.getServer().getWorld("world").getEntities()){
			if(!(en instanceof Player)){
				en.remove();
			}
		}
		PlayerEntrou entrou = new PlayerEntrou();
		PlayerHit hitted = new PlayerHit();
		PlayerDeath morreu = new PlayerDeath();
		PlayerInv inv = new PlayerInv();
		PlayerChat pc = new PlayerChat();
		PlayerBlock pb = new PlayerBlock();
		ServerEvents se = new ServerEvents();
		this.getServer().getPluginManager().registerEvents(se, this);
		this.getServer().getPluginManager().registerEvents(entrou, this);
		this.getServer().getPluginManager().registerEvents(hitted, this);
		this.getServer().getPluginManager().registerEvents(morreu, this);
		this.getServer().getPluginManager().registerEvents(inv, this);
		this.getServer().getPluginManager().registerEvents(pc, this);
		this.getServer().getPluginManager().registerEvents(pb, this);
		
		this.getCommand("tdm").setExecutor(new CommandTdm());
		this.getCommand("msg").setExecutor(new CommandMsg());
		
		
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for (Arrow arrow : Bukkit.getServer().getWorld("world").getEntitiesByClass(Arrow.class)) {
					  Vector vecor = arrow.getVelocity();
					 
					  if (vecor.getX() != 0 || vecor.getY() != 0 || vecor.getZ() != 0) {
					    continue;
					  }
					 
					  arrow.remove();
					}
				
				Bukkit.getServer().getWorld("world").setTime(0);
				
			}
		}, 20L * 30, 20L * 30);
		GameManager.startInsertingDataInDateBase();
		
		
	
		
	}

}
