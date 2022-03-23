package ce;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class CEGame {
	private List<Player> players = new ArrayList<Player>();
	private World world;
	Plugin plugin;
	private Vector[] spawnCave = {new Vector(0, -60, 0), new Vector(10, -60, 10)};
	private HashMap<Player, CEPlayer> playerData = new HashMap<Player, CEPlayer>();
	
	
	public CEGame(Plugin plugin) {
		world = Bukkit.getWorld("CEWorld");
		this.plugin = plugin;
	}
	public void start() {
		for(int i = 0; i < spawnCave.length; i++) {
			playerData.put(players.get(i), new CEPlayer(players.get(i), plugin, this));
			players.get(i).teleport(spawnCave[i].toLocation(world));
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
