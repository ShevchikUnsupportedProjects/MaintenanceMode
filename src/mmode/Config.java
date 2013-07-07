package mmode;

import java.util.HashSet;

public class Config {

	public boolean mmodeEnabled = false;
	public String mmodeMessage = "Maintenance";
	public String mmodeMOTD = "{motd} <-- At maintenance";
	public String mmodeKickMessage = "Server is at maintenance. Please come back later.";
	public HashSet<String> mmodeAdminsList = new HashSet<String>();
	
	public void loadConfig()
	{
		
		saveConfig();
	}
	
	private void saveConfig()
	{
		
	}
	
}
