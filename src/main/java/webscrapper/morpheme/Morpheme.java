package webscrapper.morpheme;

import java.util.List;

import lib.mesg.module.morpheme.mecab.MorphemeParser;

public class Morpheme {

	public void doParse(String str) {
		List<String> list = MorphemeParser.getInst().parseNounType(str);
		for(String s : list)
			System.out.println(s+"\n");
	}
}
