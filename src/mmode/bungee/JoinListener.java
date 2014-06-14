package mmode.bungee;

import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	private Config config;

	public JoinListener(Config config) {
		this.config = config;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPostLogin(ServerConnectEvent event) {
		if (config.maintenanceset.contains(event.getTarget().getName())) {
			event.setCancelled(true);
			event.getPlayer().disconnect(ColorParser.parseColor(config.kickMessage));
		}
	}

}
