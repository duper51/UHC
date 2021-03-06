package net.thepark.uhc.timers;

import java.util.concurrent.TimeUnit;

import net.thepark.uhc.UHC;
import net.thepark.uhc.utils.GameState;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;


public class GameTimer implements Runnable {
	private Server server;
	public GameTimer(Server srv) {
		server = srv;
	}
	
	@Override
	public void run() {
		try {
			int playercount = 0;
			while(UHC.hasStarted() == false || playercount < 2) {
				Player playerlist[] = server.getOnlinePlayers();
				playercount = playerlist.length;
				Thread.sleep(1000);
			}
			int max = 450;
			int min = -450;
			
			for(Player player : server.getOnlinePlayers()) {
				player.teleport(UHC.generateRandomLocation(min, max, player, false));
			}
			
			Bukkit.broadcastMessage(ChatColor.RED + "90 Seconds until the match starts.");
			TimeUnit.SECONDS.sleep(70);
			Bukkit.broadcastMessage(ChatColor.RED + "" + "Your health and hunger has been reset.");
			TimeUnit.SECONDS.sleep(5);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 15 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(5);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 10 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(5);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 5 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 4 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 3 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 2 seconds until the game starts!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 1 second until the game starts!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "GO!");
			
			for(Player player : server.getOnlinePlayers()) {
				player.setHealth(20);
				player.setExhaustion(0);
				
				for(Player player1 : server.getOnlinePlayers()) {
					if(player == player1) 
						continue;
					
					player.hidePlayer(player1);
				}
			}
			
			
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "" + "All players are invincible for the first 2 minutes!");
			Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "" + "While you're invincible, you're also invisible.");
			UHC.setState(GameState.IN_PROGRESS);
			
			UHC.setInvincible(true);
			
			TimeUnit.MINUTES.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "" + "You have 1 minute until your invincibility wears off!");
			TimeUnit.SECONDS.sleep(49);
			
			Bukkit.broadcastMessage(ChatColor.RED + "" + "10 seconds until invincibility wears off!");
		
			TimeUnit.SECONDS.sleep(5);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 5 seconds until you're no longer invincible!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 4 seconds until you're no longer invincible!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 3 seconds until you're no longer invincible!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 2 seconds until you're no longer invincible!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.RED + "You have 1 second until you're no longer invincible!");
			TimeUnit.SECONDS.sleep(1);
			
			Bukkit.broadcastMessage(ChatColor.GOLD + "" + "You're no longer invincible.");
			
			for(Player player : server.getOnlinePlayers()) {
				for(Player player1 : server.getOnlinePlayers()) {
					if(player == player1) 
						continue;
					
					player.showPlayer(player1);
				}
			}
			
			UHC.setInvincible(false);
			boolean battleBegun = false;
			
			while(true) {
				Thread.sleep(3000);
				
				if(server.getOnlinePlayers().length == 1) {
					
					for(Player player : server.getOnlinePlayers()) {
						player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have won UHC, adding your stats, and restarting in 10 seconds!");
						
						for(int i = 0; i < 1000; i++) {
							player.giveExpLevels(i);
							Thread.sleep(100);
						}
						
						TimeUnit.SECONDS.sleep(10);
						
						player.kickPlayer("The game is now restarting!");
						
						Bukkit.shutdown();
					}
				}
				
				if(server.getOnlinePlayers().length == 4 && battleBegun != true) {
					Bukkit.broadcastMessage(ChatColor.WHITE + "In 2 minutes, all players will be teleported within 50 blocks of the center of the map!");
					TimeUnit.SECONDS.sleep(90);
					
					Bukkit.broadcastMessage(ChatColor.RED + "30 seconds left until the final battle begins!");
					TimeUnit.SECONDS.sleep(20);
					
					Bukkit.broadcastMessage(ChatColor.RED + "10 seconds until the final battle begins!");
					
					TimeUnit.SECONDS.sleep(5);
					
					Bukkit.broadcastMessage(ChatColor.RED + "You have 5 seconds until the battle begins!");
					TimeUnit.SECONDS.sleep(1);
					
					Bukkit.broadcastMessage(ChatColor.RED + "You have 4 seconds until the battle begins!");
					TimeUnit.SECONDS.sleep(1);
					
					Bukkit.broadcastMessage(ChatColor.RED + "You have 3 seconds until the battle begins!");
					TimeUnit.SECONDS.sleep(1);
					
					Bukkit.broadcastMessage(ChatColor.RED + "You have 2 seconds until the battle begins!");
					TimeUnit.SECONDS.sleep(1);
					
					Bukkit.broadcastMessage(ChatColor.RED + "You have 1 second until the battle begins!");
					TimeUnit.SECONDS.sleep(1);
					
					Bukkit.broadcastMessage(ChatColor.GOLD + "" + "The final battle has begun!");
					
					for(Player player : server.getOnlinePlayers()) {
						player.teleport(UHC.generateRandomLocation(-50, 50, player, true));
					}
					
					battleBegun = true;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
