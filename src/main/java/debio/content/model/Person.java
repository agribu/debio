package debio.content.model;

/**
 * This class realizes Person objects
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class Person {

	/* Profile ID */
	private String id;
	/* Full name */
	private String name;
	/* Name abbreviation */
	private String abbr;
	/* Index ID */
	private String index;
	/* Denomination */
	private String denomination;
	/* ADB profile ID URL */
	private String adbUrl;
	/* NDB profile ID URL */
	private String ndbUrl;
	/* Index ID URL */
	private String indexUrl;

	/*
	 * Constructor for initializing global variables
	 */
	public Person() {
		this.name = "";
		this.abbr = "";
		this.id = "";
		this.index = "";
		this.denomination = "";
		this.adbUrl = "";
		this.ndbUrl = "";
		this.indexUrl = "";
	}

	/*
	 * Returns full name
	 * 
	 * @return Full name
	 */
	public String getName() {
		return name;
	}

	/*
	 * Sets full name
	 * 
	 * @param Full name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Returns name abbreviation
	 * 
	 * @return Name abbreviation
	 */
	public String getAbbr() {
		return abbr;
	}

	/*
	 * Sets name abbreviation
	 * 
	 * @param Name abbreviation
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	/*
	 * Returns profile ID
	 * 
	 * @return Profile ID
	 */
	public String getId() {
		return id;
	}

	/*
	 * Sets profile ID
	 * 
	 * @param Profile ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * Returns index ID
	 * 
	 * @return Index ID
	 */
	public String getIndex() {
		return index;
	}

	/*
	 * Sets index ID
	 * 
	 * @param Index ID
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/*
	 * Returns denomination
	 * 
	 * @return Denomination
	 */
	public String getDenomination() {
		return denomination;
	}

	/*
	 * Sets denomination
	 * 
	 * @param Denomination
	 */
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	/*
	 * Returns URL for ADB profile ID
	 * 
	 * @return URL for ADB profile ID
	 */
	public String getAdbUrl() {
		return adbUrl;
	}

	/*
	 * Sets URL for ADB profile ID
	 * 
	 * @param adbIdUrl URL for ADB profile ID
	 */
	public void setAdbUrl(String adbIdUrl) {
		this.adbUrl = adbIdUrl;
	}

	/*
	 * Sets URL for NDB profile ID
	 * 
	 * @param ndbIdUrl URL for NDB profile ID
	 */
	public void setNdbUrl(String ndbIdUrl) {
		this.ndbUrl = ndbIdUrl;
	}

	/*
	 * Returns URL for NDB profile ID
	 * 
	 * @return URL for NDB profile ID
	 */
	public String getNdbUrl() {
		return ndbUrl;
	}

	/*
	 * Returns URL for index ID
	 * 
	 * @return URL for index ID
	 */
	public String getIndexUrl() {
		return indexUrl;
	}

	/*
	 * Sets URL for index ID
	 * 
	 * @param indexURL URL for index ID
	 */
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person [name=" + name + ", abbr=" + abbr + ", id=" + id + ", index=" + index + ", denomination="
				+ denomination + ", adbURL=" + adbUrl + ", ndbUrl=" + ndbUrl + ", indexUrl=" + index + "]";
	}

}