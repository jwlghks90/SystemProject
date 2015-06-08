package Parser;
/*
 *  politics
 *  {
 *  	all article : http://www.chosun.com/site/data/rss/politics.xml
 *  	assembly : http://www.chosun.com/site/data/rss/assembly.xml
 *  	north korea : http://www.chosun.com/site/data/rss/nk.xml
 *  	administration : http://www.chosun.com/site/data/rss/administration.xml
 *  	diplomacy : http://www.chosun.com/site/data/rss/diplomacy.xml
 *  	Cheongwadae : http://www.chosun.com/site/data/rss/cwd.xml
 *  }
 *  society
 *  {
 *  	all article : http://www.chosun.com/site/data/rss/national.xml
 *  	Court : http://www.chosun.com/site/data/rss/Court.xml
 *  	education : http://www.chosun.com/site/data/rss/education.xml
 *  	defense : http://www.chosun.com/site/data/rss/defense.xml
 *  	medical : http://www.chosun.com/site/data/rss/medical.xml
 *  	living : http://www.chosun.com/site/data/rss/livingp.xml
 *  	environment : http://www.chosun.com/site/data/rss/environment.xml
 *  	traffic : http://www.chosun.com/site/data/rss/traffic.xml
 *  	labor : http://www.chosun.com/site/data/rss/labor.xml
 *  }
 *  international
 *  {
 *  	all article : http://www.chosun.com/site/data/rss/international.xml
 *  	asia : http://www.chosun.com/site/data/rss/asia.xml
 *  	usa : http://www.chosun.com/site/data/rss/usa.xml
 *  	europe :  http://www.chosun.com/site/data/rss/europe.xml
 *  	meast : http://www.chosun.com/site/data/rss/meast.xml
 *  }
 *  culture
 *  {
 *  	all article : http://www.chosun.com/site/data/rss/culture.xml
 *  	health : http://www.chosun.com/site/data/rss/health.xml
 *  	books : http://www.chosun.com/site/data/rss/books.xml
 *  	media : http://www.chosun.com/site/data/rss/culture_media.xml
 *  	religion : http://www.chosun.com/site/data/rss/religion.xml
 *  	people : http://www.chosun.com/site/data/rss/c_people.xml
 *  	life : http://www.chosun.com/site/data/rss/life.xml
 *  }
 */
public class URLlist {
	String[] politics_list = {
			//"http://www.chosun.com/site/data/rss/politics.xml", // 정치
			"http://www.chosun.com/site/data/rss/assembly.xml",
			"http://www.chosun.com/site/data/rss/nk.xml",
			"http://www.chosun.com/site/data/rss/administration.xml",
			"http://www.chosun.com/site/data/rss/diplomacy.xml",
			"http://www.chosun.com/site/data/rss/cwd.xml" };
	
	String[] sport_list = {
			"http://www.chosun.com/site/data/rss/baseball.xml", // 스포츠
			"http://www.chosun.com/site/data/rss/soccer.xml",
			"http://www.chosun.com/site/data/rss/bas.xml",
			"http://www.chosun.com/site/data/rss/golf.xml" };
	
	String[] society_list = {
			//"http://www.chosun.com/site/data/rss/national.xml", // 사회
			"http://www.chosun.com/site/data/rss/Court.xml",
			"http://www.chosun.com/site/data/rss/education.xml",
			"http://www.chosun.com/site/data/rss/defense.xml",
			"http://www.chosun.com/site/data/rss/medical.xml",
			"http://www.chosun.com/site/data/rss/livingp.xml",
			"http://www.chosun.com/site/data/rss/environment.xml",
			"http://www.chosun.com/site/data/rss/traffic.xml",
			"http://www.chosun.com/site/data/rss/labor.xml" };
	String[] international_list = {
			//"http://www.chosun.com/site/data/rss/international.xml", // 국제
			"http://www.chosun.com/site/data/rss/asia.xml",
			"http://www.chosun.com/site/data/rss/usa.xml",
			"http://www.chosun.com/site/data/rss/europe.xml",
			"http://www.chosun.com/site/data/rss/meast.xml" };
	String[] culture = { 
			//"http://www.chosun.com/site/data/rss/culture.xml", // 문화
			"http://www.chosun.com/site/data/rss/health.xml",
			"http://www.chosun.com/site/data/rss/books.xml",
			"http://www.chosun.com/site/data/rss/culture_media.xml",
			"http://www.chosun.com/site/data/rss/religion.xml",
			"http://www.chosun.com/site/data/rss/c_people.xml",
			"http://www.chosun.com/site/data/rss/life.xml" };
	
	String[] newsToday = {
			"http://myhome.chosun.com/rss/www_section_rss.xml"
	};

	public String[] getPolitics_list() {
		return politics_list;
	}

	public void setPolitics_list(String[] politics_list) {
		this.politics_list = politics_list;
	}

	public String[] getSociety_list() {
		return society_list;
	}

	public void setSociety_list(String[] society_list) {
		this.society_list = society_list;
	}

	public String[] getInternational_list() {
		return international_list;
	}

	public void setInternational_list(String[] international_list) {
		this.international_list = international_list;
	}

	public String[] getCulture() {
		return culture;
	}

	public void setCulture(String[] culture) {
		this.culture = culture;
	}
}
