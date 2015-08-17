package com.tatsunow.supply.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {

	private String name;
	private List<String> players;
	private Location spawn;
	public static HashMap<String, Team> teams = new HashMap<String, Team>();
	
	public Team(String nome) {
		this.name = nome;
		this.players = new ArrayList<String>(); 
		teams.put(nome.toLowerCase(), this);
	}
	
	public Location getSpawn() {
		return spawn;
	}
	
	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player.getName());
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public String getName() {
		return name;
	}
	
	public static HashMap<String, Team> getTeams() {
		return teams;
	}
	
	public static Team getPlayerTeam(Player p){
		Team team = null;
		for(Team t : getTeams().values()){
			if(t.getPlayers().contains(p.getName())){
				team = t;
			}
		}
		return team;
	}

	public void removePlayer(Player player) {
		this.players.remove(player.getName());
	}

	public static Team getTeam(String teamName) {
		return teams.get(teamName.toLowerCase());
	}
	
	

}
