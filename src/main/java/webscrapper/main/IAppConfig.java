package webscrapper.main;

import lib.mesg.module.IDefaultConfig;
import lib.mesg.module.util.ConfigLoader;

public class IAppConfig extends IDefaultConfig {
private static final String CONFIG_FILE = "/configuration.cfg";
	
	public static String mysql_url = "localhost";
	public static String mysql_user = "user";
	public static String mysql_pw = "pw";
	public static String mysql_dbname = "botapp";
	
	public static int init_poolcount = 4;
	public static int max_poolcount = 24;
	
	static public void loadConfig() {
		ConfigLoader.getInst().loadConfig(CONFIG_FILE);
		
		IAppConfig.mysql_url = ConfigLoader.getInst().getString("mysql_url");
		IAppConfig.mysql_user = ConfigLoader.getInst().getString("mysql_user");
		IAppConfig.mysql_pw = ConfigLoader.getInst().getString("mysql_pw");
		IAppConfig.mysql_dbname = ConfigLoader.getInst().getString("mysql_dbname");
		
		IAppConfig.init_poolcount = ConfigLoader.getInst().getInt("init_poolcount", IAppConfig.init_poolcount);
		IAppConfig.max_poolcount = ConfigLoader.getInst().getInt("max_poolcount", IAppConfig.max_poolcount);
	}
}
