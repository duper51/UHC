package com.leonhartley.uhc.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.leonhartley.uhc.UHC;
import com.leonhartley.uhc.utils.GameState;

public class ServerListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onServerListPing(ServerListPingEvent event) {
		if(UHC.getState() == GameState.IN_PROGRESS) {
			event.setMotd(ChatColor.RED + "UHC is currently in progress");
		}
		
		if(UHC.getState() == GameState.IDLE) {
			event.setMotd(ChatColor.AQUA + "Come and play UHC!");	
		}
	}
}
