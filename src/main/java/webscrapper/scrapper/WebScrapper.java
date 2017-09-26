package webscrapper.scrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class WebScrapper {

	public void doRequestListUrl(String outFilePath, int start, int end, String urlFormat) throws Exception {
		FileWriter fw = new FileWriter(outFilePath);
		for(int i=start; i<=end; i++) {
			RequestList(fw, urlFormat+i);
		}
		fw.close();
	}
	
	public void parseFoodCompoundCsvFile(String fname, List<String> codeList, List<String> laborList, List<String> yearList) throws IOException {
		FileReader fr = new FileReader(fname);
		BufferedReader in = new BufferedReader(fr);
		
		String line;
		while ((line = in.readLine()) != null) {
			if(line.trim().length()<1)
				return;
			String[] tabs = line.split("\t");
			codeList.add(tabs[0]);
			laborList.add(tabs[1]);
			yearList.add(tabs[2]);
		}
		in.close();
	}
	
	protected boolean parseDescriptioin(BufferedReader in, FileWriter fw) throws IOException {
		String code = parseDescItem(in);
		if(code==null ||code.length()<1)
			return false;
		String nameKor = parseDescItem(in).replaceAll(",","/");
		String nameEng = parseDescItem(in).replaceAll(",","/");
		String nameKor2 = parseDescItem(in).replaceAll(",","/");
		String type = parseDescItem(in).replaceAll(",","/");
		String group = parseDescItem(in).replaceAll(",","/");
		
		String description = String.format("%s,%s,%s,%s,%s,%s", code, nameKor, nameEng, nameKor2, type, group);
		System.out.println(description);
		fw.write(description);
		return true;
	}
	
	protected String parseDescItem(BufferedReader in) throws IOException {
		String line;
		while( (line=in.readLine()) != null ) {
			if(line.contains("<dd>")) {
				int start = line.indexOf(">") +1;
				int end = line.indexOf("<", start);
				if(end<0)
					end = line.length()-1;
				return line.substring(start, end);
			}
		}
		return "";
	}
	protected String parseBracket(String line) {
		int start = line.indexOf(">") +1;
		int end = line.indexOf("<", start);
		return line.substring(start, end);
	}
	
	
	protected String parseNutrition(boolean bFirst, BufferedReader in) throws IOException {
		String code = parseBracket( in.readLine() );
		String abbr = parseBracket( in.readLine() );
		String name = parseBracket( in.readLine() );
		in.readLine();
		in.readLine();
		in.readLine();
		String amount = in.readLine().trim();
		in.readLine();
		in.readLine();
		String unit = parseBracket( in.readLine() );
		String lab = parseBracket( in.readLine() );
		String year = parseBracket( in.readLine() );
		if(bFirst) 
			return String.format("\t%s\t%s,%s,%s,%s,%s,%s", lab, code, abbr, name, amount, unit, year);
		return String.format("\t%s,%s,%s,%s,%s,%s", code, abbr, name, amount, unit, year);
	}
	
	protected abstract void RequestList(FileWriter fw, String url) throws Exception;
}
