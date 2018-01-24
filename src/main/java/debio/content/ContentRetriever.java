package debio.content;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * This class realizes the retrieval of the content of a profile hold by
 * https://www.deutsche-biographie.de
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public class ContentRetriever {

	/* Profile ID */
	private String id;
	/* URL stub */
	private URL url = null;
	/* URL prefix */
	private String urlGDNPrefix = "https://www.deutsche-biographie.de/gnd";
	/* URL postfix */
	private String htmlPostfix = ".html";

	/*
	 * Constructor for generating URL to be called
	 * 
	 * @param id Profile ID
	 */
	public ContentRetriever(String id) {
		this.id = id;
		try {
			this.url = new URL(urlGDNPrefix + this.id + htmlPostfix);
		} catch (MalformedURLException e) {
			System.err.println("Malformed GDN-URL! :: " + this.id + " :: " + this.url.toString());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}
	}

	/*
	 * Retrieves the raw HTML content of a profile page
	 * 
	 * @return raw HTML content as string
	 */
	public String retrRawContent() {
		Scanner scanner = null;
		String rawtext = "";

		try {
			scanner = new Scanner(this.url.openStream());
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found! :: " + this.id + " :: " + this.url.toString());
		} catch (IOException e) {
			System.err.println("IO Error! :: " + this.id + " :: " + this.url.toString());
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
		}

		rawtext = scanner.useDelimiter("\\A").next();
		scanner.close();

		return rawtext;
	}

}