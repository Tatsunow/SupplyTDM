package com.tatsunow.supply.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.tatsunow.supply.Main;
import com.tatsunow.supply.db.ConnectionFactory;

public class GameManager {
	
	public static HashMap<String, Team> teams = new HashMap<String, Team>();
	public static boolean pvpentreamigos = false;
	public static boolean build = false;
	public static Team blueTeam;
	public static Team redTeam;
	public static HashMap<String, Integer> pkills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> pdeaths = new HashMap<String, Integer>();
	public static Location blueSpawn;
	public static Location redSpawn;
	public static HashMap<String, Integer> invencibility = new HashMap<String, Integer>() ;
	public static List<String> canbuild = new ArrayList<String>();
	public static Inventory economiainv = Bukkit.getServer().createInventory(null, 18, "§a§lEconomia");
	
	public static void givePlayerTeam(Player player){
		Team team = getRandomTeam();
		team.addPlayer(player);
		player.teleport(team.getSpawn());
		player.getInventory().clear();
		setupPlayer(player, team);
		player.sendMessage("§b[§cTDM§b] §fVocê entrou para a team: §c" + team.getName());
		setKills(player.getName(), 0);
		setDeaths(player.getName(), 0);
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
		GameManager.invencivelpor(player, 5);
	}
	
	private static void setDeaths(String string, int amount) {
		getInstance().getConfig().set("kd." + string + ".deaths", amount);
		getInstance().saveConfig();
	}

	public static void kickPlayerFromTeam(Player player, Team team){
		team.removePlayer(player);
		
	}
	
	public static void setPlayerTeam(Player player, Team team){
		if(team != null){
			Team cteam = Team.getPlayerTeam(player);
			if(cteam != null){
				kickPlayerFromTeam(player, cteam);
			}
			team.addPlayer(player);
			setupPlayer(player, team);
			player.sendMessage("§b[§cTDM§b] §fVocê entrou para a team: §c" + team.getName());
			player.teleport(team.getSpawn());
			setKills(player.getName(), 0);
			setDeaths(player.getName(), 0);
			GameManager.invencivelpor(player, 5);
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
			return;
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setupPlayer(Player player, Team team){
		player.getInventory().clear();
		if(team.getName().equalsIgnoreCase("Azul")){
			player.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (byte)11));
			
		} else {
			player.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (byte)14));
		}
		ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemStack legg = new ItemStack(Material.DIAMOND_LEGGINGS);
		ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
		ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
		ItemStack capiroto = new ItemStack(Material.GOLDEN_APPLE, 25);
		espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
		ItemStack arco = new ItemStack(Material.BOW,1);
		arco.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		arco.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		player.getInventory().setChestplate(chest);
		player.getInventory().setLeggings(legg);
		player.getInventory().setBoots(boots);
		player.getInventory().setItem(1, arco);
		player.getInventory().setItem(2, capiroto);
		player.getInventory().setItem(3,new ItemStack(Material.ARROW, 1));
		player.getInventory().setItem(0, espada);
		player.setHealth(20.0D);
		ItemStack gold = new ItemStack(Material.GOLD_INGOT);
		ItemMeta goldmeta = gold.getItemMeta();
		goldmeta.setDisplayName("§cBau da Alegria C:");
		gold.setItemMeta(goldmeta);
		player.getPlayer().getInventory().setItem(8, gold);
		player.updateInventory();
	}
	
	private static Team getRandomTeam() {
		Random r = new Random();
		if(blueTeam.getPlayers().size() > redTeam.getPlayers().size()){
			return redTeam;
		}
		if(redTeam.getPlayers().size()  > blueTeam.getPlayers().size()){
			return blueTeam;
		}
		if(blueTeam.getPlayers().size() > 0 && redTeam.getPlayers().size() > 0 || blueTeam.getPlayers().size() == 0 && redTeam.getPlayers().size() == 0){
			Team t = blueTeam;
				int g = r.nextInt(2);
				if(g == 0){
					t = blueTeam;
				} else {
					t = redTeam;
				}
			return t;
		}
		
		Team t = blueTeam;
		int g = r.nextInt(2);
		if(g == 0){
			t = blueTeam;
		} else {
			t = redTeam;
		}
		return t;
		
	}
	
	

	public static void setKills(String string, int amount){
		 getInstance().getConfig().set("kd." + string + ".kills", amount);
		 getInstance().saveConfig();
	}
	
	
	
	public static void addKill(String string) {
		 if(getInstance().getConfig().contains("kd." + string + ".kills")){
			 int kills = getKills(string)+1;
			 setKills(string, kills);
		 } else {
			 setKills(string, 1);
		 }
	}
	
	public static int getKills(String string){
		 if(getInstance().getConfig().contains("kd." + string + ".kills")){
			 int kills = getInstance().getConfig().getInt("kd." + string + ".kills");
			 return kills;
		 }  else {
			 setKills(string, 0);
			 return 0;
		 }
	} 
	
	public static Main getInstance(){
		return (Main) Bukkit.getServer().getPluginManager().getPlugin("SupplyTDM");
	}
	
	public static int getDeaths(String string){
		if(getInstance().getConfig().contains("kd." + string + ".deaths")){
			 int kills = getInstance().getConfig().getInt("kd." + string + ".deaths");
			 return kills;
		 }  else {
			 setKills(string, 0);
			 return 0;
		 }
	}


	public static void addDeath(String string) {
		if(getInstance().getConfig().contains("kd." + string + ".deaths")){
			 int deaths = getDeaths(string)+1;
			 setDeaths(string, deaths);
		 } else {
			 setDeaths(string, 1);
		 }
		
	}

	public static void invencivelpor(final Player p, int i) {
		invencibility.put(p.getName(), i);
		Main m = (Main)Bukkit.getServer().getPluginManager().getPlugin("SupplyTDM");
		m.getServer().getScheduler().scheduleSyncDelayedTask(m, new Runnable() {
			
			@Override
			public void run() {
				if(invencibility.containsKey(p.getName()))
				invencibility.remove(p.getName());
				
			}
		}, 20L * i);
		p.sendMessage("§cVocê está invencível por " + i + " segundos.");
	}

	public static void setCanBuild(String name, boolean b) {
		if(b){
			canbuild.add(name);
		} else {
			if(canbuild .contains(name)){
				canbuild.remove(name);
			} else {
				return;
			}
		}
	}

	@SuppressWarnings("deprecation")
	public static void updateScoreboard() {
	     for(Player all : Bukkit.getServer().getOnlinePlayers()){
	    	 all.setScoreboard(Main.sb);
	     }
	}

	public static void startInsertingDataInDateBase() {
		getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
			
			@Override
			public void run() {
				Main.c = new ConnectionFactory().getConnection();
				Connection c = Main.c;
					for(String str : getInstance().getConfig().getConfigurationSection("kd").getKeys(false)){
						int kills = getKills(str);
						int deaths = getDeaths(str);
						try {
							PreparedStatement prepared1 = c.prepareStatement("DELETE FROM `kd` WHERE `nick`=?");
							prepared1.setString(1, str);
							prepared1.execute();
							PreparedStatement prepared = c.prepareStatement("INSERT INTO `kd`(`nick`, `kills`, `deaths`) VALUES(?,?,?)");
							prepared.setString(1, str);
							prepared.setInt(2, kills);
							prepared.setInt(3, deaths);
							prepared.execute();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				
				
			}
		}, 0, 20L * 1200);
		
	}

}
