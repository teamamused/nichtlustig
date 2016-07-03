package teamamused.common.dtos;

/**
 * 
 * Mit dieser Klasse kann eine Prozedure auf einem entfernten Host aufgerufen werden.
 * Diese Klasse kann hierzu bei der Kommunikation Server to Client und Client to Server eingesetzt werden.
 * 
 * @author Daniel
 *
 */
public class TransportableProcedureCall extends TransportObject {
	/**
	 * Versionsnummer des Transport Objektes
	 */
	private static final long serialVersionUID = 1;
	
	/**
	 * Die verschiedenen Remote Prozeduren welche zur Verfügung stehen
	 * 
	 * @author Daniel
	 *
	 */
	public enum RemoteProcedure {
		// Prozeduren welche vom Client aufgerufen werden
		StartGame,
		RollDices,
		FixDices,
		GameSpecialCard,
		CardsChosen,
		CreatePlayer,
		ConnectPlayer,
		GetTopRanking,
		
		// Prozeduren welche vom Server aufgerufen werden
		/**
		 * Zeigt dem Client an das der Server etwas geändert hat. 
		 * Parameter: String mit der Nachricht
		*/
		ShowGameMove,
		/**
		 * Forciert den Client das Spielbrett neu anzuzeigen. 
		 * Parameter: Gameboard
		*/
		UpdateGameBoard,
		/**
		 * Aktiviert den nächsten Spieler. 
		 * Parameter: IPlayer neuer aktiver Spieler
		*/
		ChangeActivePlayer,
		/**
		 * Bietet dem Spieler eine auswahl an möglichen Optionen welche Karten er wählen kann
		 * Parameter: Hashtable &lt; Integer, List &lt; ITargetCards &gt; &gt; - Key ist die Nummer der Option, Value die enthaltenen Karten der Option
		*/
		ChooseCards,
		/**
		 * Spiel ist beendet, schickt den Clients die Platzierungen
		 * Parameter: Ranking[] mit den Platzierungen
		*/
		FinishGame
	}
	
	RemoteProcedure procedure;
	Object[] arguments;

	/**
	 * Konstruktor zur Initialiierung einen neuen, Parameter lossen, Remote Calls
	 * 
	 * @param procedure	RemoteProcedure Typ
	 * */
	public TransportableProcedureCall(RemoteProcedure procedure) {
		this(procedure, new Object[0]);
	}
	/**
	 * Konstruktor zur Initialiierung einen neuen Remote Calls
	 * 
	 * @param procedure	RemoteProcedure Typ
	 * @param arguments Parameter für die Prozedur
	 */
	public TransportableProcedureCall(RemoteProcedure procedure, Object[] arguments) {
		super(TransportType.RemoteProcedureCall);
		this.procedure = procedure;
		this.arguments = arguments;
	}

	/**
	 * Getter für die Prozedur Parameter
	 * @return RPC Typ des TransportableProcedureCall's
	 */
	public RemoteProcedure getProcedure() {
		return procedure;
	}

	/**
	 * Getter für die Prozedur Parameter
	 * @return Objekt Array desen Einträge zum erwarteten Typ gecastet werden können
	 */
	public Object[] getArguments() {
		return arguments;
	}
}
