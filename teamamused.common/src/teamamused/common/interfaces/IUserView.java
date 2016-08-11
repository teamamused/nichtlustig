package teamamused.common.interfaces;

import javafx.stage.Stage;

/**
 * Interface für Standard GUI Masken
 * Einheitliche Schnittstelle für das Starten und Stoppen
 * 
 * @author Daniel
 *
 */
public interface IUserView {

	/**
	 * Startet die Anzeige
	 */
	public void start();
	
	/**
	 * Schliest das GUI
	 */
	public void stop();
	
	/**
	 * Liefert die Prim Stage der view zurück
	 * @return Primary Stage der view
	 */
	public Stage getStage();
}
