/**
 * Copyright 2019 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.ChatColor;

/**
 * This class holds all of the plugin constants
 * 
 * @author Caleb Heydon
 */
public class Constants {
	public static final String WELCOME_MESSAGE = ChatColor.YELLOW + "WELCOME TO CHEESER!" + ChatColor.RESET;
	public static final String GAME_START_MESSAGE = ChatColor.GREEN + "GAME STARTING" + ChatColor.RESET;

	public static final double SPAWN_X = 0.5;
	public static final double SPAWN_Y = 24;
	public static final double SPAWN_Z = -24.5;

	public static final double JAIL_SPAWN_X = -5;
	public static final double JAIL_SPAWN_Y = 41;
	public static final double JAIL_SPAWN_Z = -109;
	
	public static final double HUMAN_SPAWN_X = 17;
	public static final double HUMAN_SPAWN_Y = 44;
	public static final double HUMAN_SPAWN_Z = -83;
	
	public static final float HUMAN_SPAWN_YAW = 90f;
	
	public static final double CHEESER_SPAWN_X = -43;
	public static final double CHEESER_SPAWN_Y = 55;
	public static final double CHEESER_SPAWN_Z = -141;

	public static final double MIN_HEIGHT = 0;

	public static final int MIN_PLAYERS = 2;
}
