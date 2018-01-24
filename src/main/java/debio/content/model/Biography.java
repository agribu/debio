package debio.content.model;

import com.memetix.mst.language.Language;

import debio.utils.BingTranslator;

/**
 * This class realizes Biography objects
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class Biography {

	/* Profile ID */
	private String id;
	/* Index ID */
	private String index;
	/* ADB biography content */
	private String adbCnt;
	/* NDB biography content */
	private String ndbCnt;
	
	private String adbCntEng;
	private String ndbCntEng;

	/*
	 * Constructor for initializing global variables
	 */
	public Biography() {
		this.id = "";
		this.index = "";
		this.adbCnt = "";
		this.ndbCnt = "";
		this.adbCntEng = "";
		this.ndbCntEng = "";
	}
	
	

	public String getAdbCntEng() {
		return adbCntEng;
	}



	public void setAdbCntEng(String adbCntEng) {
		this.adbCntEng = adbCntEng;
	}



	public String getNdbCntEng() {
		return ndbCntEng;
	}



	public void setNdbCntEng(String ndbCntEng) {
		this.ndbCntEng = ndbCntEng;
	}



	/*
	 * Returns biography content for a particular register in a defined language
	 * 
	 * @param lang Language selection
	 * 
	 * @param type Register type
	 * 
	 * @return Biography content
	 */
	public String getCnt(String type, String lang) {
		String content = (type.equals("adb")) ? this.adbCnt : (type.equals("ndb")) ? this.ndbCnt : "Kein Inhalt!";

		if (lang.equals("de")) {
			return content;
		} else if (lang.equals("en")) {
			System.out.println("DO TRANSLATION");
			return BingTranslator.translate(content, Language.GERMAN, Language.ENGLISH);
		} else {
			return BingTranslator.translate(content, Language.GERMAN, Language.GERMAN);
		}
	}

	/*
	 * Sets biography content for a particular register
	 * 
	 * @param type Register type
	 * 
	 * @param cnt Biography content
	 */
	public void setCnt(String type, String cnt) {
		if (type.equals("adb")) {
			this.setAdbCnt(cnt);
		} else if (type.equals("ndb")) {
			this.setNdbCnt(cnt);
		}
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
	 * @param id Profile ID
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
	 * @param index Index ID
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/*
	 * Returns biography content of ADB register
	 * 
	 * @return Biography content of ADB register
	 */
	public String getAdbCnt() {
		return adbCnt;
	}

	/*
	 * Sets biography content of ADB register
	 * 
	 * @param adbCnt Biography content of ADB register
	 */
	public void setAdbCnt(String adbCnt) {
		this.adbCnt = adbCnt;
	}

	/*
	 * Returns biography content of NDB register
	 * 
	 * @return Biography content of NDB register
	 */
	public String getNdbCnt() {
		return ndbCnt;
	}

	/*
	 * Sets biography content of NDB register
	 * 
	 * @param ndbCnt Biography content of NDB register
	 */
	public void setNdbCnt(String ndbCnt) {
		this.ndbCnt = ndbCnt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String adbtext = this.adbCnt.substring(0, Math.min(this.adbCnt.length(), 10)) + "[...]";
		String ndbtext = this.ndbCnt.substring(0, Math.min(this.ndbCnt.length(), 10)) + "[...]";
		return "Biography [id=" + id + ", index=" + index + ", adbCnt=" + adbtext + ", ndbCnt=" + ndbtext + "]";
	}
}