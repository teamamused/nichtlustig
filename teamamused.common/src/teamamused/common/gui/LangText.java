package teamamused.common.gui;

import javafx.scene.control.Label;

/**
 * Enumeration mit den Sprachresourcen
 * Um nicht überall die Schreibfehler anfälligen Einstellungs konstanten verwenden zu müssen, wurde diese Enumeratione eingefügt.
 * Sie dient als Verzeich sämtlicher im Programm vorhanden Sprachtexte
 * 
 * @author Daniel
 */
public enum LangText {
	
	// Generell gültige Texte
	Sprache("sprache"),
	
	// LogIn View
	LogInTitel ("login.titel"),
	LogInCopyright ("login.labelCopyright"),
	LogInConnect ("login.labelConnect"),
	
	// Register View
	RegisterTitel ("register.titel"),
	RegisterHello ("register.labelRegister"),
	RegisterCopyright ("register.labelCopyright"),
	RegisterHere ("register.labelRegisterHere"),
	RegisterUser ("register.labelRegUser"),
	RegisterPassword ("register.labelRegPassword"),
	RegisterPassword2 ("register.labelRegPassword2"),
	
	// Ranking View
	RankingTitel ("ranking.titel"),
	RankingText ("ranking.labelRanking");
	
	String resourceName;
	
	LangText(String rn) {
		this.resourceName = rn;
	}
	
	public String toString(){
		return this.resourceName;
	}
}