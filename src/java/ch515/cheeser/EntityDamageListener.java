/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * This listener prevents entities from taking damage
 * 
 * @author Caleb Heydon
 */
public class EntityDamageListener implements Listener {
	/**
	 * Prevents damage to entities
	 * 
	 * @param e
	 */
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		Location location = entity.getLocation();
		if (entity.getType() == EntityType.PLAYER) {
			e.setDamage(0);
			
			// Cancel fall damage for the cheeser
			if (Cheeser.getInstance().isGameRunning() && Cheeser.getInstance().isCheeser((Player) entity)
					&& e.getCause() == DamageCause.FALL) {
				e.setCancelled(true);
			}

			// Teleport player back to spawn if necessary
			if (location.getY() < Constants.MIN_HEIGHT) {
				Player player = (Player) entity;

				Location spawnLocation;
				if (Cheeser.getInstance().isGameRunning()) {
					if (Cheeser.getInstance().isCheeser(player)) {
						spawnLocation = new Location(player.getWorld(), Constants.CHEESER_SPAWN_X,
								Constants.CHEESER_SPAWN_Y, Constants.CHEESER_SPAWN_Z, 0f, 0f);
					} else if (Cheeser.getInstance().isHuman(player)) {
						Cheeser.getInstance().jail(player);
						return;
					} else {
						spawnLocation = new Location(player.getWorld(), Constants.SPAWN_X, Constants.SPAWN_Y,
								Constants.SPAWN_Z, 0f, 0f);
					}
				} else {
					spawnLocation = new Location(player.getWorld(), Constants.SPAWN_X, Constants.SPAWN_Y,
							Constants.SPAWN_Z, 0f, 0f);
				}

				player.teleport(spawnLocation);
			}
		}
	}
}
