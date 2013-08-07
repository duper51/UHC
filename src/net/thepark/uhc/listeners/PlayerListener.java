package net.thepark.uhc.listeners;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.thepark.uhc.UHC;
import net.thepark.uhc.utils.GameState;
import net.thepark.uhc.utils.SQL;

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


public class PlayerListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(UHC.getState() == GameState.IN_PROGRESS) {
			event.setKickMessage("Game is in progress!");
			event.disallow(Result.KICK_OTHER, "Game is in-progress!");
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.getPlayer().setPlayerListName(event.getPlayer().getDisplayName() + " " + 10);
		
		event.getPlayer().sendMessage(ChatColor.AQUA + "Welcome to UltraHardcore custom coded for ThePark");

		StringBuilder s = new StringBuilder();
		
		s.append(ChatColor.BOLD + "" + ChatColor.RED + "How to play UHC");
		s.append("===================================================\n");
		s.append(ChatColor.RED + "In UHC, your health doesn't regenerate naturally. It requires you to eat");
		s.append(ChatColor.RED + "golden apples or a health potions, both made with special recipes.");
		s.append("how this get her, i no has good at computer");
		
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
			
			player.setPlayerListName(player.getName() + "  " + ChatColor.GOLD + (player.getHealth()));
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
        	
        	player.setPlayerListName(player.getName() + "  " + ChatColor.GOLD + (player.getHealth()));
        }
    }
	 
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) throws SQLException {
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
			killed.kickPlayer(ChatColor.RED + "You were brutally murdered by " + killer.getName() + ".");
			Connection connection = SQL.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name, deaths FROM uhc WHERE name='" + killed.getName() + "'");
			while (resultSet.next()) {
				String name = "";
				int deaths = 0;
			    name = resultSet.getString("name");
			    deaths = resultSet.getInt("deaths");
			    deaths = deaths + 1;
			    statement.executeUpdate("UPDATE uhc SET Deaths=" + deaths + " WHERE name='" + name + "'");
			    if (name != "") {
			    	statement.executeUpdate("UPDATE uhc SET Deaths=" + deaths + " WHERE name='" + name + "'");
			    } else {
			    	statement.executeUpdate("INSERT INTO `uhc`(`name`, `Deaths`) VALUES ('" + killed.getName() + "',1)");
			    }
			}
			ResultSet rs2 = statement.executeQuery("SELECT name, kills FROM uhc WHERE name='" + killer.getName() + "'");
			while (rs2.next()) {
				String name = "";
				int kills = 0;
			    name = resultSet.getString("name");
			    kills = resultSet.getInt("kills");
			    kills = kills + 1;
			    statement.executeUpdate("UPDATE uhc SET Kills=" + kills + " WHERE name='" + name + "'");
			    if (name != "") {
			    	statement.executeUpdate("UPDATE uhc SET Kills=" + kills + " WHERE name='" + name + "'");
			    } else {
			    	statement.executeUpdate("INSERT INTO `uhc`(`name`, `Kills`) VALUES ('" + killer.getName() + "',1)");
			    }
			}
		} else {
			killed.kickPlayer(ChatColor.RED + "You were killed by mysterious causes.");
			Connection connection = SQL.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT name FROM uhc WHERE name=" + killed.getName() + "'");
			String name = "";
			int deaths = 0;
			while (resultSet.next()) {
			    name = resultSet.getString("name");
			    deaths = resultSet.getInt("deaths");
			    deaths = deaths + 1;
			    if (name != "") {
			    	statement.executeUpdate("UPDATE uhc SET Deaths=" + deaths + " WHERE name='" + name + "'");
			    } else {
			    	statement.executeUpdate("INSERT INTO `uhc`(`name`, `Deaths`) VALUES ('" + killed.getName() + "',1)");
			    }
			}
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







