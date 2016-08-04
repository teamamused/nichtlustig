package teamamused.common.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import teamamused.common.LogHelper;
import teamamused.common.ServiceLocator;

/**
 * Gemäss Unterlagen und Codebeispielen aus dem Modul Software Engineering 2 von
 * Bradley Richards
 * 
 * @author Michelle
 *
 */
// Gemäss Unterlagen aus dem Modul Software Engineering 2 von Bradley Richards
public class Translator {
	private final static String CLIENT_RESOURCE = "teamamused.client/Client";
	
	protected Locale currentLocale;
	protected ResourceBundle resourceBundle;
	protected ServiceLocator serviceLocator = ServiceLocator.getInstance();
	protected Logger logger = LogHelper.getDefaultLogger();

	// Konstruktor default mässig für die Client Resourcen
	public Translator(String localeString) {
		this(localeString, CLIENT_RESOURCE);
	}
	
	public Translator(String localeString, String resources) {
		// Can we find the language in our supported locales?
		// If not, use VM default locale
		Locale locale = Locale.getDefault();
		if (localeString != null) {
			Locale[] availableLocales = serviceLocator.getLocales();
			for (int i = 0; i < availableLocales.length; i++) {
				String tmpLang = availableLocales[i].getLanguage();
				if (localeString.substring(0, tmpLang.length()).equals(tmpLang)) {
					locale = availableLocales[i];
					break;
				}
			}
		}

		Locale.setDefault(locale); // Change VM default (for dialogs, etc.)
		currentLocale = locale;		
		// Load the resource strings - Original
		resourceBundle = ResourceBundle.getBundle(resources,currentLocale);
		
		logger.info("Loaded resources for " + locale.getLanguage());
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * Public method to get string resources, default to "--" *
	 * @param text Enum eintrag für die Sprachkonstante
	 * @return Sprachtext für das aktuelle Locale
	 */
	public String getString(LangText text) {
		try {
			return resourceBundle.getString(text.toString());
		} catch (MissingResourceException e) {
			logger.warning("Missing string: " + text.toString());
			return "--";
		}
	}

}
