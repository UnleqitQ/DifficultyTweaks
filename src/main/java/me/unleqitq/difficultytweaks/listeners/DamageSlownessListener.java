package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageSlownessListener implements Listener {
	
	public DamageSlownessListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.FALL) {
				((LivingEntity) event.getEntity()).addPotionEffect(
						new PotionEffect(PotionEffectType.SLOW, 40, (int) (event.getDamage() / 2)));
			}
		}
	}
	
}
