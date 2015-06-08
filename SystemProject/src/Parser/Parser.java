package Parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	private NounExtractor ne;
	Document doc;
	
	public Parser(){
		ne = new NounExtractor();
	}
	
	public ArrayList<String> parsing(String link){		
		ArrayList<String> temp = new ArrayList<String>();
		try {
			doc = Jsoup.connect(link).get();
			//Elements title = doc.select("#title_text");
			//System.out.println(title.text() + "\n\n"); // 제목
			Elements rowElements = doc.select(".par");
			for(Element row : rowElements) {
				//System.out.println(row.text().trim().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "") + "\n\n\n");
				String document = row.text().trim().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				//System.out.println(document);
				ne.getNounList(document, temp);
				System.out.println("document 분석!!");
			}	
		} catch (IOException e) {
			System.out.println("==========================================================================================================================Error!!!");
			return temp;
			//e.printStackTrace();
		}
		return temp;
	}
}
