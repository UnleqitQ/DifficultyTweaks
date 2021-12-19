package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	public DeathListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
	}
	
	@SuppressWarnings ("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity().getLastDamageCause().getDamage(EntityDamageEvent.DamageModifier.MAGIC) == -1) {
			Bukkit.broadcastMessage(Color.RED + event.getEntity().getDisplayName() + " did not think of making tools!");
		}
	}
	
}
