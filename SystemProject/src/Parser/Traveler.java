package Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class Traveler {
	public void travel() {
		URLlist list = new URLlist();
		String[] templist = list.getCulture();

		try {
			for (int i = 0; i < templist.length; i++) {
				String link = templist[i];
				try {
					Document doc = Jsoup.connect(link).get();
					Elements rowElements = doc.select("item");
					for (Element row : rowElements) {
						String itemtext = row.outerHtml();
						String[] firstlist = itemtext.split("<description>");
						String temp = firstlist[0];
						String[] secondlist = temp.split("<link>");
						String linktext = secondlist[1];
						//System.out.println(linktext.trim()); // �ּҸ� ����ִ�
						Parser parser= new Parser();
						parser.parsing(linktext.trim());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
