package me.unleqitq.difficultytweaks;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Configuration {
	
	private static File folder;
	private static File configFile;
	private static YamlConfiguration config;
	
	private static Map<String, Object> defaults = new HashMap<>();
	
	static {
		createDefaults();
	}
	
	private static void createDefaults() {
		defaults.put("fistBreakDamage.enable", true);
		{
			defaults.put("fistBreakDamage.log.enable", true);
			List<Material> blocks = new ArrayList<>();
			blocks.add(Material.ACACIA_LOG);
			blocks.add(Material.BIRCH_LOG);
			blocks.add(Material.DARK_OAK_LOG);
			blocks.add(Material.JUNGLE_LOG);
			blocks.add(Material.OAK_LOG);
			blocks.add(Material.SPRUCE_LOG);
			blocks.add(Material.STRIPPED_ACACIA_LOG);
			blocks.add(Material.STRIPPED_BIRCH_LOG);
			blocks.add(Material.STRIPPED_DARK_OAK_LOG);
			blocks.add(Material.STRIPPED_JUNGLE_LOG);
			blocks.add(Material.STRIPPED_OAK_LOG);
			blocks.add(Material.STRIPPED_SPRUCE_LOG);
			List<Material> tools = new ArrayList<>();
			tools.add(Material.WOODEN_AXE);
			tools.add(Material.STONE_AXE);
			tools.add(Material.GOLDEN_AXE);
			tools.add(Material.IRON_AXE);
			tools.add(Material.DIAMOND_AXE);
			tools.add(Material.NETHERITE_AXE);
			defaults.put("fistBreakDamage.log.blocks", blocks);
			defaults.put("fistBreakDamage.log.tools", tools);
		}
	}
	
	private Configuration() {}
	
	public static void loadConfig() {
		folder = DifficultyTweaks.getInstance().getDataFolder();
		folder.mkdirs();
		configFile = new File(folder, "config.yml");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not create config", e);
			}
		}
		config = new YamlConfiguration();
		if (configFile.exists()) {
			config = YamlConfiguration.loadConfiguration(configFile);
		}
		else {
			Bukkit.getLogger().log(Level.WARNING, "Config not found");
		}
		addDefaults();
		if (configFile.exists()) {
			try {
				config.save(configFile);
			} catch (IOException e) {
				Bukkit.getLogger().log(Level.WARNING, "Could not save config", e);
			}
		}
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	private static void addDefaults() {
		for (Map.Entry<String, Object> entry : defaults.entrySet()) {
			if (!config.contains(entry.getKey(), true)) {
				config.set(entry.getKey(), entry.getValue());
				continue;
			}
			if (entry.getValue() instanceof String) {
				if (!config.isString(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Integer) {
				if (!config.isInt(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Boolean) {
				if (!config.isBoolean(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof Double) {
				if (!config.isDouble(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
			if (entry.getValue() instanceof List) {
				List l = (List) entry.getValue();
				if (!config.isList(entry.getKey())) {
					config.set(entry.getKey(), entry.getValue());
				}
				continue;
			}
		}
	}
	
	public static void setConfig(YamlConfiguration config) {
		Configuration.config = config;
	}
	
	public static YamlConfiguration getConfig() {
		return config;
	}
	
	public static final class FistBreakDamage {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("fistBreakDamage.enable");
		}
		
		@NotNull
		public static List<String> types() {
			return Configuration.config.getStringList("fistBreakDamage.types");
		}
		
		public static final class Type {
			
			public static boolean enable(String type) {
				return Configuration.config.getBoolean("fistBreakDamage." + type + ".enable");
			}
			
			@NotNull
			public static List<String> blocks(String type) {
				return Configuration.config.getStringList("fistBreakDamage." + type + ".blocks");
			}
			
			@NotNull
			public static List<String> tools(String type) {
				return Configuration.config.getStringList("fistBreakDamage." + type + ".tools");
			}
			
		}
		
	}
	
}
