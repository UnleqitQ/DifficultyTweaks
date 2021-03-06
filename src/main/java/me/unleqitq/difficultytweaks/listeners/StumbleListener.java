package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import net.objecthunter.exp4j.Expression;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
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
	private Map<UUID, Double> probMap;
	private Map<UUID, Integer> durationMap;
	
	public StumbleListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
		distanceMap = new HashMap<>();
		durationMap = new HashMap<>();
		probMap = new HashMap<>();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (!event.getPlayer().hasPermission(
				"difficultytweaks.bypass") && Configuration.Stumble.enable() && event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getPlayer().getGameMode() != GameMode.SPECTATOR) {
			double d = Objects.requireNonNullElse(event.getTo(), event.getFrom()).distance(event.getFrom());
			if (d > 0) {
				if (!probMap.containsKey(event.getPlayer().getUniqueId())) {
					probMap.put(event.getPlayer().getUniqueId(), Math.random());
				}
				if (event.getPlayer().isInWater())
					return;
				double distance = distanceMap.getOrDefault(event.getPlayer().getUniqueId(), 0.0);
				distance += d;
				int duration = event.getPlayer().getTicksLived() - durationMap.getOrDefault(
						event.getPlayer().getUniqueId(), event.getPlayer().getTicksLived());
				if (!durationMap.containsKey(event.getPlayer().getUniqueId())) {
					durationMap.put(event.getPlayer().getUniqueId(), event.getPlayer().getTicksLived());
				}
				if (duration > 10) {
					if (stumble(event.getPlayer(), distance)) {
						distance = 0;
						probMap.put(event.getPlayer().getUniqueId(), Math.random());
					}
					durationMap.put(event.getPlayer().getUniqueId(), event.getPlayer().getTicksLived());
				}
				distanceMap.put(event.getPlayer().getUniqueId(), distance);
			}
		}
	}
	
	private boolean stumble(Player player, double distance) {
		Expression expression = new Expression(Configuration.Stumble.getFunction());
		Calendar time = Calendar.getInstance();
		
		expression.setVariable("gm", (int) ((float) (player.getWorld().getTime() % 1000) / 1000 * 60));
		expression.setVariable("gh", (float) ((player.getWorld().getTime() / 1000 + 6) % 24));
		expression.setVariable("rm", time.get(Calendar.MINUTE));
		expression.setVariable("rh", time.get(Calendar.HOUR_OF_DAY));
		expression.setVariable("pitch", player.getLocation().getPitch());
		expression.setVariable("yaw", player.getLocation().getYaw());
		expression.setVariable("d", distance);
		double prob = expression.evaluate();
		
		if (probMap.get(player.getUniqueId()) < prob * 0.5) {
			if (Configuration.Stumble.damage() > 0) {
				player.damage(Configuration.Stumble.damage());
			}
			if (Math.random() < Configuration.Stumble.looseItems()) {
				for (int i = 0; i < player.getInventory().getSize(); i++) {
					ItemStack itemStack = player.getInventory().getItem(i);
					if (itemStack != null && itemStack.getType() != Material.AIR) {
						if (Math.random() < Configuration.Stumble.looseItem()) {
							Item entity = player.getWorld().dropItem(
									player.getLocation().add(Math.random() * 2 - 1, Math.random(),
											Math.random() * 2 - 1), itemStack);
							player.getInventory().setItem(i, new ItemStack(Material.AIR));
							entity.setPickupDelay(20);
						}
					}
				}
			}
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 6));
			player.sendTitle(ChatColor.RED.toString() + ChatColor.BOLD + "You Stumbled", "", 4, 16, 10);
			return true;
		}
		return false;
	}
	
}
