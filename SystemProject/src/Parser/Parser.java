package Parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	public void parsing(String link)
	{
		try {
			Document doc = Jsoup.connect(link).get();
			Elements title = doc.select("#title_text");
			//System.out.println(title.text() + "\n\n"); // 제목
			
			Elements rowElements = doc.select(".par");
			for (Element row : rowElements) {				
				System.out.println(row.text().trim().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
