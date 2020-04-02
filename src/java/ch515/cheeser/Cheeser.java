/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class coordinates the minigame
 * 
 * @author Caleb Heydon
 */
public class Cheeser implements Runnable {
	private static Cheeser instance;

	/**
	 * Returns the instance of the minigame
	 * 
	 * @return
	 */
	public static Cheeser getInstance() {
		if (instance == null) {
			instance = new Cheeser();
		}

		return instance;
	}

	private GameState gameState;
	private ArrayList<Player> players;

	private Player cheeser;
	private ArrayList<Player> humans;

	/**
	 * Starts the minigame
	 */
	public void startGame() {
		if (gameState != GameState.NOT_STARTED) {
			return;
		}
		gameState = GameState.STARTING;

		Thread gameThread = new Thread(this);
		gameThread.setName("GameThread");
		gameThread.start();
	}

	/**
	 * Returns true if the game is running
	 * 
	 * @return
	 */
	public boolean isGameRunning() {
		return (gameState == GameState.IN_PROGRESS);
	}

	/**
	 * Returns true if the player is the cheeser
	 * 
	 * @param player
	 * @return
	 */
	public boolean isCheeser(Player player) {
		return (player == cheeser);
	}

	/**
	 * Returns true if the player is a human
	 * 
	 * @param player
	 * @return
	 */
	public boolean isHuman(Player player) {
		return humans.contains(player);
	}

	/**
	 * Sends a player to the jail
	 * 
	 * @param player
	 */
	public void jail(Player player) {
		if (!isHuman(player)) {
			return;
		}

		Util.sendMessageToEveryone("The cheeser got " + ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET);

		Location location = new Location(player.getWorld(), Constants.JAIL_SPAWN_X, Constants.JAIL_SPAWN_Y,
				Constants.JAIL_SPAWN_Z, 0f, 0f);
		player.teleport(location);
	}

	@Override
	public void run() {
		if (Bukkit.getOnlinePlayers().size() < Constants.MIN_PLAYERS) {
			Util.sendMessageToEveryone(
					ChatColor.RED + "THERE ARE NOT ENOUGH PLAYERS TO START THE GAME" + ChatColor.RESET);

			gameState = GameState.NOT_STARTED;
			return;
		}

		for (int i = 10; i > 0; i--) {
			Util.sendMessageToEveryone(ChatColor.RED + "Game starting in " + i + ChatColor.RESET);
			Util.playSoundForEveryone(Sound.CHICKEN_EGG_POP, 1f, 0.75f);

			if (Bukkit.getOnlinePlayers().size() < Constants.MIN_PLAYERS) {
				Util.sendMessageToEveryone(
						ChatColor.RED + "THERE ARE NO LONGER ENOUGH PLAYERS TO START THE GAME" + ChatColor.RESET);

				gameState = GameState.NOT_STARTED;
				return;
			}

			Timer.sleep(1);
		}

		Util.sendMessageToEveryone(Constants.GAME_START_MESSAGE);
		Util.playSoundForEveryone(Sound.SUCCESSFUL_HIT, 1f, 0.1f);

		gameState = GameState.IN_PROGRESS;
		Timer.sleep(1);

		players.clear();
		for (Player player : Bukkit.getOnlinePlayers()) {
			Util.runInPlugin(() -> {
				player.setGameMode(GameMode.ADVENTURE);
				player.setAllowFlight(false);
			});
			players.add(player);
		}

		if (players.size() < Constants.MIN_PLAYERS) {
			Util.sendMessageToEveryone(
					ChatColor.RED + "THERE ARE NOT ENOUGH PLAYERS TO START THE GAME" + ChatColor.RESET);

			gameState = GameState.NOT_STARTED;
			return;
		}

		cheeser = Util.getRandomPlayer(players);
		Util.sendMessageToEveryone("The cheeser is " + ChatColor.YELLOW + cheeser.getDisplayName() + ChatColor.RESET);

		humans.clear();
		humans.addAll(players);
		humans.remove(cheeser);

		Util.clearPlayerInventories(players);
		Util.clearArmor(players);

		cheeser.getInventory().setHelmet(new ItemStack(Material.SPONGE));

		ItemStack cheese = new ItemStack(Material.SPONGE);
		ItemMeta cheeseMeta = cheese.getItemMeta();
		cheeseMeta.setDisplayName("Cheese");
		cheese.setItemMeta(cheeseMeta);

		cheeser.getInventory().setHeldItemSlot(0);
		cheeser.getInventory().setItemInHand(cheese);

		Util.runInPlugin(() -> cheeser
				.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 10, false, false)));
		Util.runInPlugin(() -> Util.effectPlayers(humans,
				new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false)));

		cheeser.teleport(new Location(cheeser.getWorld(), Constants.CHEESER_SPAWN_X, Constants.CHEESER_SPAWN_Y,
				Constants.CHEESER_SPAWN_Z, 0f, 0f));
		Util.teleportPlayers(humans, new Location(cheeser.getWorld(), Constants.HUMAN_SPAWN_X, Constants.HUMAN_SPAWN_Y,
				Constants.HUMAN_SPAWN_Z, Constants.HUMAN_SPAWN_YAW, 0f));
	}

	private Cheeser() {
		gameState = GameState.NOT_STARTED;
		players = new ArrayList<Player>();
		humans = new ArrayList<Player>();
	}

	private enum GameState {
		NOT_STARTED, STARTING, IN_PROGRESS
	}
}
