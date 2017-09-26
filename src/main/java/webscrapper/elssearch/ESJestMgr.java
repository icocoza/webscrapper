package webscrapper.elssearch;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class ESJestMgr {
	private static ESJestMgr s_pThis;
	
	public static ESJestMgr getInst() {
		if(s_pThis==null)
			s_pThis = new ESJestMgr();
		return s_pThis;
	}
	
	public static void freeInst() {
		s_pThis = null;
	}
	
	/*JestClientFactory jestFactory = null;

	public void init(String url) {
		jestFactory = new JestClientFactory();
		if(url==null || url.length()<1)
			jestFactory.setHttpClientConfig(new HttpClientConfig.Builder("http://127.0.0.1:9200").multiThreaded(true).build());
		else
			jestFactory.setHttpClientConfig(new HttpClientConfig.Builder(url).multiThreaded(true).build());
	}
	

	public void createIndex(String index) throws IOException {
		JestClient client = jestFactory.getObject();
		client.execute(new CreateIndex.Builder(index).build());
	}
	
	public void createIndex(String index, int shardCnt, int replicaCnt) throws IOException {
		JestClient client = jestFactory.getObject();
		Settings.Builder settingsBuilder = Settings.builder();
		settingsBuilder.put("number_of_shards",shardCnt);
		settingsBuilder.put("number_of_replicas",replicaCnt);

		client.execute(new CreateIndex.Builder(index).settings(settingsBuilder.build().getAsMap()).build());
	}
	
	public boolean deleteIndex(String index) throws IOException{
		JestClient client = jestFactory.getObject();
		DeleteIndex deleteIndex = new DeleteIndex.Builder("newindex2").build();
        return client.execute(deleteIndex).isSucceeded();
	}
	
	public boolean upsert(String json, String index, String type, String id) throws IOException {
		JestClient client = jestFactory.getObject();
		Index post = new Index.Builder(json).index(index).type(type).id(id).build();
		DocumentResult result = client.execute(post);
		return result.isSucceeded();
	}
	
	public String get(String index, String type, String id) throws IOException {
		JestClient client = jestFactory.getObject();
		Get get = new Get.Builder(index, id).type(type).build();
		JestResult result = client.execute(get);
		return result.getJsonString();
	}
	
	public String matchSearch(String index, String type, String field, String word) throws InterruptedException, ExecutionException, IOException {
		/*JestClient client = jestFactory.getObject();
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(field, word));
		Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(index).addType(type).build();
		SearchResult result = client.execute(search);
		return result.getJsonString();
		return "";
	}
	*/
}
