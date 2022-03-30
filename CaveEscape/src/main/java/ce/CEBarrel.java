package ce;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CEBarrel {
	Plugin plugin;
	Player player;
	CEGame ceg;
	FallingBlock barrel;
	
	public CEBarrel(Plugin plugin, Player player, CEGame ceg) {
		this.plugin = plugin;
		this.player = player;
		this.ceg = ceg;
		barrel = (FallingBlock)player.getWorld().spawnFallingBlock(player.getEyeLocation(), Material.BARREL, (byte)0);
		barrel.setVelocity(player.getLocation().getDirection().normalize().multiply(2));
	}
	void hit() {
		new BukkitRunnable() {
			public void run() {
				if(!barrel.isValid()) {
					this.cancel();
					return;
				}
				if(barrel.getLocation().getBlock().getType() == Material.LAVA) {
					//TODO マグマをつぶす処理
				}
				for(Entity ent:barrel.getNearbyEntities(1, 1, 1)) {
					if(ent == ceg.getMaster()) {
						((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 2));
						barrel.remove();
					}
				}
			}
		}.runTaskTimer(plugin, 0, 4);
	}

}
