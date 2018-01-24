package debio.content;

import org.apache.commons.lang3.StringUtils;

/**
 * This class realizes the removing of HTML tags and resolves name abbreviations
 * from a raw HTML content
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class TextSanitizer {

	/* Raw HTML content */
	private String rawCnt;
	/* Name abbreviation */
	private String abbr = "";

	/*
	 * Empty constructor stub
	 */
	public TextSanitizer() {
	}

	/*
	 * Constructor for setting raw HTML content and starting sanitizing process
	 * 
	 * @param raw Raw HTML content to be sanitized
	 */
	public TextSanitizer(String raw) {
		this.rawCnt = raw;
		sanitize();
	}

	/*
	 * Removes HTML tags and resolves name abbreviations from a raw HTML content
	 * 
	 * @return Sanitized raw HTML content
	 */
	public String sanitize() {
		processAbbr();
		eliminateTags();

		return this.rawCnt;
	}

	/*
	 * Resolves all name abbreviations
	 * 
	 * @return Raw HTML content with resolved name abbreviations
	 */
	public String processAbbr() {
		String title, abbr = "";

		if (!this.rawCnt.isEmpty() && this.rawCnt != null && this.rawCnt.contains("abbr")) {
			title = StringUtils.substringBetween(this.rawCnt, "title='", "'");
			this.rawCnt = this.rawCnt.replace("title='" + title + "'", "BPTITLE");
			abbr = StringUtils.substringBetween(this.rawCnt, "BPTITLE>", "<");

			if (title != null && abbr != null) {
				this.abbr = abbr;
				this.rawCnt = this.rawCnt.replace(abbr, title);
			}
		}

		return this.rawCnt;
	}

	/*
	 * Removes all HTML tags
	 * 
	 * @return Raw HTML content with removed HTML tags
	 */
	public String eliminateTags() {
		while (this.rawCnt.contains("<") || (this.rawCnt.contains("</"))) {
			String tagStartContent = StringUtils.substringBetween(this.rawCnt, "<", ">");
			this.rawCnt = this.rawCnt.replace("<" + tagStartContent + ">", "");
			String tagEndContent = StringUtils.substringBetween(this.rawCnt, "</", ">");
			this.rawCnt = this.rawCnt.replace("</" + tagEndContent + ">", "");
		}

		return this.rawCnt;
	}

	/*
	 * Returns name abbreviation
	 * 
	 * @return Name abbreviation
	 */
	public String getAbbr() {
		return this.abbr;
	}

	/*
	 * Returns raw HTML content
	 * 
	 * @return Raw HTML content
	 */
	public String getRawCnt() {
		return this.rawCnt;
	}

	/*
	 * Sets raw HTML content
	 * 
	 * @param raw Raw HTML content
	 */
	public void setRawCnt(String raw) {
		this.rawCnt = raw;
	}

}