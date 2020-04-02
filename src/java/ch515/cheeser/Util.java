/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class provides utilities for the plugin
 * 
 * @author Caleb Heydon
 */
public class Util {
	/**
	 * Has a player join a server
	 * 
	 * @param plugin
	 * @param player
	 * @param server
	 */
	public static void joinServer(Player player, String server) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(byteOut);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		player.sendPluginMessage(Main.plugin, "BungeeCord", byteOut.toByteArray());
	}

	/**
	 * Returns a random number between a range
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomIntInRange(int min, int max) {
		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}

	/**
	 * Removes all potion effects from a player
	 * 
	 * @param player
	 */
	public static void clearPotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Sends a message to all online players
	 * 
	 * @param message
	 */
	public static void sendMessageToEveryone(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}

	/**
	 * Plays a sound for everyone
	 * 
	 * @param sound
	 * @param volume
	 * @param pitch
	 */
	public static void playSoundForEveryone(Sound sound, float volume, float pitch) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), sound, volume, pitch);
		}
	}

	/**
	 * Returns a random player
	 * 
	 * @param players
	 * @return
	 */
	public static Player getRandomPlayer(ArrayList<Player> players) {
		return players.get(getRandomIntInRange(0, players.size() - 1));
	}

	/**
	 * Clears the inventory of all the players in the list
	 * 
	 * @param players
	 */
	public static void clearPlayerInventories(ArrayList<Player> players) {
		for (Player player : players) {
			player.getInventory().clear();
		}
	}

	/**
	 * Executes a runnable synchronously in the plugin
	 * 
	 * @param code
	 */
	public static void runInPlugin(Runnable code) {
		new BukkitRunnable() {
			@Override
			public void run() {
				code.run();
			}
		}.runTask(Main.plugin);
	}

	/**
	 * Initializes a player
	 * 
	 * @param player
	 */
	public static void initPlayer(Player player) {
		Location spawnLocation = new Location(player.getWorld(), Constants.SPAWN_X, Constants.SPAWN_Y,
				Constants.SPAWN_Z, 0f, 0f);
		player.teleport(spawnLocation);

		player.setGameMode(GameMode.ADVENTURE);
		player.setHealth(player.getHealthScale());
		player.setLevel(0);
		player.setExp(0);

		player.setCanPickupItems(false);
		player.getInventory().clear();
		clearArmor(player);

		Util.clearPotionEffects(player);

		if (player.isOp()) {
			player.setAllowFlight(true);
		} else {
			player.setAllowFlight(false);
		}

		if (Math.random() >= 0.5) {
			player.getInventory().setHelmet(new ItemStack(Material.SPONGE));
		} else {
			player.getInventory().setHelmet(new ItemStack(Material.AIR));
		}
	}

	/**
	 * Teleports a group of players
	 * 
	 * @param players
	 * @param location
	 */
	public static void teleportPlayers(ArrayList<Player> players, Location location) {
		for (Player player : players) {
			player.teleport(location);
		}
	}

	/**
	 * Gives a potion effect to a group of players
	 * 
	 * @param players
	 * @param effect
	 */
	public static void effectPlayers(ArrayList<Player> players, PotionEffect effect) {
		for (Player player : players) {
			player.addPotionEffect(effect, false);
		}
	}

	/**
	 * Clears a player's armor
	 * 
	 * @param player
	 */
	public static void clearArmor(Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
	}

	/**
	 * Clears the armor slots of a group of players
	 * 
	 * @param players
	 */
	public static void clearArmor(ArrayList<Player> players) {
		for (Player player : players) {
			clearArmor(player);
		}
	}
}
