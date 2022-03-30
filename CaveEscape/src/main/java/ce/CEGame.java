package ce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class CEGame implements Listener{
	private List<Player> players = new ArrayList<Player>();
	private World world;
	Plugin plugin;
	private Vector[] spawnMaster = {new Vector(65, -60, 17), new Vector(59, -60, 106), new Vector(96, -60, 60)};
	private Vector[] spawnCave = {new Vector(18, -60, 63), new Vector(18, -60, 59), new Vector(18, -60, 67)};
	private HashMap<Player, CEPlayer> playerData = new HashMap<Player, CEPlayer>();
	Player master;
	private Vector[] spawnDiamond = {new Vector(27, -60, 35), new Vector(57, -59, 22), new Vector(89, -60, 28), new Vector(86, -60, 82), new Vector(70, -60, 93), new Vector(57, -60, 103)};
	private Vector[] spawnFurnace = {new Vector(39, -60, 73), new Vector(72, -60, 87), new Vector(86, -60, 53), new Vector(47, -60, 48), new Vector(50, -60, 29), new Vector(30, -60, 103), new Vector(95, -60, 91)};
	private Vector[] spawnPrison = {new Vector(65, -60, 9), new Vector(104, -60, 60), new Vector(59, -60, 115)};
	private Vector[] spawnObsidian = {new Vector(55.5, -60, 61.5), new Vector(54.5, -58, 61.5)};
	private Vector[] spawnWater = {new Vector(30, -60, 101)};
	
	
	public CEGame(Plugin plugin) {
		world = Bukkit.getWorld("CEWorld");
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void start() {
		resetBlock(spawnDiamond);
		resetBlock(spawnFurnace);
		resetBlock(spawnWater);
		Collections.shuffle(players);
		master = players.get(0);
		Random rnd = new Random();
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			playerData.put(p, p == master? new CEMaster(p, plugin, this) : new CEMiner(p, plugin, this));
			p.teleport(p == master ? spawnMaster[rnd.nextInt(spawnMaster.length)].toLocation(world) : spawnCave[i-1].toLocation(world));
			
		}
		//ダイヤモンドスポーン&かまどスポーン
		randomPut(3, rnd, spawnDiamond, Material.DIAMOND_ORE);
		randomPut(4, rnd, spawnFurnace, Material.FURNACE);
		randomPut(2, rnd, spawnObsidian, Material.CRYING_OBSIDIAN);
		randomPut(1, rnd, spawnWater, Material.BARREL);
	}
	
	/**
	 * ランダムで何か置きたい用
	 * @param time 置きたいブロックの数
	 * @param rnd ランダム
	 * @param locations 参照する座標
	 * @param m 置きたいブロックの種類
	 */
	void randomPut(int time, Random rnd, Vector[] locations, Material m) {
		List<Integer> index = new ArrayList<Integer>();
		
		for(int i = 0; i < time; i++) {
			int x;
			do {
				x = rnd.nextInt(locations.length);
			}while(index.contains(x));
			index.add(x);
			world.getBlockAt(locations[x].toLocation(world)).setType(m);
			
		}
	}
	void resetBlock(Vector[] location) {
		for(Vector v :location) {
			v.toLocation(world).getBlock().setType(Material.AIR);
		}
	}
	//ダイヤモンド鉱石&泣く黒曜石以外のブロック破壊禁止
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getType() == Material.DIAMOND_ORE) {
			e.setDropItems(false);
			e.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_ORE));
		}else {
			if(e.getBlock().getType() == Material.CRYING_OBSIDIAN) {
				e.setDropItems(false);
				return;
			}
			e.setCancelled(true);
		}
	}
	//食料ゲージ固定
	@EventHandler
	public void onFoodLevel(FoodLevelChangeEvent e) {
		if(players.contains(e.getEntity())) {
			e.setCancelled(true);
		}
	}
	//ブロック設置禁止
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(players.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	//体力回復禁止
	@EventHandler
	public void onRegainHealth(EntityRegainHealthEvent e) {
		if(players.contains(e.getEntity())){
			e.setCancelled(true);
		}
	}
	//殴った時の処理
	@EventHandler
	public void onTouch(EntityDamageByEntityEvent e) {
		if(e.getDamager().equals(master) && players.contains(e.getEntity())) {
			Player miner = (Player)e.getEntity();
			if(((CEMaster)playerData.get(master)).getCooldown()) {
				((CEMiner)playerData.get(miner)).damage(20);
				((CEMaster)playerData.get(master)).cooldown();
				e.setDamage(0);
			}
		}else if(players.contains(e.getDamager()) && e.getDamager() != master) {
			e.setCancelled(true);
		}
	}
	
	
	
	
	
	
	
	
	public List<Player> getPlayers(){
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public void addPlayers(Player player) {
		players.add(player);
	}

}
