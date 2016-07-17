package teamamused.common.dtos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import teamamused.common.LogHelper;

/**
 * 
 * Transport Objekt ist die Hauptklasse alle Objekte welche wir zwischen dem Client und Server austauschen.
 * 
 * Die Klasse basiert auf die im Unterricht bei Brad behandelten Message.java Klasse.
 * Die Objekte werden via Java Objektserialisierung versendet.
 * 
 * @author Daniel
 */

public class TransportObject implements Serializable{ 
	
	/**
	 * Folgende Arten von Objekten können übermittelt werden
	 * 
	 * @author Daniel
	 */
	public enum TransportType {
		Hallo,
		ChatMessage,
		RemoteProcedureCall,
		Answer,
		State,
		Goodbye
	}
	
	/**
	 * Die Versions Nummer dient zur sicherstellung dass Server und Client mit der gleichen Klassen Version arbeiten.
	 * Wird nach dem ersten Release eine TransportObject Klasse modifiziert, muss die muss die VersionsUID erhöht werden 
	 */
	private static final long serialVersionUID = 1;
	
	// Standard Attribute welche jede Nachricht entählt
    private int id;
    private long timestamp;
    private String client;
    private TransportType transportType;

    // Eindeutige Kennung der Nachricht
    private static int messageID = 0;

    /**
     * Interne ID der Nachricht
     * 
     * @return nächste ID
     */
    private static int nextMessageID() {
        return messageID++;
    }

    /**
     * Konstruktor um ein neues transportierbares Objekt zu erstellen
     * 
     * @param transportType Der Objekttyp
     */
    public TransportObject(TransportType transportType) {
        this.id = -1;
        this.transportType = transportType;
    }
    
    /**
     * Objekt serialisieren und über das Socket versenden
     * 
     * @param out OutputStream des Sockets
     */
    public void send(ObjectOutputStream out) {
    	// Set the message id before sending (if not already done)
    	if (this.id < 0) {
    		this.id = nextMessageID();
    	}
    	
    	// Set the timestamp
    	this.timestamp = System.currentTimeMillis();
    	
        try { // Ignore IO errors
        	out.writeObject(this);
            out.flush();
         } catch (Exception e) {
        	LogHelper.LogException(e);
        }
    }    

    /**
     * Factory method to construct a message-object from data received via socket
     * 
     * @param in Input Stream des Sockets
     * @return erhaltenes DTO Objekt
     */
    public static TransportObject receive(ObjectInputStream in) {
    	TransportObject dto = null;
		try {
			dto = (TransportObject) in.readObject();
		} catch (Exception e) {
        	LogHelper.LogException(e);
		}
        return dto;
    }
    
    @Override
    public String toString() {
		return "Message type " + this.getClass().getSimpleName() + ": ID = " + this.id + ", Timestamp=" + this.timestamp
				+ ", Client='" + this.client + "'";
    }

    /**
     * Id des Transportobjektes 
     * @return ling mit der Id
     */
	public int getId() {
		return id;
	}

	/**
	 * Zeitpunkt an welchem die Nachricht geschickt wurde
	 * @return ZeitStempel als Long
	 */
	public long getTimestamp() {
		return timestamp;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public TransportType getTransportType() {
		return this.transportType;
	}

}
