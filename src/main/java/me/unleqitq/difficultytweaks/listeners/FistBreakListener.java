package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

public class FistBreakListener implements Listener {
	
	public static Map<Material, ToolType> types;
	
	public FistBreakListener() {
		Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
		types = new HashMap<>();
		for (String type : Configuration.FistBreakDamage.types()) {
			new ToolType(type);
		}
	}
	
	@EventHandler
	public void onHitBlock(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = Objects.requireNonNull(event.getClickedBlock());
			ItemStack item = Objects.requireNonNull(event.getItem());
			Material tool = event.getMaterial();
			if (types.containsKey(block.getType())) {
				ToolType type = types.get(block.getType());
				if (!type.tools.contains(tool)) {
					if (tool == Material.AIR) {
						event.getPlayer().damage(2);
						event.getPlayer().sendTitle(ChatColor.RED + "Ouch", ChatColor.GOLD + "Get some tools first", 0,
								20, 20);
					}
					else {
						Bukkit.getPluginManager().callEvent(new PlayerItemDamageEvent(event.getPlayer(), item, 2));
						event.getPlayer().sendTitle(ChatColor.RED + "Damn it",
								ChatColor.GOLD + "Get some appropriate tools first", 0, 20, 20);
					}
				}
			}
		}
	}
	
	private static class ToolType {
		
		String name;
		Set<Material> blocks;
		Set<Material> tools;
		
		public ToolType(String name) {
			this.name = name;
			List<String> toolList = Configuration.FistBreakDamage.Type.tools(name);
			List<String> blockList = Configuration.FistBreakDamage.Type.blocks(name);
			blocks = new HashSet<>();
			tools = new HashSet<>();
			for (String block : blockList) {
				try {
					blocks.add(Material.getMaterial(block));
					FistBreakListener.types.put(Material.getMaterial(block), this);
				} catch (NullPointerException e) {
					Bukkit.getLogger().log(Level.WARNING, "Did not recognize Material " + block);
					Bukkit.getLogger().log(Level.WARNING, "Please check your config");
				}
			}
			for (String tool : toolList) {
				try {
					tools.add(Material.getMaterial(tool));
				} catch (NullPointerException e) {
					Bukkit.getLogger().log(Level.WARNING, "Did not recognize Material " + tool);
					Bukkit.getLogger().log(Level.WARNING, "Please check your config");
				}
			}
		}
		
	}
	
}
