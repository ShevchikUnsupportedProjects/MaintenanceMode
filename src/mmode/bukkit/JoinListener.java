/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */

package mmode.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class JoinListener implements Listener {

	private Config config;

	public JoinListener(Config config) {
		this.config = config;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onLogin(PlayerLoginEvent event) {
		if (event.getResult() != Result.ALLOWED) {
			return;
		}

		if (!config.mmodeEnabled || event.getPlayer().hasPermission("mmode.join")) {
			return;
		}

		event.disallow(Result.KICK_OTHER, ColorParser.parseColor(config.kickMessage));
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent event) {
		if (!config.mmodeEnabled || event.getPlayer().hasPermission("mmode.join")) {
			return;
		}

		event.getPlayer().kickPlayer(ColorParser.parseColor(config.kickMessage));
	}

}
