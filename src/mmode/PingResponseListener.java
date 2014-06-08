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

import java.io.File;
import java.io.FileInputStream;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.comphenix.protocol.wrappers.WrappedServerPing.CompressedImage;

public class PingResponseListener {

	private Main main;
	private Config config;

	public PingResponseListener(Main main, Config config) {
		this.main = main;
		this.config = config;
	}

	public void addPingResponsePacketListener() {
		ProtocolLibrary.getProtocolManager().addPacketListener(
			new PacketAdapter(
					PacketAdapter
					.params(main, PacketType.Status.Server.OUT_SERVER_INFO)
					.serverSide()
					.gamePhase(GamePhase.BOTH)
					.listenerPriority(ListenerPriority.HIGHEST)
					.optionAsync()
			) {
				public void onPacketSending(PacketEvent event) {
					try {
						if (!config.mmodeEnabled) {
							return;
						}
						// read from packet
						WrappedServerPing ping = event.getPacket().getServerPings().getValues().get(0);
						// set ping message
						String pingMessage = ColorParser.parseColor(config.mmodeMessage);
						ping.setVersionProtocol(-1);
						ping.setVersionName(pingMessage);
						// set motd
						String prevmotd = ping.getMotD().getJson();
						String motd = ColorParser.parseColor(config.mmodeMOTD.replace("{motd}", prevmotd));
						ping.setMotD(motd);
						// set icon
						File iconfile = new File(config.mmodeIconPath);
						if (iconfile.exists()) {
							CompressedImage favicon = CompressedImage.fromPng(new FileInputStream(iconfile));
							ping.setFavicon(favicon);
						}
						// write to packet
						event.getPacket().getServerPings().getValues().set(0, ping);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);
	}
}
