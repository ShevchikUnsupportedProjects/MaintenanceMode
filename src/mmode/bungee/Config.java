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

import java.util.HashSet;

import net.md_5.bungee.api.Favicon;

public class Config {

	public HashSet<String> maintenanceaddressset = new HashSet<String>();

	public String mmodeMessage = "&6Maintenance";
	public String mmodeMOTD = "{motd} &4At maintenance";
	public String mmodeIconPath = "server-icon.png";
	public boolean kickOnEnable = true;
	public String kickMessage = "Server is at maintenance. Please come back later.";

	public Favicon favicon = null;

	public void loadConfig() {
	}

	public void saveConfig() {
	}

}
