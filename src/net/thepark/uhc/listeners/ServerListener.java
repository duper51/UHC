package net.thepark.uhc.listeners;

import net.thepark.uhc.UHC;
import net.thepark.uhc.utils.GameState;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class ServerListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerListPing(ServerListPingEvent event) {
		if(UHC.getState() == GameState.IN_PROGRESS) {
			event.setMotd(ChatColor.RED + "Match In Progress");
		}
		
		if(UHC.getState() == GameState.IDLE) {
			event.setMotd(ChatColor.AQUA + "Waiting");	
		}
	}
}
