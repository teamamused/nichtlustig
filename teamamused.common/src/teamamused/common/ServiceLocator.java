package teamamused.common;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	    this.logger = this.createDefaultLogger();
	}
	return logger;
    }

    /**
     * Setzt einen neuen Logger
     * 
     * @param logger
     */
    public void setLogger(Logger logger) {
	this.logger = logger;
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
     * @param logger
     */
    public void setSetting(Settings settings) {
	this.settings = settings;
    }

    /**
     * Standard Logger erstellen, wie behandelt in der Vorlesung Software
     * Enginiering 2
     */
    private Logger createDefaultLogger() {
	Logger mainLogger = Logger.getLogger("");
	mainLogger.setLevel(Level.FINEST);

	// Standardmässig wird alles auf die Konsole ausgegeben, wir wollen in
	// der Konsole alles ab Level Info sehen
	Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
	defaultHandlers[0].setLevel(Level.INFO);

	// Zus�tzlicher Logger erstellen welcher in die LogDatei schreibt. Dort
	// wollen wir jedes Detail konfiguriert haben
	Logger fileLogger = Logger.getLogger(ServiceLocator.APPLICATION_NAME);
	fileLogger.setLevel(Level.FINEST);

	// Neuer LogHandler erstellen. Folgende Parameter:
	// Pattern beschrieb für den Dateinamen:
	// - "%t" the system temporary directory
	// - "%u" a unique number to resolve conflicts
	// - "%g" the generation number to distinguish rotated logs
	// limit: Maximale Dateigr�sse in Bytes
	// count: Wieviele Logfiles behaltet werden (wenn 9 Logfiles voll sind
	// wird das erste wieder �berschrieben)
	try {
	    Handler logHandler = new FileHandler("%t/" + ServiceLocator.APPLICATION_NAME + "_%u" + "_%g" + ".log",
		    1000000, 9);
	    logHandler.setLevel(Level.FINEST);
	    fileLogger.addHandler(logHandler);
	} catch (Exception e) {
	    throw new RuntimeException("Logfile konnte nicht erstellt werden: " + e.toString());
	}

	return fileLogger;
    }

}