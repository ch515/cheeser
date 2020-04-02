/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * This listener stops explosions from destroying blocks
 * 
 * @author Caleb Heydon
 */
public class ExplosionListener implements Listener {
	/**
	 * Prevents blocks from being destroyed by entity explosions
	 * 
	 * @param e
	 */
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}
}
