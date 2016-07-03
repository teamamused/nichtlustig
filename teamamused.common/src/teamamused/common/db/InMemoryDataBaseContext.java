package teamamused.common.db;


/**
 * 
 * Datenbank kontext für das Speichern der Daten in Memory
 * Für Unit Testing
 * 
 * @author Daniel
 *
 */
public class InMemoryDataBaseContext extends DataBaseContext {
	/**
	 * Alle Listen initialisieren, keine Daten werden geladen
	 * @return Immer true 
	 */
	@Override
	public boolean loadContext() {
		this.initEmptyLists();
		return true;
	}
	/**
	 * In Memory ist es automatisch schon gespeichert ;)
	 * Nichts wird gemacht.
	 * @return Immer true 
	 */
	@Override
	public boolean saveContext() {
		return true;
	}
}
