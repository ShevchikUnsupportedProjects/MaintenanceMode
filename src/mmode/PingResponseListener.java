package mmode;

import org.bukkit.Bukkit;

import com.comphenix.protocol.events.ConnectionSide;
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
	    		new PacketAdapter(main, ConnectionSide.SERVER_SIDE, ListenerPriority.HIGHEST, GamePhase.LOGIN, new Integer[] { Integer.valueOf(255) })
	    		{
	    			public void onPacketSending(PacketEvent event) {
	    				try {
	    					if (!config.mmodeEnabled) {return;}
	    					
	    					StructureModifier<String> packetStr = event.getPacket().getSpecificModifier(String.class);
	    					String p = (String)packetStr.read(0);
	    					String prep = p.substring(0, 3);
	    					String motd = config.mmodeMOTD.replace("{motd}", p.split("\u0000")[3]);
	    					packetStr.write(0, 
	    							prep  //first 3 bytes
	    							+ -1  //protocol vesion
	    							+ "\u0000" 
	    							+ ColorParser.parseColor(config.mmodeMessage) //message near ping
	    							+ "\u0000" 
	    							+ ColorParser.parseColor(motd) //motd
	    							+ "\u0000" 
	    							+ Bukkit.getOnlinePlayers().length  //online players count
	    							+ "\u0000" 
	    							+ Bukkit.getMaxPlayers() // max players
	    							);
	    				}
	    				catch (Exception e) {}
	    			}
	    		}
	    );
	}
}
