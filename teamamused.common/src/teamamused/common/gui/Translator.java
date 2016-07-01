package teamamused.common.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.sun.javafx.logging.Logger;

import teamamused.common.ServiceLocator;

//Gemäss Unterlagen aus dem Modul Software Engineering 2 von Bradley Richards
public class Translator {
	private static Translator translator = null;
	private Locale locale;
	private ResourceBundle resourceBundle;
	private Logger logger = new Logger();
	
	//Konstruktor
	private Translator(){
	}
	
	//Factory-Pattern, damit nur eine Translator-Instanz existiert
	public static Translator getTranslator(){
		if (translator == null){
			return translator = new Translator();
		} else {
			return translator;
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
