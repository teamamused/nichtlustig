package teamamused.common.models.cards;

/**
 * Zentralle enum mit den Grunddaten der Spielkarten.
 * Sie beinhaltet Name und Bilder aller Karten sowie deren Platzierung auf dem Spielfeld.
 * @author Daniel Hirsbrunner
 *
 */
public enum GameCard {

	SK_Ente(1, "Spezialkarte Ente", "Ente.png", "Empty.png"),
	SK_UFO(2, "Spezialkarte UFO", "UFO.png", "Empty.png"),
	SK_Clown(3, "Spezialkarte Clown", "Clown.png", "Empty.png"),
	SK_RoboterNF700(4, "Spezialkarte NF-700 Roboter", "Roboter.png", "Empty.png"),
	SK_Zeitmaschine(5, "Spezialkarte Zeitmaschine", "Zeitmaschine.png", "Empty.png"),
	SK_KillerVirus(6, "Spezialkarte Killervirus", "Killervirus.png", "Empty.png"),

	Tod_Pudel(1, "Todeskarte Pudel", "Tod0Vorne.png", "Tod0Hinten.png"),
	Tod_Eins(2, "Todeskarte Eins", "Tod1Vorne.png", "Tod1Hinten.png"),
	Tod_Zwei(3, "Todeskarte Zwei", "Tod2Vorne.png", "Tod2Hinten.png"),
	Tod_Drei(4, "Todeskarte Drei", "Tod3Vorne.png", "Tod3Hinten.png"),
	Tod_Vier(5, "Todeskarte Vier", "Tod4Vorne.png", "Tod4Hinten.png"),
	Tod_Fuenf(6, "Todeskarte Fünf", "Tod5Vorne.png", "Tod5Hinten.png"),

	ZK_Riebmann1(1, "Riebmann Eins", "Riebmann1Vorne.png", "Riebmann1Hinten.png"),
	ZK_Riebmann2(2, "Riebmann Zwei", "Riebmann2Vorne.png", "Riebmann2Hinten.png"),
	ZK_Riebmann3(3, "Riebmann Drei", "Riebmann3Vorne.png", "Riebmann3Hinten.png"),
	ZK_Riebmann4(4, "Riebmann Vier", "Riebmann4Vorne.png", "Riebmann4Hinten.png"),
	ZK_Riebmann5(5, "Riebmann Fünf", "Riebmann5Vorne.png", "Riebmann5Hinten.png"),
	
	ZK_Yeti1(6, "Yeti Eins", "Yeti1Vorne.png", "Yeti1Hinten.png"),
	ZK_Yeti2(7, "Yeti Zwei", "Yeti2Vorne.png", "Yeti2Hinten.png"),
	ZK_Yeti3(8, "Yeti Drei", "Yeti3Vorne.png", "Yeti3Hinten.png"),
	ZK_Yeti4(9, "Yeti Vier", "Yeti4Vorne.png", "Yeti4Hinten.png"),
	ZK_Yeti5(10, "Yeti Fünf", "Yeti5Vorne.png", "Yeti5Hinten.png"),
	
	ZK_Lemming1(11, "Lemming Eins", "Lemming1Vorne.png", "Lemming1Hinten.png"),
	ZK_Lemming2(12, "Lemming Zwei", "Lemming2Vorne.png", "Lemming2Hinten.png"),
	ZK_Lemming3(13, "Lemming Drei", "Lemming3Vorne.png", "Lemming3Hinten.png"),
	ZK_Lemming4(14, "Lemming Vier", "Lemming4Vorne.png", "Lemming4Hinten.png"),
	ZK_Lemming5(15, "Lemming Fünf", "Lemming5Vorne.png", "Lemming5Hinten.png"),
	
	ZK_Professoren1(16, "Professoren Eins", "Professoren1Vorne.png", "Professoren1Hinten.png"),
	ZK_Professoren2(17, "Professoren Zwei", "Professoren2Vorne.png", "Professoren2Hinten.png"),
	ZK_Professoren3(18, "Professoren Drei", "Professoren3Vorne.png", "Professoren3Hinten.png"),
	ZK_Professoren4(19, "Professoren Vier", "Professoren4Vorne.png", "Professoren4Hinten.png"),
	ZK_Professoren5(20, "Professoren Fünf", "Professoren5Vorne.png", "Professoren5Hinten.png"),
	
	ZK_Dinosaurier1(21, "Dinosaurier Eins", "Dino1Vorne.png", "Dino1Hinten.png"),
	ZK_Dinosaurier2(22, "Dinosaurier Zwei", "Dino2Vorne.png", "Dino2Hinten.png"),
	ZK_Dinosaurier3(23, "Dinosaurier Drei", "Dino3Vorne.png", "Dino3Hinten.png"),
	ZK_Dinosaurier4(24, "Dinosaurier Vier", "Dino4Vorne.png", "Dino4Hinten.png"),
	ZK_Dinosaurier5(25, "Dinosaurier Fünf", "Dino5Vorne.png", "Dino5Hinten.png");

	int cardNumber;
	String cardName;
	String foregroundImageName;
	String backgroundImageName;
	
	GameCard(int number, String name, String foregroundImageName, String backgroundImageName) {
		this.cardNumber = number;
		this.cardName = name;
		this.foregroundImageName = foregroundImageName;
		this.backgroundImageName = backgroundImageName;
	}
	
	/**
	 * gibt die Kartennummer zurück
	 * @return Karten Nummer
	 */
	public int getCardNumber() {
		return this.cardNumber;
	}
	
	/**
	 * Gibt den Namen des Vordergrund Bildes zurück
	 * @return Vordergrund Bild Name
	 */
	public String getForgroundImageName() {
		return this.foregroundImageName;
	}
	
	/**
	 * Gibt den Namen des Hintergrundbildes zurück
	 * @return Hintergrund Bildname
	 */
	public String getBackgroundImageName() {
		return this.backgroundImageName;
	}
	
	/**
	 * Gibt den Namen der Karte zurück
	 * @return Kartenbezeichnung
	 */
	@Override
	public String toString() {
		return this.cardName;
	}
	
	public boolean isRiebmann() {
		return this == ZK_Riebmann1 || this == ZK_Riebmann2 || this == ZK_Riebmann3 || this == ZK_Riebmann4 || this == ZK_Riebmann5;
	}
	
	public boolean isYeti() {
		return this == ZK_Yeti1 || this == ZK_Yeti2 || this == ZK_Yeti3 || this == ZK_Yeti4 || this == ZK_Yeti5;
	}
	
	public boolean isLemming() {
		return this == ZK_Lemming1 || this == ZK_Lemming2 || this == ZK_Lemming3 || this == ZK_Lemming4 || this == ZK_Lemming5;
	}
	
	public boolean isProffessoren() {
		return this == ZK_Professoren1 || this == ZK_Professoren2 || this == ZK_Professoren3 || this == ZK_Professoren4 || this == ZK_Professoren5;
	}
	
	public boolean isDino() {
		return this == ZK_Dinosaurier1 || this == ZK_Dinosaurier2 || this == ZK_Dinosaurier3 || this == ZK_Dinosaurier4 || this == ZK_Dinosaurier5;
	}
}
