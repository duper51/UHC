package com.leonhartley.uhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.leonhartley.uhc.listeners.CommandListener;
import com.leonhartley.uhc.listeners.ServerListener;
import com.leonhartley.uhc.utils.GameState;
import com.leonhartley.uhc.listeners.PlayerListener;
import com.leonhartley.uhc.items.Recipies;
import com.leonhartley.uhc.timers.GameTimer;

public class UHC extends JavaPlugin {
	private static GameState state;
	private static boolean invincible;
	private static Thread gameTimer;
	private static boolean started;
	
	public static Server server;
	
	public static int forcefieldMin;
	public static int forcefieldMax;
	
	@Override
	public void onEnable() {
		started = false;
		forcefieldMin = -500;
		forcefieldMax = 500;
		
		UHC.setServer(getServer());
		
		setState(GameState.IDLE);
		
		getServer().getRecipesFor(new ItemStack(Material.GOLDEN_APPLE, 1));
		
		getServer().addRecipe(Recipies.goldenApple());
		getServer().addRecipe(Recipies.healthPotion());
		getServer().addRecipe(Recipies.compass());
		
		getCommand("startgame").setExecutor(new CommandListener());
		
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
				
		GameTimer game = new GameTimer(server);
		gameTimer = new Thread(game);
		gameTimer.start();
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Unloading UHC");
	}
	
	public static void startGame() {
		UHC.started = true;
		UHC.setState(GameState.STARTING);
		
		Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "UHC is about to start, ready yourself!");
	}
	
	public static Location generateRandomLocation(int min, int max, Player player, boolean ocean) {
		double newX = min + (int)(Math.random() * (max - min + 1));
		double newZ = min + (int)(Math.random() * (max - min + 1));
		double newY = player.getWorld().getHighestBlockAt((int)newX, (int)newZ).getY() + 0.5;
		
		Location location = new Location(player.getWorld(), newX, newY, newZ);
		
		if(!ocean) {
			while(player.getWorld().getBiome((int)newX, (int)newY) == Biome.OCEAN) {
				newX = min + (int)(Math.random() * (max - min + 1));
				newZ = min + (int)(Math.random() * (max - min + 1));
				newY = player.getWorld().getHighestBlockAt((int)newX, (int)newZ).getY() + 0.5;
				
				location = new Location(player.getWorld(), newX, newY, newZ);
			}
		}
		
		return location;
	}
	
	public static void setState(GameState state) {
		UHC.state = state;
	}
	
	public static boolean hasStarted() {
		return started;
	}
	
	public static void setInvincible(boolean i) {
		UHC.invincible = i;
	}
	
	public static GameState getState() {
		return state;
	}
	
	public static boolean isInvincible() {
		return invincible;
	}
	
	public static void setServer(Server newServer) {
		server = newServer;
	}
}
