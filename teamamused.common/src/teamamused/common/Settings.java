/**
 * 
 */
package teamamused.common;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 
 * Klasse zur lessen und speichern der App Settings
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class Settings {

	/**
	 * Aufzählung mit den Einstellungen welche im Settings File vorhanden sind.
	 * 
	 * @author Daniel Hirsbrunner
	 *
	 */
	public static enum Setting {
		Language("Language"), 
		DBFileName("DbFileName"), 
		ImagePath("ImagePath");

		String optionName;

		Setting(String name) {
			this.optionName = name;
		}
		
		@Override
		public String toString() {
			return this.optionName;
		}
	}

	private Properties settings;
	private Logger logger;

	/**
	 * Initialisiert die Settings Klasse
	 */
	public Settings() {
		this.logger = ServiceLocator.getInstance().getLogger();
		logger.info("Lade Konfigurationen");
		this.loadSettings();
	}

	/**
	 * gibt den gesetzten Wert der übergebenen Option zurück
	 * 
	 * @param setting
	 *            Einstellung die Abgefragt werden soll
	 * @return Einstellungswert
	 */
	public String getSetting(Setting setting) {
		return this.settings.getProperty(setting.toString());
	}

	/**
	 * Lädt die Einstellungsdatei
	 */
	private void loadSettings() {
		// Load default properties from wherever the code is
		settings = new Properties();

		try (InputStream inputStream = Settings.class.getResourceAsStream("teamamused.config");) {
			settings.load(inputStream);
		} catch (Exception e) {
			logger.warning("Settings Datei konnte geöffnet werden gefunden werden: teamamused.config");
		}
	}
}
