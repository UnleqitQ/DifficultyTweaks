package me.unleqitq.difficultytweaks;

import me.unleqitq.difficultytweaks.listeners.*;
import me.unleqitq.difficultytweaks.tasks.NetherBoilingTask;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class DifficultyTweaks extends JavaPlugin {
	
	private static DifficultyTweaks instance;
	
	public DifficultyTweaks() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		Configuration.loadConfig();
		new EndermanListener();
		new FistBreakListener();
		new DiamondArmorDamageListener();
		new DamageSlownessListener();
		new StumbleListener();
		
		if (Configuration.NetherBoiling.enable()) {
			Bukkit.getScheduler().runTaskTimer(this, new NetherBoilingTask(), 20, 10);
		}
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);
	}
	
	public static DifficultyTweaks getInstance() {
		return instance;
	}
	
}
