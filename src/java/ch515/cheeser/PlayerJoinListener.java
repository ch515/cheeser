/**
 * Copyright 2019 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This listener listens for players joining the server
 * 
 * @author Caleb Heydon
 */
public class PlayerJoinListener implements Listener {
	/**
	 * This method is called when a player joins the server
	 * 
	 * @param e
	 */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		player.sendMessage(Constants.WELCOME_MESSAGE);

		Util.initPlayer(player);
	}
}
