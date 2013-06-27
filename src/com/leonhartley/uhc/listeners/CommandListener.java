package com.leonhartley.uhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import com.leonhartley.uhc.UHC;
import com.leonhartley.uhc.utils.GameState;

public class CommandListener implements Listener, CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("startgame")) {
			if(!sender.isOp())
				return false;
			
			if(UHC.getState() == GameState.IN_PROGRESS) {
				sender.sendMessage(ChatColor.GOLD + "The game is already in progress!");
			}
			UHC.startGame();
			return true;
		}
		
		return false;

	}
}
