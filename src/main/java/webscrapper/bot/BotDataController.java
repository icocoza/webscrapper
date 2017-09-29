package webscrapper.bot;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

public class BotDataController {
	List<BotData> dataList = new ArrayList<>();
	
	public void add(String keyword, SearchResponse sr) {
		dataList.add(new BotData(keyword, sr));
	}
	
	public void addProperty(String property) {
		dataList.add(new BotData(property));
	}
	
	public List<BotData> getList() {
		return dataList;
	}
	
	public boolean hasKeyword(String findword) {
		for(BotData data : dataList)
			if(data.keyword.contains(findword))
				return true;
		return false;
	}
	
	public class BotData {
		public String keyword="";
		public SearchResponse sr;
		public String property;
		public EBotDataType type;
		
		public BotData(String keyword, SearchResponse sr) {
			this.keyword = keyword;
			this.sr = sr;
			type = EBotDataType.eSearchResult;
		}
		public BotData(String property) {
			this.property = property;
			type = EBotDataType.eProperty;
		}

	}
}
