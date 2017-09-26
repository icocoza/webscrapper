package webscrapper.scrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GroceryWebScrapper extends WebScrapper{
	
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
			if(line.contains("<td scope=\"row\">")) {
				in.readLine();
				in.readLine();
				line = in.readLine();
				parseListLink(fw, line);
			}
		}
		in.close();
	}
	
	private void parseListLink(FileWriter fw, String line) throws IOException {
		int start = line.indexOf("'") +1;
		int end = line.indexOf("'", start);
		String code = line.substring(start, end);
		System.out.println(code);
		
		start = line.indexOf("'", ++end) + 1;
		end = line.indexOf("'", start);
		String code2 = line.substring(start, end);
		System.out.println(code2);
		
		start = line.indexOf("'", ++end) + 1;
		end = line.indexOf("'", start);
		String year = line.substring(start, end);
		System.out.println(year);
		
		start = line.indexOf(">", ++end) + 1;
		end = line.indexOf("<", start);
		String name = line.substring(start, end);
		System.out.println(name);
		
		fw.write(String.format("%s\t%s\t%s\t%s\n", code, code2, year, name));
	}
	
	public void doRequestDetailUrl(String outFilePath, String[] codeList, String[] laborList, String[] yearList, String url1, String url2, String url3, String url4) throws Exception {
		FileWriter fw = new FileWriter(outFilePath);
		
		for(int i=0; i<codeList.length; i++)
			RequestDetail(fw, url1 + codeList[i] + url2 + yearList[i] + url3 + laborList[i] + url4);
		fw.close();
	}
	
	private void RequestDetail(FileWriter fw, String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String line;
		boolean bFirstDesc = true;
		while ((line = in.readLine()) != null) {
			if(line.contains("<div class=\"info-container\">")) {
				while( in.readLine().trim().length()<1 )	//remove blank line
					;
				parseDescriptioin(in, fw);
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
