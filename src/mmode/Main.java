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

package mmode;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Main extends JavaPlugin implements Listener {

	public ProtocolManager protocolManager;
	private Config config;
	private Commands commands;
	private JoinListener jlistener;
	private PingResponseListener prlistener;
	
	@Override
	public void onEnable()
	{
		config = new Config();
		config.loadConfig();
		commands = new Commands(config);
		getCommand("mmode").setExecutor(commands);
		jlistener = new JoinListener(config);
		getServer().getPluginManager().registerEvents(jlistener, this);
	    protocolManager = ProtocolLibrary.getProtocolManager();
		prlistener = new PingResponseListener(this, config);
		prlistener.addPingResponsePacketListener();
	}
	
	@Override
	public void onDisable()
	{
		 commands = null;
		 HandlerList.unregisterAll((JavaPlugin)this);
		 jlistener = null;
		 protocolManager.removePacketListeners(this);
		 prlistener = null;
		 protocolManager = null;
		 config = null;
	}
	
	
	
}
