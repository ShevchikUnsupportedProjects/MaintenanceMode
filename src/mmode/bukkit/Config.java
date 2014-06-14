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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public boolean mmodeEnabled = false;
	public String mmodeMessage = "&6Maintenance";
	public String mmodeMOTD = "{motd} &4At maintenance";
	public String mmodeIconPath = "server-icon.png";
	public boolean allowedlistEnabled = true;
	public boolean kickOnEnable = true;
	public String kickMessage = "Server is at maintenance. Please come back later.";
	public HashSet<String> mmodeAllowedList = new HashSet<String>();

	public void loadConfig() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/MMode/config.yml"));

		mmodeEnabled = config.getBoolean("MaintenanceEnabled", mmodeEnabled);
		mmodeMessage = config.getString("PingMessage", mmodeMessage);
		mmodeMOTD = config.getString("MOTD", mmodeMOTD);
		mmodeIconPath = config.getString("Icon", mmodeIconPath);
		allowedlistEnabled = config.getBoolean("enableallowedlist", allowedlistEnabled);
		kickMessage = config.getString("KickMessage", kickMessage);
		mmodeAllowedList = new HashSet<String>(config.getStringList("AllowedList"));
		kickOnEnable = config.getBoolean("KickNonAllowedOnMModeEnable", kickOnEnable);

		saveConfig();
	}

	public void saveConfig() {
		FileConfiguration config = new YamlConfiguration();

		config.set("MaintenanceEnabled", mmodeEnabled);
		config.set("PingMessage", mmodeMessage);
		config.set("MOTD", mmodeMOTD);
		config.set("Icon", mmodeIconPath);
		config.set("enableallowedlist", allowedlistEnabled);
		config.set("KickMessage", kickMessage);
		config.set("AllowedList", new ArrayList<String>(mmodeAllowedList));
		config.set("KickNonAllowedOnMModeEnable", kickOnEnable);

		try {
			config.save(new File("plugins/MMode/config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
