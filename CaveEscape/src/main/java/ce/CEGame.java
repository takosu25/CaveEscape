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
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class CEGame {
	private List<Player> players = new ArrayList<Player>();
	private World world;
	Plugin plugin;
	private Vector[] spawnMaster = {new Vector(), new Vector(), new Vector()};
	private Vector[] spawnCave = {new Vector(0, -60, 0), new Vector(10, -60, 10), new Vector(20, -60, 20)};
	private HashMap<Player, CEPlayer> playerData = new HashMap<Player, CEPlayer>();
	Player master;
	private Vector[] spawnDiamond = {new Vector(40, -60, 10), new Vector(15, -60, 25), new Vector(10, -60, 15)};
	private Vector[] spawnFurnace = {new Vector(35, -60, 15), new Vector(20, -60, 10), new Vector(15, -60, 10), new Vector(10, -60, 20)};
	
	
	
	public CEGame(Plugin plugin) {
		world = Bukkit.getWorld("CEWorld");
		this.plugin = plugin;
	}
	
	public void start() {
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
