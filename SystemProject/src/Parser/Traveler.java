package Parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Traveler {
	private Parser parser;
	private URLlist list;

	public Traveler() {
		parser = new Parser();
		list = new URLlist();
	}

	public ArrayList<String> travel(String topic) {
		ArrayList<String> keylist = null;
		String tempTopic = null;
		try {
			switch (topic) {
			case "국회":
				System.out.println("국회");
				tempTopic = list.getAssembly();
				break;
			case "북한":
				tempTopic = list.getNk();
				break;
			case "행정":
				tempTopic = list.getAdministration();
				break;
			case "외교":
				tempTopic = list.getDiplomacy();
				break;
			case "청와대":
				tempTopic = list.getCwd();
				break;
			case "야구":
				tempTopic = list.getBaseball();
				break;
			case "축구":
				tempTopic = list.getSoccer();
				break;
			case "농구":
				tempTopic = list.getBas();
				break;
			case "골프":
				tempTopic = list.getGolf();
				break;
			case "법원":
				tempTopic = list.getCourt();
				break;
			case "교육":
				tempTopic = list.getEducation();
				break;
			case "국방":
				tempTopic = list.getDefence();
				break;
			case "사건":
				tempTopic = list.getLaw();
				break;
			case "의료":
				tempTopic = list.getMedical();
				break;
			case "리빙":
				tempTopic = list.getLife();
				break;
			case "환경":
				tempTopic = list.getEnvironment();
				break;
			case "교통":
				tempTopic = list.getTraffic();
				break;
			case "노동":
				tempTopic = list.getLabor();
				break;
			case "아시아":
				tempTopic = list.getAsia();
				break;
			case "중남미":
				tempTopic = list.getUsa();
				break;
			case "유럽":
				tempTopic = list.getEurope();
				break;
			case "대양주":
				tempTopic = list.getDyj();
				break;
			case "중동아프리카":
				tempTopic = list.getMeast();
				break;
			case "국제기구":
				tempTopic = list.getW18();
				break;
			case "해외이슈":
				tempTopic = list.getW_issue();
				break;
			case "건강":
				tempTopic = list.getHealth();
				break;
			case "북스":
				tempTopic = list.getBooks();
				break;
			case "미디어":
				tempTopic = list.getCulture_media();
				break;
			case "종교":
				tempTopic = list.getReligion();
				break;
			case "문화인물":
				tempTopic = list.getC_people();
				break;
			case "생활":
				tempTopic = list.getLife();
				break;
			case "공연":
				tempTopic = list.getF_art();
				break;
			case "디자인":
				tempTopic = list.getDesign();
				break;
			case "오늘의뉴스":
				tempTopic = list.getNewsToday_list();
				break;
			case "연예":
				tempTopic = list.getEntertainment_list();
				break;
			default:
				System.out.println("없는 주제입니다.");
				break;
			}

			for (int i = 0; i < tempTopic.length(); i++) {
				String link = tempTopic;
				try {
					Document doc = Jsoup.connect(link).get();
					Elements rowElements = doc.select("item");
					for (Element row : rowElements) {
						String itemtext = row.outerHtml();
						String[] firstlist = itemtext.split("<description>");
						String temp = firstlist[0];
						String[] secondlist = temp.split("<link>");
						String linktext = secondlist[1];
						keylist = parser.parsing(linktext.trim());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keylist;
	}
}
