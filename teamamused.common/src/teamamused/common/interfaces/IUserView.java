package teamamused.common.interfaces;

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
}
