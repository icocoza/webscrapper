package webscrapper.elssearch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.index.IndexResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import lib.mesg.bot.DbBotManager;
import lib.mesg.bot.food.EFoodCmdType;

public class ElasticSearchData {
	private Map<String, EFoodCmdType> wordCollector = new HashMap<>();
	
	public void addFoodDataToElasticSearch(String dataPath, String index, String type, String encodeType) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataPath),encodeType));

		BufferedReader in = new BufferedReader(reader);
		
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line.replaceAll("\t", "|"));
			String[] units = line.split("\t");
			String id = units[0].substring(0, units[0].indexOf(','));
			String title = units[0].substring(units[0].indexOf(',')+1);
			addData(index, type, id, title, units);
			addMapTitle(title);
			
		}
		in.close();
		//addFile();
		//addDb();
	}
	
	private void addData(String index, String type, String id, String title, String[] items) {
		JSONArray jarr = new JSONArray(); 
		for(int i=2; i<items.length; i++) {
			JSONObject jitem = new JSONObject();
			String[] units = items[i].split(",");
			if(units.length<5)
				return;
			jitem.put("name", units[2]);
			jitem.put("amount", parseFloat(units[3]));
			jitem.put("unit", units[4]);
			jarr.add(jitem);
			
			if(wordCollector.containsKey(units[2])==false)
				wordCollector.put(units[2], EFoodCmdType.eNutrient);
		}
		JSONObject jobj = new JSONObject();
		jobj.put("title", title);
		jobj.put("nutrient", jarr);
		IndexResponse ir = ESTransportMgr.getInst().insert(jobj.toJSONString(), index, type, id);
		System.out.println(ir.getId());
	}
	
	private void addMapTitle(String title) {
		String[] commas = title.split(",");
		for(String comma : commas) {
			String[] units = comma.split("/");
			for(String unit : units) {
				if(unit.length()>0 && wordCollector.containsKey(unit.trim())==false)
					wordCollector.put(unit.trim(), EFoodCmdType.eTitle);
			}
		}
	}
	
	public void addFile() {
		try {
			FileWriter fw = new FileWriter(EFoodCmdType.Domain.getValue());
			Set<String> keys = wordCollector.keySet();
			for(String key : keys) {
				fw.write(String.format("%s\t%s\n", key, wordCollector.get(key).getValue()));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addDb() {
		Set<String> keys = wordCollector.keySet();
		for(String key : keys) {
			DbBotManager.getInst().addKeyword(key, wordCollector.get(key), EFoodCmdType.Domain.getValue(), 1);
		}
	}
	
	private float parseFloat(String sf) {
		if(sf==null || sf.length()<1)
			return 0f;
		try{
			return Float.parseFloat(sf);
		}catch(Exception e) {
			return 0f;
		}
	}
}
