package me.unleqitq.difficultytweaks.tasks;

import me.unleqitq.difficultytweaks.Configuration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class NetherBoilingTask implements Runnable {
	
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() != GameMode.SPECTATOR && player.getGameMode() != GameMode.CREATIVE) {
				if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
					PlayerInventory inv = player.getInventory();
					if (inv.getChestplate().getType() == Material.DIAMOND_CHESTPLATE || inv.getChestplate().getType() == Material.IRON_CHESTPLATE || inv.getChestplate().getType() == Material.GOLDEN_CHESTPLATE || inv.getChestplate().getType() == Material.NETHERITE_CHESTPLATE) {
						player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
						player.damage(Configuration.NetherBoiling.damagePerSecond() * 0.5);
					}
				}
			}
		}
	}
	
}
