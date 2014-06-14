package mmode.bungee;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	private Config config;

	@Override
	public void onEnable() {
		config = new Config();
		config.loadConfig();
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new Commands(config));
		ProxyServer.getInstance().getPluginManager().registerListener(this, new JoinListener(config));
	}

	@Override
	public void onDisable() {
		config.saveConfig();
	}

}
