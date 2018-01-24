package debio;

import java.util.ArrayList;

import com.google.common.base.Strings;
import com.memetix.mst.language.Language;

import debio.content.ContentRetriever;
import debio.content.IDExtractor;
import debio.content.model.Biography;
import debio.content.model.Person;
import debio.utils.CntToModelTransl;
import debio.utils.ModelToDBTransl;

/**
 * This class realizes the testing of the content model and of the I/O
 * interaction with https://www.deutsche-biographie.de
 * 
 * @author Benjamin Schuerman <edu (at) schuermann (dot) cc>
 */
public class ModelTest {

	/*
	 * Executes the building process of an existing database
	 * 
	 * @param id Profile ID
	 * 
	 * @param mtdbt ModelToDBTransl object
	 * 
	 * @param i Index number in list of persons
	 * 
	 * @param total Total number of persons
	 */
	public static void buildDatabase(String id, ModelToDBTransl mtdbt, int i, int total) {
		Person p;
		Biography bio;
		Boolean error = false;
		CntToModelTransl ctmt = new CntToModelTransl(id);

		ctmt.genPerson();
		p = ctmt.getPerson();
		error = (p == null) ? true : false;

		// Add new person and biography to database
		if (!error) {
			ctmt.genBio();
			bio = ctmt.getBio();
			mtdbt.insertPerson(p);
			mtdbt.insertBiography(bio);

			System.out.println("[" + i + "|" + total + "] Insert Person: " + p.getName() + " (" + p.getIndex() + ")");
		} else {
			System.err.println("(2) Person mit id=" + id + " ist mehr als einmal im Index");
		}
	}

	/*
	 * Prints biographic contents and its translation
	 * 
	 * @param id Profile ID
	 * 
	 * @param type Register type
	 * 
	 * @param i Index number
	 */
	public static void printTranslation(String id, String type, int i) {
		CntToModelTransl ctmt = new CntToModelTransl(id);
		ctmt.genPerson();
		ctmt.genBio();

		Person p = ctmt.getPerson();
		System.out.println("\n############ Index :: " + i + " ############");

		System.out.println(p.getIndexUrl());

		// Print german content
		System.out.println("## Person: " + p.getName() + "(" + p.getIndex() + ")" + ":: Lang=DE ##");
		String adbCnt = ctmt.getBio().getCnt(type, "de");
		System.out.println(adbCnt.substring(0, Math.min(adbCnt.length(), 500)) + "[...]");

		// Print englisch content
		System.out.println("## Person: " + p.getName() + "(" + p.getIndex() + ")" + ":: Lang=EN ##");
		adbCnt = ctmt.getBio().getCnt(type, "en");
		System.out.println(adbCnt.substring(0, Math.min(adbCnt.length(), 500)) + "[...]");
	}

	/*
	 * Prints the data of objects of the content model
	 * 
	 * @param id Profile ID
	 */
	public static void printModelData(String id) {
		CntToModelTransl ctmt = new CntToModelTransl(id);
		ctmt.setCntRetriever(new ContentRetriever(id));
		ctmt.genPerson();
		ctmt.genBio();
		System.out.println(ctmt.getPerson().toString());
		System.out.println(ctmt.getBio().toString());
	}

	/*
	 * Prints profile data for a register profile at a particular index
	 * 
	 * @param id Profile ID
	 * 
	 * @param type Register type
	 * 
	 * @param i Index number
	 */
	private static void printEntry(String id, String type, int i) {
		CntToModelTransl ctmt = new CntToModelTransl(id);
		String cnt = "No content available";
		ctmt.setCntRetriever(new ContentRetriever(id));
		ctmt.genPerson();
		ctmt.genBio();

		// Print header
		System.out.println("\n" + Strings.repeat("#", 85));
		System.out.println(type.toUpperCase() + "-Index " + i + " :: " + type.toUpperCase() + ":" + id + " :: "
				+ ctmt.getPerson().getName());
		System.out.println(Strings.repeat("#", 85));

		System.out.println(ctmt.getPerson().getIndexUrl());

		if (type.equals("adb") && !ctmt.getBio().getAdbCnt().contains(cnt)) {
			cnt = ctmt.getBio().getAdbCnt();
		} else if (type.equals("ndb") && !ctmt.getBio().getNdbCnt().contains(cnt)) {
			cnt = ctmt.getBio().getNdbCnt();
		}

		// Print content
		System.out.println(cnt.substring(0, Math.min(cnt.length(), 80)) + "[...]");
	}

	/*
	 * Prints iteratively for a collection of IDs
	 * 
	 * @param start Start index number
	 * 
	 * @param end End index number
	 * 
	 * @param type Register type
	 */
	public static void printIteratively(int start, int end, String type) {
		IDExtractor ide = new IDExtractor(type);
		// Get dataset containing all profile IDs of type (ADB, NDB)
		ArrayList<String> ext = new ArrayList<String>(ide.extractIDS());
		// Profile ID list defined by start and end index (e.g. 20 - 50)
		ArrayList<String> al = new ArrayList<String>();

		int i = start;
		// if end = -1, set limit to size of dataset
		int e = (end == -1) ? ext.size() - 1 : end;

		// Get profile IDs in between index values
		while (i < e) {
			al.add(ext.get(i));
			i++;
		}

		// Print total number of profile IDs hold by a register
		System.out.print("Index Size: " + ext.size() + "\n");

		ModelToDBTransl mtdbt = new ModelToDBTransl();

		i = start;
		for (String id : al) {
			// printEntry(id, type, i);
			// printModelData(id);
			// testTranslation(id, type, i);
			buildDatabase(id, mtdbt, i, (ext.size() - 1));
			i++;
		}

		mtdbt.closeConnection();
	}

	/*
	 * Main function for testing
	 */
	public static void main(String[] args) {
		 printModelData("101796641");
		
		// if end = -1, set limit to size of dataset
//		 printIteratively(0, -1, "adb");
	}

}