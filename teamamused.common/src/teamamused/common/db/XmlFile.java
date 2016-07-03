package teamamused.common.db;

import java.util.ArrayList;

/**
 * Klasse welche das XML File repräsentiert
 * Diese wird dann mit den Java.beans XML De/En Coder funktionalitäten automatisch XML-isiert 
 * 
 * @author Daniel
 */
public class XmlFile {
	/** Liste der gespielten Spiele */
	public ArrayList<GameInfo> gameInfos;
	/** Liste der Registrierten Spieler */
	public ArrayList<PlayerInfo> playerInfos;
	/** Liste der Spielerplatzierungen */
	public ArrayList<Ranking> rankings;
}