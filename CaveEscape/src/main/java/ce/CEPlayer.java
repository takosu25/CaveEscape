package ce;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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

}
