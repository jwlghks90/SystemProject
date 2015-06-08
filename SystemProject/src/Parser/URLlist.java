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
	private String assembly = "http://www.chosun.com/site/data/rss/assembly.xml";
	private String nk = "http://www.chosun.com/site/data/rss/nk.xml";
	private String administration = "http://www.chosun.com/site/data/rss/administration.xml";
	private String diplomacy = "http://www.chosun.com/site/data/rss/diplomacy.xml";
	private String cwd = "http://www.chosun.com/site/data/rss/cwd.xml";
	private String baseball = "http://www.chosun.com/site/data/rss/baseball.xml";
	private String soccer = "http://www.chosun.com/site/data/rss/soccer.xml";
	private String bas = "http://www.chosun.com/site/data/rss/bas.xml";
	private String golf = "http://www.chosun.com/site/data/rss/golf.xml";
	private String court = "http://www.chosun.com/site/data/rss/Court.xml";
	private String education = "http://www.chosun.com/site/data/rss/education.xml";
	private String defence = "http://www.chosun.com/site/data/rss/defense.xml";
	private String law = "http://www.chosun.com/site/data/rss/law.xml";
	private String medical = "http://www.chosun.com/site/data/rss/medical.xml";
	private String environment = "http://www.chosun.com/site/data/rss/environment.xml";
	private String traffic = "http://www.chosun.com/site/data/rss/traffic.xml";
	private String labor = "http://www.chosun.com/site/data/rss/labor.xml";
	private String asia = "http://www.chosun.com/site/data/rss/asia.xml";
	private String usa = "http://www.chosun.com/site/data/rss/usa.xml";
	private String europe = "http://www.chosun.com/site/data/rss/europe.xml";
	private String dyj = "http://www.chosun.com/site/data/rss/dyj.xml";
	private String meast = "http://www.chosun.com/site/data/rss/meast.xml";
	private String w18 = "http://www.chosun.com/site/data/rss/w18.xml";
	private String w_issue = "http://www.chosun.com/site/data/rss/w_issue.xml";
	private String health = "http://www.chosun.com/site/data/rss/health.xml";
	private String books = "http://www.chosun.com/site/data/rss/books.xml";
	private String culture_media = "http://www.chosun.com/site/data/rss/culture_media.xml";
	private String religion = "http://www.chosun.com/site/data/rss/religion.xml";
	private String c_people = "http://www.chosun.com/site/data/rss/c_people.xml";
	private String life = "http://www.chosun.com/site/data/rss/life.xml";
	private String f_art = "http://www.chosun.com/site/data/rss/f_art.xml";
	private String design = "http://www.chosun.com/site/data/rss/design.xml";	
	private String newsToday_list = "http://myhome.chosun.com/rss/www_section_rss.xml";
	private String entertainment_list = "http://www.chosun.com/site/data/rss/ent.xml";
	
	public URLlist(){}
	
	public String getAssembly() {
		return assembly;
	}
	public String getNk() {
		return nk;
	}
	public String getAdministration() {
		return administration;
	}
	public String getDiplomacy() {
		return diplomacy;
	}
	public String getCwd() {
		return cwd;
	}
	public String getBaseball() {
		return baseball;
	}
	public String getSoccer() {
		return soccer;
	}
	public String getBas() {
		return bas;
	}
	public String getGolf() {
		return golf;
	}
	public String getCourt() {
		return court;
	}
	public String getEducation() {
		return education;
	}
	public String getDefence() {
		return defence;
	}
	public String getLaw() {
		return law;
	}
	public String getMedical() {
		return medical;
	}
	public String getEnvironment() {
		return environment;
	}
	public String getTraffic() {
		return traffic;
	}
	public String getLabor() {
		return labor;
	}
	public String getAsia() {
		return asia;
	}
	public String getUsa() {
		return usa;
	}
	public String getEurope() {
		return europe;
	}
	public String getDyj() {
		return dyj;
	}
	public String getMeast() {
		return meast;
	}
	public String getW18() {
		return w18;
	}
	public String getW_issue() {
		return w_issue;
	}
	public String getHealth() {
		return health;
	}
	public String getBooks() {
		return books;
	}
	public String getCulture_media() {
		return culture_media;
	}
	public String getReligion() {
		return religion;
	}
	public String getC_people() {
		return c_people;
	}
	public String getLife() {
		return life;
	}
	public String getF_art() {
		return f_art;
	}
	public String getDesign() {
		return design;
	}
	public String getNewsToday_list() {
		return newsToday_list;
	}
	public String getEntertainment_list() {
		return entertainment_list;
	}
}
