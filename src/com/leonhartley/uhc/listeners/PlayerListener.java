package com.leonhartley.uhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.leonhartley.uhc.UHC;
import com.leonhartley.uhc.utils.GameState;

public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(UHC.getState() == GameState.IN_PROGRESS) {
			event.setKickMessage("Game is in-progress!");
			event.disallow(Result.KICK_OTHER, "Game is in-progress!");
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setPlayerListName(event.getPlayer().getDisplayName() + " " + 10);
		
		event.getPlayer().sendMessage(ChatColor.AQUA + "Welcome to UHC, a custom plugin written by Leon H!");

		StringBuilder s = new StringBuilder();
		
		s.append(ChatColor.DARK_RED + "How to play UHC\n");
		s.append("===================================================\n");
		s.append(ChatColor.RED + "In UHC, your health doesn't regenerate naturally. ");
		s.append("The only way to gain health is to craft a golden apple (which requires 8 golden ingots and an apple) or" +
				" you can also craft a health potion which requires a block of gold, a diamond, a bottle and a rose.");
		
		s.append("\n\n");
		s.append("You can track players down by using a compass, however compass' require gold to craft!\n");
		s.append("Once there are only 2 players remaining, the pit will begin, bringing you both together to create an epic battle!\n\n");
		s.append("Be careful!\n");
		
		event.getPlayer().sendMessage(ChatColor.RED + s.toString());
	}
	                                                                                                                       
	@EventHandler(priority = EventPriority.HIGH)                                                                           
	public void onPlayerMove(PlayerMoveEvent event) {                                                                      
		if(UHC.getState() != GameState.IN_PROGRESS && event.getPlayer().getLocation().length() != event.getTo().length()) {
			moveBack(event);                                                                                               
			event.setCancelled(false);                                                                                     
		}                                                                                                                  
                                                                                                                           
		int max = UHC.forcefieldMax;                                                                                                     
		int least = UHC.forcefieldMin;                                                                                                  
	                                                                                                                       
		if (event.getTo().getX() > max || event.getTo().getX() < least) {                                                  
			moveBack(event);                                                                                                                                                                           
			event.getPlayer().sendMessage(ChatColor.GOLD + "You have reached the force-field!");                           
		}                                                                                                                  
	                                                                                                                       
		if (event.getTo().getZ() > max || event.getTo().getZ() < least) {                                                  
			moveBack(event);                                                                                                                                                                                 
			event.getPlayer().sendMessage(ChatColor.GOLD + "You have reached the force-field!");                           
		}		                                                                                                           
	 }                                                                                                                     
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(UHC.getState() != GameState.IN_PROGRESS) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEntityDamage(EntityDamageEvent event) {
		if(UHC.getState() != GameState.IN_PROGRESS) {
			event.setCancelled(true);
		}
	
		if(event.getEntityType() == EntityType.PLAYER) {
			if(UHC.isInvincible()) {
				event.setCancelled(true);
			}
			
			Player player = (Player) event.getEntity();
			
			player.setPlayerListName(player.getName() + " " + (player.getHealth() / 2));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		if(UHC.getState() != GameState.IN_PROGRESS) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPickupItem(PlayerPickupItemEvent event) {
		if(UHC.getState() != GameState.IN_PROGRESS) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRegainHealth(EntityRegainHealthEvent event) {
        if(event.getEntityType() == EntityType.PLAYER) {
        	if(event.getRegainReason() != RegainReason.MAGIC_REGEN) {
        		event.setCancelled(true);
        	}
        	
        	Player player = (Player) event.getEntity();
        	
        	player.setPlayerListName(player.getName() + " " + (player.getHealth() / 2));
        }
    }
	 
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player killed = event.getEntity().getPlayer();
		Player killer = killed.getKiller();

		for(Player player : UHC.server.getOnlinePlayers()) {
			Location loc = player.getLocation();
			loc.setX(loc.getBlockX() + 10);
			loc.setY(loc.getBlockY());
			loc.setZ(loc.getBlockZ() + 10);
			
			player.getWorld().strikeLightning(loc);
		}
		
		if(killer != null) {
			killed.kickPlayer(ChatColor.RED + killer.getName() + " brutally murdered you! Good game!");
		} else {
			killed.kickPlayer(ChatColor.RED + "Aww dang! You died, good game! :(");
		}
	}
	
	 public void moveBack(PlayerMoveEvent event) {
		 Location newLoc = event.getFrom();
		 newLoc.setX(newLoc.getBlockX() + 0.5);
		 newLoc.setY(newLoc.getBlockY());
		 newLoc.setZ(newLoc.getBlockZ() + 0.5);
		 event.getPlayer().teleport(newLoc);
	 }
}







