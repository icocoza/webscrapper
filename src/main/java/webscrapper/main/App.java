package webscrapper.main;

import java.io.IOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import lib.mesg.bot.DbBotManager;
import lib.mesg.module.util.ResFileLoader;
import webscrapper.elssearch.ElasticSearchData;
import webscrapper.elssearch.ESJestMgr;
import webscrapper.elssearch.ESTransportMgr;
import webscrapper.scrapper.FoodWebScrapper;
import webscrapper.scrapper.GroceryWebScrapper;
import webscrapper.scrapper.ProceedFoodWebScrapper;

/**
 * Hello world!

 */
public class App 
{
	//static Logger logger=Logger.getLogger(App.class); 
    public static void main( String[] args )
    {
        System.out.println( "Hello Scrapper!" );
        //loadGrocery(true);
        //loadFood(true);
        //loadProceedFood(true);
        //loadFood(false);
        //loadProceedFood(false);
        
        //testElastic();
        testElasticSearchClient();
        //addTransportDataToElasticSearch() ;
    }
    
    private static void loadGrocery(boolean bList) {
        GroceryWebScrapper gws = new GroceryWebScrapper();
        try {
        	if(bList==true)
        		gws.doRequestListUrl("foodcompound.tsv", 1, 131, "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/advancedSearch.do?menu_no=2833&menu_grp=MENU_NEW03&order_column=2&order_asc=ASC&nutri_cd_selected=10100%2C10500%2C10300%2C10400%2C10700%2C20400%2C40100%2C40300%2C43700&code4=1&code1=&code2=&code3=&search_name=&labor_cd=&year=&nation=&nutri_cd_2=10100&nutri_c_1=&nutri_c_2=&page=");
        	else {
	        	List<String> codeList = new ArrayList<>();
	        	List<String> laborList = new ArrayList<>();
	        	List<String> yearList = new ArrayList<>();
	        	gws.parseFoodCompoundCsvFile("foodcompound.tsv", codeList, laborList, yearList);
	        	String[] codeStrList = codeList.toArray(new String[codeList.size()]); 
	        	String[] laborStrList = laborList.toArray(new String[laborList.size()]);
	        	String[] yearStrList = yearList.toArray(new String[yearList.size()]); 
	        	
	        	String url1 = "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/searchDetail.do?menu_no=2833&menu_grp=MENU_NEW03&food_cd=";
	        	String url2 = "&year2=";
	        	String url3 = "&page=1&order_column=2&order_asc=ASC&nutri_cd_selected=10100%2C10500%2C10300%2C10400%2C10700%2C20400%2C40100%2C40300%2C43700&pageType=advanced&labor_cd_source=&code4=1&code1=&code2=&code3=&search_name=&labor_cd=";
	        	String url4 = "&year=&nation=&nutri_cd_2=10100&nutri_c_1=&nutri_c_2=";
	        	gws.doRequestDetailUrl("fooddetail.tsv", codeStrList, laborStrList, yearStrList, url1, url2, url3, url4);
        	}
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static void loadFood(boolean bList) {
    	FoodWebScrapper fws = new FoodWebScrapper();
        try {
        	if(bList==true)
        		//http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/simpleSearch.do?menu_no=2805&menu_grp=MENU_NEW03&code4=2&code2=&search_name=&page=2
        		fws.doRequestListUrl("foodcompound2.tsv", 1, 910, "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/simpleSearch.do?menu_no=2805&menu_grp=MENU_NEW03&code4=2&code2=&search_name=&page=");
        	else {
	        	List<String> codeList = new ArrayList<>();
	        	List<String> laborList = new ArrayList<>();
	        	List<String> yearList = new ArrayList<>();
	        	fws.parseFoodCompoundCsvFile("foodcompound2.tsv", codeList, laborList, yearList);
	        	String[] codeStrList = codeList.toArray(new String[codeList.size()]); 
	        	String[] laborStrList = laborList.toArray(new String[laborList.size()]);
	        	String[] yearStrList = yearList.toArray(new String[yearList.size()]); 
	        	
	        	String url1 = "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/searchDetail.do?menu_no=2805&menu_grp=MENU_NEW03&code4=2&code2=&search_name=&food_cd=";
	        	String url2 = "&labor_cd=";
	        	String url3 = "&year2=";
	        	fws.doRequestDetailUrl("fooddetail2.tsv", codeStrList, laborStrList, yearStrList, url1, url2, url3);
        	}
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static void loadProceedFood(boolean bList) {
    	ProceedFoodWebScrapper pfws = new ProceedFoodWebScrapper();
        try {
        	if(bList==true)
        		pfws.doRequestListUrl("foodcompound3.tsv", 203, 628, "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/simpleSearch.do?menu_no=2805&menu_grp=MENU_NEW03&code4=3&code2=&search_name=&page=");
        	else {
	        	List<String> codeList = new ArrayList<>();
	        	List<String> laborList = new ArrayList<>();
	        	List<String> yearList = new ArrayList<>();
	        	pfws.parseFoodCompoundCsvFile("foodcompound3.tsv", codeList, laborList, yearList);
	        	String[] codeStrList = codeList.toArray(new String[codeList.size()]); 
	        	String[] laborStrList = laborList.toArray(new String[laborList.size()]);
	        	String[] yearStrList = yearList.toArray(new String[yearList.size()]); 
	        	//http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/searchDetail.do?menu_no=2805&menu_grp=MENU_NEW03&code4=3&code2=&search_name=&food_cd=300101003200300053&labor_cd=202&year2=2006&page=2
	        	String url1 = "http://www.foodsafetykorea.go.kr/portal/healthyfoodlife/foodnutrient/searchDetail.do?menu_no=2805&menu_grp=MENU_NEW03&code4=3&code2=&search_name=&food_cd=";
	        	String url2 = "&labor_cd=";
	        	String url3 = "&year2=";
	        	pfws.doRequestDetailUrl("fooddetail3.tsv", codeStrList, laborStrList, yearStrList, url1, url2, url3);
        	}
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private static void testElastic() {
 
    }
    
    private static void testElasticSearchClient() {
    	try {
			ESTransportMgr.getInst().init("foodingredient-cluster", "food-node-1", "localhost", 9300);
			/*ESTransportMgr.getInst().deleteIndex("food");
			ResFileLoader.getInst().loadConfig("/elastic_foodsettings.cfg");
			String settings = ResFileLoader.getInst().getAllText();
			ESTransportMgr.getInst().createIndex("food", settings);
			ResFileLoader.getInst().loadConfig("/elastic_foodmappings.cfg");
			String mappings = ResFileLoader.getInst().getAllText();
			ESTransportMgr.getInst().putMappings("food", "foodnut", mappings);*/
			SearchResponse res = ESTransportMgr.getInst().matchSearch("food", "foodnut", "title", "»õ¿ì±ø");
			System.out.print(res.getHits().getTotalHits()+"");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    private static void addTransportDataToElasticSearch() {
    	IAppConfig.loadConfig();
        DbBotManager.getInst().createDatabase(IAppConfig.mysql_url, IAppConfig.mysql_dbname, IAppConfig.mysql_user, IAppConfig.mysql_pw);
        DbBotManager.getInst().init(IAppConfig.mysql_url, IAppConfig.mysql_dbname, IAppConfig.mysql_user, IAppConfig.mysql_pw, 4, 32);
        
    	try {
			ESTransportMgr.getInst().init("foodingredient-cluster", "food-node-1", "localhost", 9300);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
    	ElasticSearchData esd = new ElasticSearchData();
    	try {
			esd.addFoodDataToElasticSearch("fooddetail1.txt", "food", "foodnut", "UTF-8");
			esd.addFoodDataToElasticSearch("fooddetail2.txt", "food", "foodnut", "EUC-KR");
			esd.addFoodDataToElasticSearch("fooddetail3.txt", "food", "foodnut", "EUC-KR");
			esd.addFile();
			esd.addDb();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
