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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.imageio.ImageIO;

import net.md_5.bungee.api.Favicon;

public class Config {

	private Main main;

	public Config(Main main) {
		this.main = main;
	}

	public HashSet<String> maintenanceaddressset = new HashSet<String>();
	public String mmodeMessage = "&6Maintenance";
	public String mmodeMOTD = "{motd} &4At maintenance";
	public String mmodeIconPath = "server-icon.png";
	public boolean kickOnEnable = true;
	public String kickMessage = "Server is at maintenance. Please come back later.";

	public Favicon favicon = null;

	public void loadConfig() {
		File configfile = new File(main.getDataFolder(), "config.yml");

		YamlConfiguration config = new YamlConfiguration();
		try {
			config.load(configfile);
		} catch (FileNotFoundException e) {
		}

		maintenanceaddressset = new HashSet<String>(config.getStringList("MaintenanceEnabled"));
		mmodeMessage = config.getString("PingMessage", mmodeMessage);
		mmodeMOTD = config.getString("MOTD", mmodeMOTD);
		mmodeIconPath = config.getString("Icon", mmodeIconPath);
		kickMessage = config.getString("KickMessage", kickMessage);
		kickOnEnable = config.getBoolean("KickNonAllowedOnMModeEnable", kickOnEnable);

		try {
			File iconfile = new File(mmodeIconPath);
			if (iconfile.exists()) {
				favicon = Favicon.create(ImageIO.read(iconfile));
			}
		} catch (Exception e) {
		}
	}

	public void saveConfig() {
		File configfile = new File(main.getDataFolder(), "config.yml");

		YamlConfiguration config = new YamlConfiguration();

		config.set("MaintenanceEnabled", new ArrayList<String>(maintenanceaddressset));
		config.set("PingMessage", mmodeMessage);
		config.set("MOTD", mmodeMOTD);
		config.set("Icon", mmodeIconPath);
		config.set("KickMessage", kickMessage);
		config.set("KickNonAllowedOnMModeEnable", kickOnEnable);

		try {
			config.save(configfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
