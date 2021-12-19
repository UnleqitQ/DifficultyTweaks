package me.unleqitq.difficultytweaks.listeners;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.DifficultyTweaks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.*;
import java.util.logging.Level;

public class FistBreakListener implements Listener {
	
	public static Map<Material, ToolType> types;
	
	public FistBreakListener() {
		if (Configuration.FistBreakDamage.enable()) {
			Bukkit.getPluginManager().registerEvents(this, DifficultyTweaks.getInstance());
			types = new HashMap<>();
			for (String type : Configuration.FistBreakDamage.types()) {
				if (Configuration.FistBreakDamage.Type.enable(type)) {
					new ToolType(type);
				}
			}
			
		}
	}
	
	@EventHandler
	public void onHitBlock(PlayerInteractEvent event) {
		if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
			return;
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = Objects.requireNonNull(event.getClickedBlock());
			Material tool = event.getMaterial();
			if (types.containsKey(block.getType())) {
				ToolType type = types.get(block.getType());
				if (!type.tools.contains(tool)) {
					if (tool == Material.AIR) {
						event.getPlayer().damage(2);
						EntityDamageByBlockEvent cause = new EntityDamageByBlockEvent(block, event.getPlayer(),
								EntityDamageEvent.DamageCause.CONTACT, 2);
						event.getPlayer().setLastDamageCause(cause);
						event.getPlayer().sendTitle(ChatColor.RED + "Ouch", ChatColor.GOLD + "Get some tools first", 0,
								20, 20);
					}
					else {
						ItemStack item = event.getItem();
						if (item.getItemMeta() instanceof Damageable) {
							Damageable meta = (Damageable) item.getItemMeta();
							meta.setDamage(meta.getDamage() + 2);
							item.setItemMeta(meta);
							if (meta.getDamage() >= tool.getMaxDurability()) {
								item.setAmount(item.getAmount() - 1);
							}
						}
						else {
							item.setAmount(item.getAmount() - 1);
							event.getPlayer().sendMessage("" + item.getAmount());
						}
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
					Bukkit.getLogger().log(Level.INFO, block);
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
