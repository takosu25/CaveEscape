package ce;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CECommand implements CommandExecutor {

	Plugin plugin;
	CEGame ceg;
	
	public CECommand(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(args[0].equals("newgame")) {
			ceg = new CEGame(plugin);
		}else if(args[0].equals("join")) {
			if(sender instanceof Player) {
				Player player = (Player)sender;
				for(Player p:player.getWorld().getPlayers()) {
					ceg.addPlayers(p);
				}
			}
		}else if(args[0].equals("start")) {
			ceg.start();
		}
		return true;
	}

}
