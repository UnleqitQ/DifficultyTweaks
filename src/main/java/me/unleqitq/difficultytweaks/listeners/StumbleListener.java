package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import net.objecthunter.exp4j.Expression;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class StumbleListener implements Listener {
	
	private Map<UUID, Double> distanceMap;
	
	public StumbleListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
		distanceMap = new HashMap<>();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (Configuration.Stumble.enable() && event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getPlayer().getGameMode() != GameMode.SPECTATOR) {
			double d = Objects.requireNonNullElse(event.getTo(), event.getFrom()).distance(event.getFrom());
			if (d > 0) {
				double distance = distanceMap.getOrDefault(event.getPlayer().getUniqueId(), 0.0);
				distance += d;
				if (stumble(event.getPlayer(), distance)) {
					distance = 0;
				}
				distanceMap.put(event.getPlayer().getUniqueId(), distance);
			}
		}
	}
	
	private boolean stumble(Player player, double distance) {
		Expression expression = new Expression(Configuration.Stumble.getFunction());
		expression.setVariable("x", player.getLocation().getX());
		expression.setVariable("y", player.getLocation().getY());
		expression.setVariable("z", player.getLocation().getZ());
		expression.setVariable("pitch", player.getLocation().getPitch());
		expression.setVariable("yaw", player.getLocation().getYaw());
		expression.setVariable("d", distance);
		double prob = expression.evaluate();
		if (Math.random() < prob) {
			if (Configuration.Stumble.damage() > 0) {
				player.damage(Configuration.Stumble.damage());
			}
			if (Math.random() < Configuration.Stumble.looseItems()) {
				List<ItemStack> inv = List.of(player.getInventory().getStorageContents());
				for (ItemStack itemStack : inv) {
					if (Math.random() < Configuration.Stumble.looseItem()) {
						player.getInventory().remove(itemStack);
						player.getWorld().dropItem(
								player.getLocation().add(Math.random() * 2 - 1, Math.random(), Math.random() * 2 - 1),
								itemStack);
					}
				}
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 6));
			return true;
		}
		return false;
	}
	
}
