package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class DiamondArmorDamageListener implements Listener {
	
	
	public DiamondArmorDamageListener() {
		if (Configuration.DiamondArmorDamage.enable()) {
			Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
			
		}
	}
	
	@EventHandler
	public void onHitBlock(@NotNull PlayerMoveEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		PlayerInventory inv = event.getPlayer().getInventory();
		if (inv.getHelmet() != null && inv.getHelmet().getType() == Material.DIAMOND_HELMET) {
			event.getPlayer().sendTitle(ChatColor.RED + "Your Diamond Armor is very sharp", "", 0, 10, 5);
			event.getPlayer().damage(Configuration.DiamondArmorDamage.damageHelmet());
		}
		if (inv.getChestplate() != null && inv.getChestplate().getType() == Material.DIAMOND_CHESTPLATE) {
			event.getPlayer().sendTitle(ChatColor.RED + "Your Diamond Armor is very sharp", "", 0, 10, 5);
			event.getPlayer().damage(Configuration.DiamondArmorDamage.damageChestplate());
		}
		if (inv.getLeggings() != null && inv.getLeggings().getType() == Material.DIAMOND_LEGGINGS) {
			event.getPlayer().sendTitle(ChatColor.RED + "Your Diamond Armor is very sharp", "", 0, 10, 5);
			event.getPlayer().damage(Configuration.DiamondArmorDamage.damageLeggings());
		}
		if (inv.getBoots() != null && inv.getBoots().getType() == Material.DIAMOND_BOOTS) {
			event.getPlayer().sendTitle(ChatColor.RED + "Your Diamond Armor is very sharp", "", 0, 10, 5);
			event.getPlayer().damage(Configuration.DiamondArmorDamage.damageBoots());
		}
	}
	
}
