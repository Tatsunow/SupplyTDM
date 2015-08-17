package com.tatsunow.supply.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tatsunow.supply.Main;
import com.tatsunow.supply.manager.GameManager;
import com.tatsunow.supply.manager.Team;

public class CommandTdm implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tdm")){
			if(args.length < 1){
				sender.sendMessage("§cUse: /tdm kdr");
				if(sender.isOp()){
					sender.sendMessage("§cUse: /tdm forcejointeam <team>");
					sender.sendMessage("§cUse: /tdm build <on/off>");
					sender.sendMessage("§cUse: /tdm kickall <motivo>");
				}
				return true;
			}
			
			
			if(args[0].equalsIgnoreCase("kickall")){
				if(!sender.hasPermission("supplytdm.kickall")){
					sender.sendMessage("§cVocê não tem permissão.");
					return true;
				}
				if(args.length < 2){
					sender.sendMessage("§cUse: /tdm kickall <motivo>");
					return true;
				}
				StringBuilder sb = new StringBuilder();
				sb.append(args[1]);
				for(int i = 2; i<args.length; i++){
					sb.append(" ");
					sb.append(args[i]);
				}
				for(Player players : Bukkit.getServer().getOnlinePlayers()){
					if(players.getName().equalsIgnoreCase(sender.getName()))continue;
					players.kickPlayer("§cVocê foi kickado do servidor.\nmotivo:" + sb.toString());
				}
				sender.sendMessage("§b[§cTDM§b] Todos os jogadores foram kickados do servidor.");
				return true;
			}
			if(args[0].equalsIgnoreCase("build")){
				if(!sender.hasPermission("supplytdm.build")){
					sender.sendMessage("§cVocê não tem permissão");
					return true;
				}
				if(args.length < 2){
					sender.sendMessage("§cUse: /tdm build <on/off>");
					return true;
				}
				String build = args[1];
				if(build.equalsIgnoreCase("on")){
					GameManager.setCanBuild(sender.getName(), true);
					sender.sendMessage("§b[§cTDM§b] Agora você pode construir!");
					return true;
				} else {
					sender.sendMessage("§b[§cTDM§b] Agora você não pode mais construir ;-;");
					GameManager.setCanBuild(sender.getName(), false);
					return true;
				}
			}
			if(args[0].equalsIgnoreCase("topkdr")){
				
				sender.sendMessage("§c§l§m--------------[ §bTOP 10 KDR §c§l§m]--------------");
				Connection c = Main.c;
				try {
					java.sql.PreparedStatement prepar = c.prepareStatement("SELECT * FROM `kd` ORDER BY `kills` DESC");
					ResultSet set = prepar.executeQuery();
					while(set.next()){
						sender.sendMessage("§bNome: §c" + set.getString("nick") + " §bKills: §c" + set.getInt("kills") + " §bDeaths: §c" + set.getInt("deaths"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			if(args[0].equalsIgnoreCase("kdr")){
               if(sender instanceof Player){
            	   if(args.length < 2){
            	   Player p = (Player)sender;
            	   double kills = GameManager.getKills(p.getName());
            	   double deaths = GameManager.getDeaths(p.getName());
            	   double kdr = kills / deaths;
            	   if(kills == 0.0D && deaths == 0.0D){
            		   kdr = 0.0D;
            	   }
            	   p.sendMessage("§b[§cTDM§b] Seu KDR: §c" + String.valueOf(kdr));
            	   p.sendMessage(ChatColor.AQUA + "        Kills: §c" + kills );
            	   p.sendMessage(ChatColor.AQUA + "        Deaths: §c" + deaths );
            	   return true;
               } else {
            	   Player p = Bukkit.getServer().getPlayer(args[1]);
            	   double kills = GameManager.getKills(p.getName());
            	   double deaths = GameManager.getDeaths(p.getName());
            	   double kdr = kills / deaths;
            	   if(kills == 0.0D && deaths == 0.0D){
            		   kdr = 0.0D;
            	   }
            	   sender.sendMessage("§b[§cTDM§b] Info do Player:" + p.getName());
            	   sender.sendMessage(ChatColor.AQUA + "KDR: §c" + kdr);
            	   sender.sendMessage(ChatColor.AQUA + "Kills: §c" + kills );
            	   sender.sendMessage(ChatColor.AQUA + "Deaths: §c" + deaths );
            	   return true;
               }
            	   
               }
			}
		
			if(args[0].equalsIgnoreCase("forcejointeam")){
				if(!sender.hasPermission("supplytdm.forcejointeam")){
					sender.sendMessage("§cVocê não tem permissão.");
					return true;
				}
				if(sender instanceof Player){
					if(args.length < 3){
						sender.sendMessage("§cUse: /tdm forcejointeam <player> <team>");
						return true;
					}
					String player = args[1];
					String teamName = args[2];
					Player p = Bukkit.getServer().getPlayer(player);
					Team team = Team.getTeam(teamName);
					GameManager.setPlayerTeam(p, team);
					return true;
					
				} else {
					sender.sendMessage("§cVocê tem de ser um player.");
					return true;
				}
			}
		}
		return false;
	}

}
