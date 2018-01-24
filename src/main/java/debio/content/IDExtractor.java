package debio.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * This class realizes the processing of beacon lists for extracting profile IDs
 * See: http://www.historische-kommission-muenchen-editionen.de/pnd.html
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class IDExtractor {

	/* List of all profile IDs hold by a beacon list */
	private Collection<String> idlist = null;
	/* URL stub */
	private URL url = null;
	/* Register type: ADB, NDB */
	private String type = "";
	/* URL prefix */
	private final String prefix = "http://www.historische-kommission-muenchen-editionen.de/beacon_";
	/* URL postfix */
	private final String postfix = ".txt";

	/*
	 * Constructor for setting register type and generating URL
	 * 
	 * @param type Register type
	 */
	public IDExtractor(String type) {
		this.type = type;
		try {
			url = new URL(prefix + this.type + postfix);
		} catch (MalformedURLException e) {
			System.err.println("Malformed URL! :: " + this.type + " :: " + this.url.toString());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	/*
	 * Extracts profile IDs from a beacon list
	 * 
	 * @return Collection of profile IDs
	 */
	public Collection<String> extractIDS() {
		InputStream is = null;
		this.idlist = new ArrayList<String>();

		try {
			is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			while ((line = br.readLine()) != null) {
				// A line is construed as "118500015|Aal, Johannes|sfz15"
				// Grep first token as id, delimited by "|"
				if (line.contains("|")) {
					StringTokenizer st = new StringTokenizer(line, "|");
					if (st.hasMoreElements()) {
						String id = st.nextElement().toString();
						this.idlist.add(id);
					}
				}
			}

			br.close();
			is.close();
		} catch (IOException e) {
			System.err.println("IO Error! :: " + this.type + " :: " + this.url.toString());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		return this.idlist;
	}

}