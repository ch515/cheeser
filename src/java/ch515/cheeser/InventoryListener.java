/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * This class listens for a player to try to change their inventory and stops
 * them
 * 
 * @author Caleb Heydon
 */
public class InventoryListener implements Listener {
	/**
	 * Called when a player interacts with their inventory
	 * 
	 * @param e
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked().getGameMode() == GameMode.ADVENTURE) {
			e.setCancelled(true);
		}
	}
}
