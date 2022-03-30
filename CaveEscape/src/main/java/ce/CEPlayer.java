package ce;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class CEPlayer implements Listener{
	Player player;
	Plugin plugin;
	CEGame ceg;
	
	public CEPlayer(Player player, Plugin plugin, CEGame ceg) {
		this.player = player;
		this.plugin = plugin;
		this.ceg = ceg;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
		player.setHealth(40);
		player.setFoodLevel(20);
		player.getInventory().clear();
		
	}
	//右クリックの処理
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(player == e.getPlayer()) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(e.getClickedBlock().getType() == Material.BARREL) {
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
				}
			}
		}
	}

}
