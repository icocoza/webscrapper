package webscrapper.scrapper;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FoodWebScrapper extends WebScrapper {

	@Override
	protected void RequestList(FileWriter fw, String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line;
		while ((line = in.readLine()) != null) {
			if(line.contains("<div class=\"board-footer\">")==true)
				break;
			if(line.contains("<a href=\"javascript:goDetail(")) {
				parseListLink(fw, line);
			}
		}
		in.close();
	}
	
	private void parseListLink(FileWriter fw, String line) throws IOException {
		//<a href="javascript:goDetail('230001000300100005', '305', '2010');">
		String[] sunit = line.split("'", -1);
		String code = sunit[1];
		String code2 = sunit[3];
		String year = sunit[5];
		String name = "";
		fw.write(String.format("%s\t%s\t%s\t%s\n", code, code2, year, name));
	}
	
	public void doRequestDetailUrl(String outFilePath, String[] codeList, String[] laborList, String[] yearList, String url1, String url2, String url3) throws Exception {
		FileWriter fw = new FileWriter(outFilePath);
		
		for(int i=0; i<codeList.length; i++)
			RequestDetail(fw, url1 + codeList[i] + url2 + laborList[i] + url3 + yearList[i]);
		fw.close();
	}
	
	private void RequestDetail(FileWriter fw, String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		
		String line;
		boolean bFirstDesc = true;
		while ((line = in.readLine()) != null) {
			if(line.contains("<div class=\"info-container\">")) {
				while( in.readLine().trim().length()<1 )	//remove blank line
					;
				if(parseDescriptioin(in, fw)==false)
					break;
			}
			else if(line.contains("<td class=\"num\" scope=\"row\">")) {
				String parse = parseNutrition(bFirstDesc, in);
				System.out.println(parse);
				fw.write(parse);
				bFirstDesc = false;
			}
			else if(line.contains("<div class=\"board-footer\">")) {
				fw.write("\n");
				return;
			}
		}
		in.close();
	}



}
