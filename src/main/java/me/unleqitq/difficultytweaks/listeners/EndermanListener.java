package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityMountEvent;

public class EndermanListener implements Listener {
	
	public EndermanListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
	}
	
	@EventHandler
	public void onMount(EntityMountEvent event) {
		if (event.getEntityType() == EntityType.ENDERMAN) {
			event.setCancelled(true);
		}
	}
	
}
