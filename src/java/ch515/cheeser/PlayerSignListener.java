/**
 * Copyright 2019 Caleb Heydon
 */

package ch515.cheeser;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * This class listens for right clicks on signs
 * 
 * @author Caleb Heydon
 */
public class PlayerSignListener implements Listener {
	/**
	 * Returns the text of the sign
	 * 
	 * @param sign
	 * @return
	 */
	private static String getSignText(Sign sign) {
		return sign.getLine(1);
	}

	@EventHandler
	public void onPlayerClickSign(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}

		Player player = e.getPlayer();
		Material material = e.getClickedBlock().getType();
		Action action = e.getAction();

		if (material == Material.WALL_SIGN && action == Action.RIGHT_CLICK_BLOCK) {
			player.playSound(player.getLocation(), Sound.WOOD_CLICK, 1f, 0.75f);

			Sign sign = (Sign) e.getClickedBlock().getState();
			String text = getSignText(sign);

			if ("lobby".equalsIgnoreCase(text)) {
				Util.joinServer(player, "lobby");
			} else if ("start".equalsIgnoreCase(text)) {
				Cheeser.getInstance().startGame();
			}
		}
	}
}
