package ce;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CEMaster extends CEPlayer {
	private boolean cooldown = true;

	public CEMaster(Player player, Plugin plugin, CEGame ceg) {
		super(player, plugin, ceg);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public void cooldown() {
		cooldown = false;
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 100));
		new BukkitRunnable() {
			public void run() {
				cooldown = true;
			}
		}.runTaskLater(plugin, 100);
	}
	
	
	//getter&setter
	public boolean getCooldown() {
		return cooldown;
	}
	

}
