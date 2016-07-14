package teamamused.common.db;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import teamamused.common.ServiceLocator;
import teamamused.common.Settings;

/**
 * 
 * Datenbank kontext für das Speichern der Daten in einem XML File
 * 
 * @author Daniel
 *
 */
public class XmlDataBaseContext extends AbstractDataBaseContext {

	/**
	 * XML Datei öffnen falls vorhanden und die Daten importierten.
	 * Falls XML Datei nicht vorhanden oder im falschen Format werden leere Listen initialisiert
	 * @return
	 * 	False falls Datei vorhanden aber Fehler beim Mappen, True falls erfolgreich geladen oder keine DB vorhanden
	 * 
	 */
	@Override
	public boolean loadContext() {
		// Pfad zur DB auslesen
		String dbFileName = ServiceLocator.getInstance().getSettings().getSetting(Settings.Setting.DBFileName);
		File f = new File(dbFileName);
		if (f.exists()) {
			try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(dbFileName)))) {
				// decoder = new XMLDecoder(new BufferedInputStream(new
				// FileInputStream(dbFileName)));
				XmlFile file = (XmlFile) decoder.readObject();
				this.playerInfos = file.playerInfos;
				this.gameInfos =  file.gameInfos;
				this.rankings = file.rankings;

			} catch (Exception e) {
				ServiceLocator.getInstance().getLogger().severe(e.toString() + " - " + e.getMessage());
				this.initEmptyLists();
				return false;
			}
		}
		// Listen initialisieren, falls noch kein DB File vorhanden ist.
		this.initEmptyLists();
		// auch wenn kein DB File vorhanden ist war das laden erfolgreich, dann
		// gabs einfach nichts zum laden
		return true;
	}
	
	/**
	 * Speichern der Daten  in die XML Datei
	 * @return
	 * 	False falls die Datei nicht geschrieben werden konnte, true bei Erfolg.
	 * 
	 */
	@Override
	public boolean saveContext() {
		String dbFileName = ServiceLocator.getInstance().getSettings().getSetting(Settings.Setting.DBFileName);

		ServiceLocator.getInstance().getLogger().info("Speichere Daten unter: " + dbFileName);
		try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(dbFileName)))) {
			XmlFile file = new XmlFile();
			file.rankings = this.getRankings();
			file.playerInfos = this.getPlayerInfos();
			file.gameInfos = this.getGameInfos();
			encoder.writeObject(file);
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().severe(e.toString() + " - " + e.getMessage());
			return false;
		}
		return true;
	}
}
