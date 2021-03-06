package me.unleqitq.difficultytweaks;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
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
		
		defaults.put("netherBoiling.enable", true);
		defaults.put("netherBoiling.damageHelmet", 0.5);
		defaults.put("netherBoiling.damageChestplate", 0.7);
		defaults.put("netherBoiling.damageLeggings", 0.5);
		defaults.put("netherBoiling.damageBoots", 0.3);
		
		defaults.put("diamondArmorDamage.enable", true);
		defaults.put("diamondArmorDamage.damageHelmet", 0.5);
		defaults.put("diamondArmorDamage.damageChestplate", 0.7);
		defaults.put("diamondArmorDamage.damageLeggings", 0.5);
		defaults.put("diamondArmorDamage.damageBoots", 0.3);
		
		defaults.put("stumble.enable", true);
		defaults.put("stumble.damage", 0.35);
		defaults.put("stumble.looseItems", 0.6);
		defaults.put("stumble.looseItem", 0.8);
		defaults.put("stumble.probability", "d/1000");
		
		{
			defaults.put("fistBreakDamage.log.enable", true);
			List<String> blocks = new ArrayList<>();
			blocks.add(Material.ACACIA_LOG.name());
			blocks.add(Material.BIRCH_LOG.name());
			blocks.add(Material.DARK_OAK_LOG.name());
			blocks.add(Material.JUNGLE_LOG.name());
			blocks.add(Material.OAK_LOG.name());
			blocks.add(Material.SPRUCE_LOG.name());
			blocks.add(Material.STRIPPED_ACACIA_LOG.name());
			blocks.add(Material.STRIPPED_BIRCH_LOG.name());
			blocks.add(Material.STRIPPED_DARK_OAK_LOG.name());
			blocks.add(Material.STRIPPED_JUNGLE_LOG.name());
			blocks.add(Material.STRIPPED_OAK_LOG.name());
			blocks.add(Material.STRIPPED_SPRUCE_LOG.name());
			blocks.add(Material.ACACIA_WOOD.name());
			blocks.add(Material.BIRCH_WOOD.name());
			blocks.add(Material.DARK_OAK_WOOD.name());
			blocks.add(Material.JUNGLE_WOOD.name());
			blocks.add(Material.OAK_WOOD.name());
			blocks.add(Material.SPRUCE_WOOD.name());
			blocks.add(Material.STRIPPED_ACACIA_WOOD.name());
			blocks.add(Material.STRIPPED_BIRCH_WOOD.name());
			blocks.add(Material.STRIPPED_DARK_OAK_WOOD.name());
			blocks.add(Material.STRIPPED_JUNGLE_WOOD.name());
			blocks.add(Material.STRIPPED_OAK_WOOD.name());
			blocks.add(Material.STRIPPED_SPRUCE_WOOD.name());
			List<String> tools = new ArrayList<>();
			tools.add(Material.WOODEN_AXE.name());
			tools.add(Material.STONE_AXE.name());
			tools.add(Material.GOLDEN_AXE.name());
			tools.add(Material.IRON_AXE.name());
			tools.add(Material.DIAMOND_AXE.name());
			tools.add(Material.NETHERITE_AXE.name());
			defaults.put("fistBreakDamage.log.blocks", blocks);
			defaults.put("fistBreakDamage.log.tools", tools);
		}
		
		{
			List<String> types = new ArrayList<>();
			types.add("log");
			defaults.put("fistBreakDamage.types", types);
		}
	}
	
	private Configuration() {}
	
	public static void loadConfig() {
		folder = DifficultyTweaks.getInstance().getDataFolder();
		//Create Folder
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		configFile = new File(folder, "config");
		
		DifficultyTweaks.getInstance().saveDefaultConfig();
		
		
		config = (YamlConfiguration) DifficultyTweaks.getInstance().getConfig();
	}
	
	public static void loadConfig0() {
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
	
	
	public static final class EndermanMount {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("endermanMount.enable");
		}
		
	}
	
	public static final class NetherNoWaterCauldron {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("netherNoWaterCauldron.enable");
		}
		
	}
	
	public static final class DamageSlowness {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("damageSlowness.enable");
		}
		
		public static int duration() {
			return Configuration.config.getInt("damageSlowness.duration");
		}
		
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
	
	public static final class NetherBoiling {
		
		public static boolean enable() {
			return Configuration.config.getBoolean("netherBoiling.enable");
		}
		
		public static double damageHelmet() {
			return Configuration.config.getDouble("netherBoiling.damageHelmet");
		}
		
		public static double damageChestplate() {
			return Configuration.config.getDouble("netherBoiling.damageChestplate");
		}
		
		public static double damageLeggings() {
			return Configuration.config.getDouble("netherBoiling.damageHelmet");
		}
		
		public static double damageBoots() {
			return Configuration.config.getDouble("netherBoiling.damageHelmet");
		}
		
	}
	
	public static final class Stumble {
		
		private static Expression expression;
		
		public static Expression getFunction() {
			if (expression == null) {
				expression = new ExpressionBuilder(probability()).variables("x", "y", "z", "pitch", "yaw", "d").build();
				ValidationResult result = expression.validate(false);
				if (!result.isValid()) {
					Bukkit.getLogger().log(Level.SEVERE, "Probability function not correct");
					Bukkit.getLogger().log(Level.SEVERE, result.toString());
					for (String s : result.getErrors()) {
						Bukkit.getLogger().log(Level.SEVERE, s);
					}
					Bukkit.getLogger().log(Level.SEVERE, "Input: " + probability());
					expression = new ExpressionBuilder("d/1000").variables("x", "y", "z", "pitch", "yaw", "d").build();
				}
			}
			return expression;
		}
		
		public static boolean enable() {
			return Configuration.config.getBoolean("stumble.enable");
		}
		
		public static double damage() {
			return Configuration.config.getDouble("stumble.damage");
		}
		
		public static double looseItems() {
			return Configuration.config.getDouble("stumble.looseItems");
		}
		
		public static double looseItem() {
			return Configuration.config.getDouble("stumble.looseItem");
		}
		
		public static String probability() {
			return Configuration.config.getString("stumble.probability");
		}
		
		
	}
	
	public static final class DiamondArmorDamage {
		
		public static boolean enable() {return Configuration.config.getBoolean("diamondArmorDamage.enable");}
		
		public static double damageHelmet() {
			return Configuration.config.getDouble("diamondArmorDamage.damageHelmet");
		}
		
		public static double damageChestplate() {
			return Configuration.config.getDouble("diamondArmorDamage.damageChestplate");
		}
		
		public static double damageLeggings() {
			return Configuration.config.getDouble("diamondArmorDamage.damageHelmet");
		}
		
		public static double damageBoots() {
			return Configuration.config.getDouble("diamondArmorDamage.damageHelmet");
		}
		
	}
	
}
