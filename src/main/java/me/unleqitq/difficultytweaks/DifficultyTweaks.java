package me.unleqitq.difficultytweaks;

import me.unleqitq.difficultytweaks.listeners.EndermanListener;
import me.unleqitq.difficultytweaks.listeners.FistBreakListener;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class DifficultyTweaks extends JavaPlugin {
	
	private static DifficultyTweaks instance;
	
	@Override
	public void onEnable() {
		Configuration.loadConfig();
		new EndermanListener();
		new FistBreakListener();
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	
	public static DifficultyTweaks getInstance() {
		return instance;
	}
	
}
