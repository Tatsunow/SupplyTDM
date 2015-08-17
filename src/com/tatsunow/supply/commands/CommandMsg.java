package com.tatsunow.supply.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMsg implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 2){
			sender.sendMessage("§cUse: /msg <jogador> <mensagem>");
			return true;
		}
		String jogador = args[0];
		@SuppressWarnings("deprecation")
		Player tp = Bukkit.getServer().getPlayer(jogador);
		StringBuilder sb = new StringBuilder();
		sb.append(args[1]);
		for(int i = 2; i <args.length; i++){
			sb.append(" ");
			sb.append(args[i]);
		}
		if(tp != null){
			tp.sendMessage("§b[§cTDM§b] §6" + sender.getName() + "§f para: §cVoce§f: " + sb.toString());
			sender.sendMessage("§b[§cTDM§b] §6Você §f para: §c" + tp.getName() + "§f: " + sb.toString());
		} else {
			sender.sendMessage("§cJogador offline, pow :/");
		}
		
		return false;
	}

}
