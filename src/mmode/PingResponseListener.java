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

import net.minecraft.server.v1_7_R1.ChatComponentText;
import net.minecraft.server.v1_7_R1.ChatModifier;
import net.minecraft.server.v1_7_R1.ChatModifierSerializer;
import net.minecraft.server.v1_7_R1.ChatSerializer;
import net.minecraft.server.v1_7_R1.ChatTypeAdapterFactory;
import net.minecraft.server.v1_7_R1.IChatBaseComponent;
import net.minecraft.server.v1_7_R1.ServerPing;
import net.minecraft.server.v1_7_R1.ServerPingPlayerSample;
import net.minecraft.server.v1_7_R1.ServerPingPlayerSampleSerializer;
import net.minecraft.server.v1_7_R1.ServerPingSerializer;
import net.minecraft.server.v1_7_R1.ServerPingServerData;
import net.minecraft.server.v1_7_R1.ServerPingServerDataSerializer;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.reflect.StructureModifier;

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
	    			public void onPacketSending(PacketEvent event) 
	    			{
	    					if (!config.mmodeEnabled) {return;}
				
	    					StructureModifier<String> packetStr = event.getPacket().getSpecificModifier(String.class);
	    					String jsonencoded = (String)packetStr.read(0);
	    					
	    					Gson gson = new GsonBuilder()
	    					.registerTypeAdapter(ServerPingServerData.class, new ServerPingServerDataSerializer())
	    					.registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSampleSerializer())
	    					.registerTypeAdapter(ServerPing.class, new ServerPingSerializer())
	    					.registerTypeHierarchyAdapter(IChatBaseComponent.class, new ChatSerializer())
	    					.registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifierSerializer())
	    					.registerTypeAdapterFactory(new ChatTypeAdapterFactory())
	    					.create();
	    					ServerPing serverping = gson.fromJson(jsonencoded, ServerPing.class);

	    					serverping.setMOTD(new ChatComponentText(ColorParser.parseColor(config.mmodeMOTD)));
	    					serverping.setServerInfo(new ServerPingServerData(ColorParser.parseColor(config.mmodeMOTD),-1));
	    					
	    					jsonencoded = gson.toJson(serverping);
	    					
	    					packetStr.write(0, jsonencoded);
	    					
	    			}
	    		}
	    );
	}
}
