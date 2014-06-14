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
