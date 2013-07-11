package mmode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	public boolean mmodeEnabled = false;
	public String mmodeMessage = "Maintenance";
	public String mmodeMOTD = "{motd} <-- At maintenance";
	public boolean kickOnEnable = true;
	public String mmodeKickMessage = "Server is at maintenance. Please come back later.";
	public HashSet<String> mmodeAdminsList = new HashSet<String>();
	
	public void loadConfig()
	{
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/MMode/config.yml"));
		
		mmodeMessage = config.getString("PingMessage", mmodeMessage);
		mmodeMOTD = config.getString("MOTD", mmodeMOTD);
		mmodeKickMessage = config.getString("KickMessage", mmodeKickMessage);
		mmodeAdminsList = new HashSet<String>(config.getStringList("AllowedList"));
		kickOnEnable = config.getBoolean("KickNonAllowedOnMModeEnable", kickOnEnable);
		
		saveConfig();
	}
	
	private void saveConfig()
	{
		FileConfiguration config = new YamlConfiguration();
		
		config.set("PingMessage", mmodeMessage);
		config.set("MOTD", mmodeMOTD);
		config.set("KickMessage", mmodeKickMessage);
		config.set("AllowedList", new ArrayList<String>(mmodeAdminsList));
		config.set("KickNonAllowedOnMModeEnable", kickOnEnable);
		
		try {
			config.save(new File("plugins/MMode/config.yml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
