package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NetherNoWaterCauldronListener implements Listener {
	
	public static ConcurrentMap<Location, Long> cauldrons;
	
	public NetherNoWaterCauldronListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
		cauldrons = new ConcurrentHashMap<>();
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (Configuration.NetherNoWaterCauldron.enable()) {
			if (!event.getPlayer().hasPermission(
					"difficultytweaks.bypass") && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (Objects.requireNonNull(
						event.getClickedBlock()).getType() == Material.CAULDRON || event.getClickedBlock().getType() == Material.WATER_CAULDRON) {
					if (event.getClickedBlock().getWorld().getEnvironment() == World.Environment.NETHER) {
						cauldrons.put(event.getClickedBlock().getLocation(),
								event.getClickedBlock().getWorld().getGameTime());
					}
				}
			}
		}
	}
	
}
