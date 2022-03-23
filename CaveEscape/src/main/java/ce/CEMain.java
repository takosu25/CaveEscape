package ce;

import org.bukkit.plugin.java.JavaPlugin;

public class CEMain extends JavaPlugin{
	@Override
	public void onEnable() {
		super.onEnable();
		getCommand("ce").setExecutor(new CECommand(this));
	}
}
