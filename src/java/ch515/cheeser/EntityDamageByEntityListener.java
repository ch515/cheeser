/**
 * Copyright 2020 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * This class listens for when the players to hit each other
 * 
 * @author Caleb Heydon
 */
public class EntityDamageByEntityListener implements Listener {
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();

		if (e.getEntityType() == EntityType.PLAYER && damager instanceof Player) {
			Player playerDamager = (Player) damager;

			if (Cheeser.getInstance().isGameRunning() && Cheeser.getInstance().isCheeser((Player) damager)
					&& playerDamager.getItemInHand().getType() == Material.SPONGE) {
				Player player = (Player) e.getEntity();

				Cheeser.getInstance().jail(player);
			}
		}
	}
}
