/**
 * 
 * In diesem Package befinden sich alle relevanten Objekte für die Daten Speicherung und Verwaltung
 * Es gibt 3 Hautpentitäten zum verwalten:
 *  - PlayerInfo
 *  - GameInfo
 *  - Ranking
 *  
 *  Zu jeder Entität wird ein Repository zur Verfügung gestelt. In diesem sind die zentralen Funktionen welche sich auf diese Entitäten beziehen.
 *  
 *  Für die Datenspeicherung stehen momentan 2 Datenbank Kontexte (IDataBaseContext) zur verfügung:
 *  - InMemoryDataBaseContext
 *  - XMLDataBaseContext
 *  Diese definieren das Laden und zurückspeichern der Daten.
 *  Beide erben von der Klasse DataBaseContext welche das Interface IDataBaseContext implementiert und die Grundfunktionalitäten zur verfügung stellt.
 *  
 * @author Daniel
 *
 */
package teamamused.common.db;