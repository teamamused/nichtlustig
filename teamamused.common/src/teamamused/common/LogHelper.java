package teamamused.common;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {

	/**
	 * Standard Logger erstellen, wie behandelt in der Vorlesung Software
	 * Enginiering 2
	 */
	public static Logger getDefaultLogger() {
		Logger mainLogger = Logger.getLogger("");
		mainLogger.setLevel(Level.FINEST);

		// Standardmässig wird alles auf die Konsole ausgegeben, wir wollen in
		// der Konsole alles ab Level Info sehen
		Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
		defaultHandlers[0].setLevel(Level.INFO);

		// Zusätzlicher Logger erstellen welcher in die LogDatei schreibt. Dort
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
		// wird das erste wieder überschrieben)
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
	
	/**
	 * Logt die übergebene Exception samt Stack Trace
	 * @param ex Ausnahme zum Loggen
	 */
	public static void LogException(Exception ex) {
	     ServiceLocator.getInstance().getLogger().log(Level.SEVERE, ex.getMessage(), ex);
	}
}
