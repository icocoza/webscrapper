package webscrapper.bot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum EBotDataType {
	eNone("none"), eSearchResult("search"), eProperty("property");
	
	public final String value;
	
	private EBotDataType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static final Map<String, EBotDataType> StrToAptCmdMap;
	
	static {
		StrToAptCmdMap = new ConcurrentHashMap<>();
		for(EBotDataType cmd : EBotDataType.values())
			StrToAptCmdMap.put(cmd.getValue(), cmd);
	}
	
	static public EBotDataType getType(String cmd) {
		EBotDataType ecmd = StrToAptCmdMap.get(cmd);
		if(ecmd != null)
			return ecmd;
		return eNone;
	}
}
