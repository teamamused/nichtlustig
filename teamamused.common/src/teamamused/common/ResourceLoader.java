package teamamused.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;

import teamamused.common.Settings.Setting;
import javafx.scene.image.Image;

/**
 * 
 * Klasse welche sich um das Laden externer Resourcen kümmert. Beispiel Bilder
 * 
 * @author Daniel Hirsbrunner
 *
 */
public class ResourceLoader {

	/**
	 * Lädt ein Bild vom Dateisystem
	 * 
	 * @param imageName
	 *            Name des Bildes (samt endung)
	 * @return Image Objekt des Bildes
	 * @throws FileNotFoundException
	 *             Exception falls Datei nicht gefunden
	 */
	public static Image getImage(String imageName) throws FileNotFoundException {
		String imagePath = ServiceLocator.getInstance().getSettings().getSetting(Setting.ImagePath);
		File f = new File(imagePath+ "/" + imageName);
		if (!f.exists()) {
			ServiceLocator.getInstance().getLogger().warning(f.getAbsolutePath() + " - Konnte nicht gefunden werden");
			throw new FileNotFoundException("Das Bild " + imagePath+ "/" + imageName + " konnte nicht gefunden werden!");
		}
		try {
			return new Image("file:" + f.getAbsolutePath());
		} catch (Exception e) {
			ServiceLocator.getInstance().getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
