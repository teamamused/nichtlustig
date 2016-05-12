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

	ZK_Riebmann1(1, "Riebmann Eins", "Empty.png", "Empty.png"),
	ZK_Riebmann2(2, "Riebmann Zwei", "Empty.png", "Empty.png"),
	ZK_Riebmann3(3, "Riebmann Drei", "Empty.png", "Empty.png"),
	ZK_Riebmann4(4, "Riebmann Vier", "Empty.png", "Empty.png"),
	ZK_Riebmann5(5, "Riebmann Fünf", "Empty.png", "Empty.png"),
	
	ZK_Yeti1(6, "Yeti Eins", "Empty.png", "Empty.png"),
	ZK_Yeti2(7, "Yeti Zwei", "Empty.png", "Empty.png"),
	ZK_Yeti3(8, "Yeti Drei", "Empty.png", "Empty.png"),
	ZK_Yeti4(9, "Yeti Vier", "Empty.png", "Empty.png"),
	ZK_Yeti5(10, "Yeti Fünf", "Empty.png", "Empty.png"),
	
	ZK_Lemming1(11, "Lemming Eins", "Empty.png", "Empty.png"),
	ZK_Lemming2(12, "Lemming Zwei", "Empty.png", "Empty.png"),
	ZK_Lemming3(13, "Lemming Drei", "Empty.png", "Empty.png"),
	ZK_Lemming4(14, "Lemming Vier", "Empty.png", "Empty.png"),
	ZK_Lemming5(15, "Lemming Fünf", "Empty.png", "Empty.png"),
	
	ZK_Professoren1(16, "Professoren Eins", "Empty.png", "Empty.png"),
	ZK_Professoren2(17, "Professoren Zwei", "Empty.png", "Empty.png"),
	ZK_Professoren3(18, "Professoren Drei", "Empty.png", "Empty.png"),
	ZK_Professoren4(19, "Professoren Vier", "Empty.png", "Empty.png"),
	ZK_Professoren5(20, "Professoren Fünf", "Empty.png", "Empty.png"),
	
	ZK_Dinosaurier1(21, "Dinosaurier Eins", "Empty.png", "Empty.png"),
	ZK_Dinosaurier2(22, "Dinosaurier Zwei", "Empty.png", "Empty.png"),
	ZK_Dinosaurier3(23, "Dinosaurier Drei", "Empty.png", "Empty.png"),
	ZK_Dinosaurier4(24, "Dinosaurier Vier", "Empty.png", "Empty.png"),
	ZK_Dinosaurier5(25, "Dinosaurier Fünf", "Empty.png", "Empty.png");

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
	
	public int getCardNumber() {
		return this.cardNumber;
	}

	public String getForgroundImageName() {
		return this.foregroundImageName;
	}
	
	public String getBackgroundImageName() {
		return this.backgroundImageName;
	}
	
	public String toString() {
		return this.cardName;
	}
}
