/**
 * Copyright 2019 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Plugin plugin;
	
	/**
	 * This method is called when the plugin is enabled
	 */
	@Override
	public void onEnable() {
		plugin = this;
		
		super.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		super.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		super.getServer().getPluginManager().registerEvents(new PlayerSignListener(), this);
		super.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		super.getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
		super.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		super.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
	}

	/**
	 * This method is called when the plugin is disabled
	 */
	@Override
	public void onDisable() {
		
	}
}
