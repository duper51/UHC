package net.thepark.uhc.listeners;

import net.thepark.uhc.UHC;
import net.thepark.uhc.utils.GameState;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;


public class CommandListener implements Listener, CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("uhc")) {
			if (args.length == 0) {
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.BOLD + "" + ChatColor.RED + "UltraHardcore: " + ChatColor.RESET + "Custom coded for ThePark");
				return true;
				} else {
					sender.sendMessage("UHC Admin Command Help");
					sender.sendMessage("/uhc startgame - Starts the game currently running on the server.");
				return true;
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("startgame")) {
					if(!sender.isOp()) {
						return false;
					}
					else if(UHC.getState() == GameState.IN_PROGRESS) {
						sender.sendMessage(ChatColor.GOLD + "The game is already in progress!");
					}
					else {
					UHC.startGame();
					return true;
					}
					
				}
					
				}
			}
		
		return false;

	}
}
/*if(cmd.getName().equalsIgnoreCase("uhc")) {
	if(!sender.isOp())
		return false;
	
	if(UHC.getState() == GameState.IN_PROGRESS) {
		sender.sendMessage(ChatColor.GOLD + "The game is already in progress!");
	}
	UHC.startGame();
	return true;*/