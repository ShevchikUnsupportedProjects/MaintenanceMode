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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.utility.MinecraftReflection;

public class PingResponseListener {

	private Main main;
	private Config config;
	
	public PingResponseListener(Main main, Config config)
	{
		this.main = main;
		this.config = config;
	}
	
	public void addPingResponsePacketListener()
	{

		try {
			main.protocolManager.addPacketListener(
					new PacketAdapter
					(
							PacketAdapter
							.params(main, PacketType.Status.Server.OUT_SERVER_INFO)
							.serverSide()
							.gamePhase(GamePhase.BOTH)
							.listenerPriority(ListenerPriority.HIGHEST)
					)
					{
						//prepare some variables
						Class<?> serverPing = MinecraftReflection.getMinecraftClass("ServerPing");
						Class<?> serverPingServerData = MinecraftReflection.getMinecraftClass("ServerPingServerData");
						Constructor<?> serverPingServerDataConstructor = serverPingServerData.getConstructor(String.class,int.class);
						{
							serverPingServerDataConstructor.setAccessible(true);
						}
						Class<?> iChatBaseComponent = MinecraftReflection.getMinecraftClass("IChatBaseComponent");
						Class<?> chatComponentText = MinecraftReflection.getMinecraftClass("ChatComponentText");
						Constructor<?> chatComponentTextConstructor = chatComponentText.getConstructor(String.class);
						{
							chatComponentTextConstructor.setAccessible(true);
						}

						
						
						public void onPacketSending(PacketEvent event) 
						{
							try {
								if (!config.mmodeEnabled) {return;}
								//construct packet data reader
								@SuppressWarnings("unchecked")
								StructureModifier<Object> packetStr = (StructureModifier<Object>) event.getPacket().getSpecificModifier(serverPing);
								//read data from packet
								Object serverping = packetStr.read(0);
								//set ping message
								String pingMessage = ColorParser.parseColor(config.mmodeMessage);
								Object serverPingServerDataArgs = serverPingServerDataConstructor.newInstance(pingMessage,-1);
								Method setServerInfoMethod = serverping.getClass().getDeclaredMethod("setServerInfo", serverPingServerData);
								setServerInfoMethod.setAccessible(true);
								setServerInfoMethod.invoke(serverping, serverPingServerDataArgs);
								//set motd
								Method aMethod = serverping.getClass().getDeclaredMethod("a");
								aMethod.setAccessible(true);
								Object aReturn = aMethod.invoke(serverping);
								Method eMethod = aReturn.getClass().getDeclaredMethod("e");
								eMethod.setAccessible(true);
								String prevmotd = (String) eMethod.invoke(aReturn);
								String motd = ColorParser.parseColor(config.mmodeMOTD.replace("{motd}", prevmotd));
								Object chatComponentTextArgs = chatComponentTextConstructor.newInstance(ColorParser.parseColor(motd));
								Method setMOTDMethod = serverping.getClass().getDeclaredMethod("setMOTD", iChatBaseComponent);
								setMOTDMethod.setAccessible(true);
								setMOTDMethod.invoke(serverping, chatComponentTextArgs);
								//write data to packet
								packetStr.write(0, serverping);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
