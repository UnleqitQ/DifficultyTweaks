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
					if (inv.getHelmet() != null) {
						Material type = inv.getHelmet().getType();
						if (type == Material.DIAMOND_HELMET || type == Material.IRON_HELMET || type == Material.GOLDEN_HELMET || type == Material.NETHERITE_HELMET) {
							player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
							player.damage(Configuration.NetherBoiling.damageHelmet());
						}
					}
					if (inv.getChestplate() != null) {
						Material type = inv.getChestplate().getType();
						if (type == Material.DIAMOND_CHESTPLATE || type == Material.IRON_CHESTPLATE || type == Material.GOLDEN_CHESTPLATE || type == Material.NETHERITE_CHESTPLATE) {
							player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
							player.damage(Configuration.NetherBoiling.damageChestplate());
						}
					}
					if (inv.getLeggings() != null) {
						Material type = inv.getLeggings().getType();
						if (type == Material.DIAMOND_LEGGINGS || type == Material.IRON_LEGGINGS || type == Material.GOLDEN_LEGGINGS || type == Material.NETHERITE_LEGGINGS) {
							player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
							player.damage(Configuration.NetherBoiling.damageLeggings());
						}
					}
					if (inv.getBoots() != null) {
						Material type = inv.getBoots().getType();
						if (type == Material.DIAMOND_BOOTS || type == Material.IRON_BOOTS || type == Material.GOLDEN_BOOTS || type == Material.NETHERITE_BOOTS) {
							player.sendTitle(ChatColor.RED + "You are boiling in your armor", "", 0, 10, 5);
							player.damage(Configuration.NetherBoiling.damageBoots());
						}
					}
				}
			}
		}
	}
	
}
