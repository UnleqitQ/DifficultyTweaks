package me.unleqitq.difficultytweaks;

import me.unleqitq.difficultytweaks.listeners.*;
import me.unleqitq.difficultytweaks.tasks.NetherBoilingTask;
import me.unleqitq.difficultytweaks.tasks.NetherNoWaterCauldronTask;
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
		new NetherNoWaterCauldronListener();
		
		Bukkit.getScheduler().runTaskTimer(this, new NetherBoilingTask(), 20, 10);
		Bukkit.getScheduler().runTaskTimer(this, new NetherNoWaterCauldronTask(), 20, 2);
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
