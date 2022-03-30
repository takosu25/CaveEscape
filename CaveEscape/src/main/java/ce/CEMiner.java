package ce;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CEMiner extends CEPlayer {

	public CEMiner(Player player, Plugin plugin, CEGame ceg) {
		super(player, plugin, ceg);
		player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
		
	}
	public void damage(double damage) {
		if(player.getHealth() - damage <= 0) {
			
		}else {
			player.setHealth(player.getHealth() - damage);
		}
	}

}
