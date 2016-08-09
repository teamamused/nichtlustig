package teamamused.common;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import teamamused.common.dtos.BeanGameBoard;

public class LogHelper {

	/**
	 * Standard Logger erstellen, wie behandelt in der Vorlesung Software
	 * Enginiering 2
	 * @return Standard Logger Objekt
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
		// limit: Maximale Dateigrösse in Bytes
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
		ex.printStackTrace();
	    ServiceLocator.getInstance().getLogger().log(Level.SEVERE, ex.toString(), ex);
	}
	
	/**
	 * Logt alle wichtigen Eigenschaften des Spielbrettes als info
	 * @param kennung Prefix für die Erkennung des Boardes im Log
	 * @param board Spielbread
	 */
	public static void LogGameBoard(String kennung, BeanGameBoard board) {
		Logger log = ServiceLocator.getInstance().getLogger();
		log.info("Spielbrett " + kennung + " " + board.toString());
		log.info("Anzahl Zielkarten " + board.targetCards.size());
		log.info("Anzahl Spezialkarten " + board.specialCards.size());
		log.info("Anzahl Todeskarten " + board.deadCards.size());
		log.info("Anzahl Würfel " + board.cubeValues.length);
	}
}
