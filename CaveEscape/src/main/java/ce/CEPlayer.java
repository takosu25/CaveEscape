package ce;

import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
				boolean cancel = rightClickBlock(e.getClickedBlock());
				if(cancel) {
					e.setCancelled(true);
					return;
				}
			}
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				rightClickAll();
			}
		}
	}
	abstract boolean rightClickBlock(Block block);
	
	abstract void rightClickAll();

}
