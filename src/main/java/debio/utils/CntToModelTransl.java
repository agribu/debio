package debio.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import debio.content.ContentRetriever;
import debio.content.TextSanitizer;
import debio.content.model.Biography;
import debio.content.model.Person;

/**
 * This class realizes the translation of a raw HTML content of a profile page
 * into the content model
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class CntToModelTransl {

	/* Profile ID */
	private String id;
	/* Raw HTML content */
	private String rawCnt;
	/* Person object */
	private Person person;
	/* Biography object */
	private Biography bio;
	/* ContentRetriever */
	private ContentRetriever cntRtrv;
	/* TextSanitizer */
	private TextSanitizer textSan;

	/*
	 * Constructor for initializing global variables and retrieving the raw HTML
	 * content
	 * 
	 * @param id Profile ID
	 */
	public CntToModelTransl(String id) {
		this.id = id;
		this.person = null;
		this.bio = null;
		this.textSan = new TextSanitizer("");
		this.cntRtrv = new ContentRetriever(this.id);
		this.rawCnt = cntRtrv.retrRawContent();
	}

	/*
	 * Checks if raw HTML content contains ADB register reference
	 * 
	 * @return TRUE if ADB register reference exists in raw HTML content
	 */
	private Boolean isADB() {
		return this.rawCnt.contains("id=\"adbcontent_leben\"");
	}

	/*
	 * Checks if raw HTML content contains NDB register reference
	 * 
	 * @return TRUE if NDB register reference exists in raw HTML content
	 */
	private Boolean isNDB() {
		return this.rawCnt.contains("id=\"ndbcontent_leben\"");
	}

	/*
	 * Extracts the name abbreviation included in a raw HTML content
	 * 
	 * @param content Raw HTML content
	 * 
	 * @return Name abbreviation
	 */
	public String extrAbbr(String content) {
		this.textSan = new TextSanitizer();
		this.textSan.setRawCnt(content);
		this.textSan.processAbbr();
		return this.textSan.getAbbr();
	}

	/*
	 * Extracts the biographical content block from the raw HTML content
	 * 
	 * @param type Register type
	 */
	public String extrBioCnt(String type) {
		String content = "";
		Pattern regex = Pattern.compile("id=\"" + type + "content_leben(.*)", Pattern.DOTALL);
		Matcher regexMatcher = regex.matcher(this.rawCnt);

		if (regexMatcher.find()) {
			content = StringUtils.substringBetween(regexMatcher.group(1), "<p>", "</p>");
		} else {
			content = "No content available!";
		}

		return content.trim();
	}

	/*
	 * Generates a Biography object from the raw HTML content
	 * 
	 * @return Biography object
	 */
	public Biography genBio() {
		Person person = (this.person == null) ? genPerson() : this.person;
		Biography bio;
		String content = "";
		bio = new Biography();
		bio.setId(person.getId());
		bio.setIndex(person.getIndex());

		if (isADB()) {
			this.textSan = new TextSanitizer(extrBioCnt("adb"));
			content = StringEscapeUtils.unescapeHtml4(textSan.getRawCnt());
			bio.setAdbCnt(content);
		}
		if (isNDB()) {
			this.textSan = new TextSanitizer(extrBioCnt("ndb"));
			content = StringEscapeUtils.unescapeHtml4(textSan.getRawCnt());
			bio.setNdbCnt(content);
		}

		this.bio = bio;
		return bio;
	}

	/*
	 * Generates a Person object from the raw HTML content
	 * 
	 * @return Person object
	 */
	public Person genPerson() {
		// New person
		Person person = new Person();
		// Set name
		String name = StringUtils.substringBetween(this.rawCnt, "<h1>", "</h1>");
		person.setName(StringEscapeUtils.unescapeHtml4(name));
		
		if (name.equals("Fehler")) {
			System.err.println("(1) Person mit id=" + id + " ist mehr als einmal im Index");
			return null;
		}
		
		// Set abbreviation
		String abbr = extrAbbr(extrBioCnt((isADB()) ? "adb" : (isNDB()) ? "ndb" : ""));
		person.setAbbr(StringEscapeUtils.unescapeHtml4(abbr));
		// Set index
		String index = StringUtils.substringBetween(this.rawCnt, "data-personid=\"", "\"");
		person.setIndex(StringEscapeUtils.unescapeHtml4(index));
		// Set ID
		person.setId(this.id);
		// Set denomination
		Pattern regex;
		Matcher regexMatcher;
		regex = Pattern.compile("indexlabel\">Konfession+(.*)", Pattern.DOTALL);
		regexMatcher = regex.matcher(rawCnt);
		if (regexMatcher.find()) {
			String denomination = StringUtils.substringBetween(regexMatcher.group(1), "<dd class=\"indexvalue\">",
					"</dd>");
			person.setDenomination(StringEscapeUtils.unescapeHtml4(denomination));
		} else {
			person.setDenomination("");
		}
		// Set URLs
		String aurl = (isADB()) ? "http://www.deutsche-biographie.de/pnd" + id + ".html#" + "adb" + "content" : "";
		String nurl = (isNDB()) ? "http://www.deutsche-biographie.de/pnd" + id + ".html#" + "ndb" + "content" : "";
		String iurl = (!index.equals("") || index != null) ? "https://www.deutsche-biographie.de/" + index + ".html" : "";
		person.setAdbUrl(aurl);
		person.setNdbUrl(nurl);
		person.setIndexUrl(iurl);

		this.person = person;
		return this.person;
	}

	/*
	 * Returns the profile ID
	 * 
	 * @return Profile ID
	 */
	public String getId() {
		return id;
	}

	/*
	 * Sets the profile ID
	 * 
	 * @param id Profile ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * Returns the raw HTML content
	 * 
	 * @return Raw HTML content
	 */
	public String getRawcontent() {
		return rawCnt;
	}

	/*
	 * Sets the raw HTML content
	 * 
	 * @param raw Raw HTML content
	 */
	public void setRawcontent(String raw) {
		this.rawCnt = raw;
	}

	/*
	 * Returns the Person object
	 * 
	 * @return Person object
	 */
	public Person getPerson() {
		return person;
	}

	/*
	 * Sets the Person object
	 * 
	 * @param person Person
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	/*
	 * Returns the ContentRetriever object
	 * 
	 * @return ContentRetriever object
	 */
	public ContentRetriever getCntRetriever() {
		return cntRtrv;
	}

	/*
	 * Sets the ContentRetriever object
	 * 
	 * @param cntRetriever ContentRetriever object
	 */
	public void setCntRetriever(ContentRetriever cntRetriever) {
		this.cntRtrv = cntRetriever;
	}

	/*
	 * Returns the Biography object
	 * 
	 * @return Biography object
	 */
	public Biography getBio() {
		return bio;
	}

	/*
	 * Sets the Biography object
	 * 
	 * @param bio Biography object
	 */
	public void setBio(Biography bio) {
		this.bio = bio;
	}

}