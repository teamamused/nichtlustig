package teamamused.common;

import java.util.logging.Logger;

import teamamused.common.db.XmlDataBaseContext;
import teamamused.common.interfaces.*;

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

	/**
	 * Getter der Instanz vom ServiceLocater
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
	 * Gibt den aktuell gesetzten Datenbankkontext zurück, falls keiner gesetzt wurde wird
	 * ein standard DB Context erstellt.
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
	 * @param settings Einstellungsobjet Referenz
	 */
	public void setSetting(Settings settings) {
		this.settings = settings;
	}

}