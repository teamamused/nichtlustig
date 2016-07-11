package teamamused.common.dtos;

/**
 * 
 * Die TransportableAnswer wird als Antwort auf einen RemoteProcedureCall mit einem Return value erstellt.
 * 
 * @author Daniel
 *
 */
public class TransportableAnswer extends TransportObject {

	/**
	 * Versionsnummer des Transport Objektes
	 */
	private static final long serialVersionUID = 1;
	
	private TransportableProcedureCall originalCall;
	private boolean isOK;
	private Object returnValue;

	/**
	 * Instanziert eine neue Transportierbare Antwort zum übergebenen Aufruf
	 * @param originalCall Ursprünglicher Aufruf
	 * @param isOK War die Anfrage Fehlerfrei
	 * @param returnValue Returnvalue zur Anfrage
	 */
	public TransportableAnswer(TransportableProcedureCall originalCall, boolean isOK, Object returnValue) {
		super(TransportType.Answer);
		this.originalCall = originalCall;
		this.isOK = isOK;
		this.returnValue = returnValue;
	}

	/**
	 * @return the originalCall
	 */
	public TransportableProcedureCall getOriginalCall() {
		return originalCall;
	}

	/**
	 * @param originalCall the originalCall to set
	 */
	public void setOriginalCall(TransportableProcedureCall originalCall) {
		this.originalCall = originalCall;
	}

	/**
	 * @return the isOK
	 */
	public boolean isOK() {
		return isOK;
	}

	/**
	 * @param isOK the isOK to set
	 */
	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}

	/**
	 * @return the returnValue
	 */
	public Object getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue the returnValue to set
	 */
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	
}
