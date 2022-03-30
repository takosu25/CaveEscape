package ce;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
	@Override
	boolean rightClickBlock(Block block) {
		if(block.getType() == Material.BARREL) {
			if(!player.getInventory().contains(Material.BARREL)) {
				ItemStack barrel = new ItemStack(Material.BARREL);
				ItemMeta mbarrel = barrel.getItemMeta();
				mbarrel.setDisplayName("水樽");
				List<String> lore = new ArrayList<String>();
				lore.add("水の入った樽");
				lore.add("マグマを黒曜石にすることが出来る。");
				lore.add("ヌシに当てることで減速を付与できる。");
				mbarrel.setLore(lore);
				barrel.setItemMeta(mbarrel);
				player.getInventory().addItem(barrel);
				
			}
			return true;
		}
		return false;
	}
	@Override
	void rightClickAll() {
		ItemStack item = player.getInventory().getItemInMainHand().clone();
		int index = player.getInventory().getHeldItemSlot();
		if(index == 0) {
			
		}else if(item.getType() == Material.BARREL) {
			player.getInventory().setItem(index, null);
			new CEBarrel(plugin, player, ceg);
		}
	}

}
