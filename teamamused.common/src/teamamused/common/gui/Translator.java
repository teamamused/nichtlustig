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
	protected Locale currentLocale;
	protected ResourceBundle resourceBundle;
	protected ServiceLocator serviceLocator = ServiceLocator.getInstance();
	protected Logger logger = LogHelper.getDefaultLogger();

	// Konstruktor
	public Translator(String localeString) {
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
		
		// Load the resource strings
		// TODO
//		 resourceBundle = ResourceBundle.getBundle(Main.getClass().getName(),currentLocale);


		logger.info("Loaded resources for " + locale.getLanguage());
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public String getString(String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			System.out.println("Wert fehlt zu: " + key);
			return "";
		}
	}

}
