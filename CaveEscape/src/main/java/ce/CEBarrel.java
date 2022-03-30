package ce;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
		barrel.setVelocity(player.getLocation().getDirection().normalize().multiply(1.5));
		hit();
	}
	void hit() {
		new BukkitRunnable() {
			public void run() {
				if(!barrel.isValid()) {
					this.cancel();
					return;
				}
				if(barrel.getLocation().getBlock().getType() == Material.LAVA) {
					barrel.remove();
					lavaToObsidian(barrel.getLocation().clone());
					return;
				}
				for(Entity ent:barrel.getNearbyEntities(1.4, 1.4, 1.4)) {
					if(ent == ceg.getMaster()) {
						((Player)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 2));
						barrel.remove();
					}
				}
			}
		}.runTaskTimer(plugin, 0, 2);
	}

	void lavaToObsidian(Location l) {
		Material m = Material.OBSIDIAN;
		Location c = l.clone();
		c.add(0,-2,0);
		if(c.getBlock().getType() == Material.BEDROCK) {
			m = Material.CRYING_OBSIDIAN;
		}else {
			c.add(0,-1,0);
			if(c.getBlock().getType() == Material.BEDROCK) {
				m = Material.CRYING_OBSIDIAN;
			}
		}
		l.getBlock().setType(m);
		Vector[] vs = {new Vector(1,0,0),new Vector(-1,0,0),new Vector(0,1,0), new Vector(0,-1,0),new Vector(0,0,1),new Vector(0,0,-1)};
		for(Vector v:vs) {
			Location cl = l.clone();
			cl.add(v);
			if(cl.getBlock().getType() == Material.LAVA) {
				lavaToObsidian(cl.clone());
			}
		}
	}
}
