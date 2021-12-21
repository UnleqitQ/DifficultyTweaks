package me.unleqitq.difficultytweaks.tasks;

import me.unleqitq.difficultytweaks.Configuration;
import me.unleqitq.difficultytweaks.listeners.NetherNoWaterCauldronListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import java.util.Map;

public class NetherNoWaterCauldronTask implements Runnable {
	
	@Override
	@SuppressWarnings ("deprecation")
	public void run() {
		if (!Configuration.NetherNoWaterCauldron.enable())
			return;
		for (Map.Entry<Location, Long> entry : NetherNoWaterCauldronListener.cauldrons.entrySet()) {
			Block block = entry.getKey().getBlock();
			if (block.getType() == Material.WATER_CAULDRON) {
				long ticks = block.getWorld().getGameTime();
				if (ticks - entry.getValue() > 6) {
					BlockState state = block.getState();
					if (state.getRawData() > 1) {
						entry.setValue(ticks);
						state.setRawData((byte) (state.getRawData() - 1));
						state.update();
					}
					else {
						block.setType(Material.CAULDRON);
					}
				}
			}
			else {
				NetherNoWaterCauldronListener.cauldrons.remove(entry.getKey());
			}
		}
	}
	
}
