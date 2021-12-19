package me.unleqitq.difficultytweaks.tasks;

import me.unleqitq.difficultytweaks.Configuration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class NetherBoilingTask implements Runnable {
	
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() != GameMode.SPECTATOR && player.getGameMode() != GameMode.CREATIVE) {
				if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
					PlayerInventory inv = player.getInventory();
					ItemStack item = inv.getChestplate();
					if (item != null) {
						Material type = item.getType();
						if (type == Material.DIAMOND_CHESTPLATE || type == Material.IRON_CHESTPLATE || type == Material.GOLDEN_CHESTPLATE || type == Material.NETHERITE_CHESTPLATE) {
							player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
							player.damage(Configuration.NetherBoiling.damagePerSecond());
						}
						
					}
				}
			}
		}
	}
	
}
