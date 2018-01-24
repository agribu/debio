package debio.utils;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import util.Config;

/**
 * This class realizes Bing translator API calls
 * 
 * @author Benjamin Schuermann <edu (at) schuermann (dot) cc>
 */
public abstract class BingTranslator {

	// Set your Windows Azure Marketplace client info
	// See: http://msdn.microsoft.com/en-us/library/hh454950.aspx

	/* Windows Azure client ID */
	private static final String CLIENT_ID = Config.bingClientID;
	/* Windows Azure client secret */
	private static final String CLIENT_SECRET = Config.bingClientSecret;

	/*
	 * Translates an input text from one language into another
	 * 
	 * @param input Input text
	 * 
	 * @param fromLang Source language
	 * 
	 * @param toLang Language of translation
	 * 
	 * @return Translated input text
	 */
	public static String translate(String input, Language fromLang, Language toLang) {
		String translatedText = "";

		Translate.setClientId(CLIENT_ID);
		Translate.setClientSecret(CLIENT_SECRET);

		try {
			translatedText = Translate.execute(input, fromLang, toLang);
		} catch (Exception e) {
			System.err.println("Bing translation error! :: INPUT: \"" + input.substring(0, Math.min(input.length(), 10))
					+ " [...]\"" + " :: FROM: " + fromLang.name() + " :: TO: " + toLang.name());
		}

		return translatedText;
	}

}