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
		UpdateGameBoard,
		ChangeActivePlayer,
		ChooseCards,
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
