package teamamused.common.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

//Gemäss Unterlagen aus dem Modul Software Engineering 2 von Bradley Richards
public class Translator {
	private static Translator instance = null;
	private Locale locale;
	private ResourceBundle resourceBundle;
	
	//Konstruktor
	private Translator(){
	}
	
	// Singleton-Pattern, damit nur eine Translator-Instanz existiert
	public static Translator getInstance(){
		if (instance == null){
			return instance = new Translator();
		} else {
			return instance;
		}
	}
	
	public Locale getCurrentLocale(){
		return locale;
	}
	
	public String getString(String key){
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e){
			System.out.println("Wert fehlt zu: " + key);
			return "";
		}
	}
	
	
}
