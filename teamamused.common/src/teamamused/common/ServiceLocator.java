package teamamused.common;

import java.util.Locale;
import java.util.logging.Logger;

import javafx.application.HostServices;
import teamamused.common.db.XmlDataBaseContext;
import teamamused.common.gui.Translator;
import teamamused.common.interfaces.IDataBaseContext;

/**
 * 
 * ServiceLocator für die zentrale Verwaltung der Referenzen
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class ServiceLocator {
	private static ServiceLocator instance;
	private Logger logger;
	public static final String APPLICATION_NAME = "Nicht Lustig";
	private Settings settings;
	private IDataBaseContext dbContext;
	private HostServices hostServices;
	private Translator translator;
	final private Locale[] locales = new Locale[] { new Locale("de"), new Locale("ch") };

	/**
	 * Getter der Instanz vom ServiceLocater
	 * 
	 * @return Instanz zum Service Locator
	 */
	public static ServiceLocator getInstance() {
		if (instance == null) {
			instance = new ServiceLocator();
		}
		return instance;
	}

	/**
	 * Konstruktor muss private sein für Singelton Pattern
	 */
	private ServiceLocator() {
	}

	/**
	 * Gibt den aktuell gesetzten Logger zurück, falls keiner gesetzt wurde wird
	 * ein standard Logger erstellt.
	 * 
	 * @return Standard Logger
	 */
	public Logger getLogger() {
		if (this.logger == null) {
			this.logger = LogHelper.getDefaultLogger();
		}
		return logger;
	}

	/**
	 * Setzt einen neuen Logger
	 * 
	 * @param logger
	 *            Logger welcher gesetzt wird
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Gibt den aktuell gesetzten Datenbankkontext zurück, falls keiner gesetzt
	 * wurde wird ein standard DB Context erstellt.
	 * 
	 * @return DatenbankKontext
	 */
	public IDataBaseContext getDBContext() {
		if (this.dbContext == null) {
			this.dbContext = new XmlDataBaseContext();
			this.dbContext.loadContext();
		}
		return this.dbContext;
	}

	/**
	 * Setzt einen neuen Datenbank Kontext
	 * 
	 * @param context
	 *            Datenbank Kontext welcher gesetzt wird
	 */
	public void setDBContext(IDataBaseContext context) {
		this.dbContext = context;
	}

	/**
	 * Gibt die Referenz zu den Einstellungen zurück
	 * 
	 * @return Settings Referenz
	 */
	public Settings getSettings() {
		if (this.settings == null) {
			this.settings = new Settings();
		}
		return settings;
	}

	/**
	 * Setzt die Referenz zu den Einstellungen
	 * 
	 * @param settings
	 *            Einstellungsobjet Referenz
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	/**
	 * Gibt die JavaFX-HostServices zurück
	 * 
	 * @return {@link HostServices}
	 */
	public HostServices getHostServices() {
		return hostServices;
	}

	/**
	 * Setzt die JavaFX-HostServices
	 * 
	 * @param hostServices
	 *            {@link HostServices}
	 */
	public void setHostServices(HostServices hostServices) {
		this.hostServices = hostServices;
	}

	/**
	 * Gibt die Referenz auf den Translator zurück, welcher als Singleton
	 * implementiert ist
	 * 
	 * @return Referenz auf Translator-Singleton
	 */
	public Translator getTranslator() {
		return translator;
	}

	/**
	 * Setzt den Translator
	 * 
	 * @param translator
	 */
	public void setTranslator(Translator translator) {
		this.translator = translator;
	}

	/**
	 * Gibt ein Array mit Locales zurück
	 * 
	 * @return Locale-Array
	 */
	public Locale[] getLocales() {
		return locales;
	}
	
}