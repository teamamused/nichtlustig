package teamamused.client.gui.splashscreen;

import teamamused.common.ServiceLocator;
import teamamused.common.Settings;
import javafx.concurrent.Task;
import teamamused.common.gui.AbstractModel;
import teamamused.common.gui.Translator;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class Splash_Model extends AbstractModel {
    ServiceLocator serviceLocator;

    public Splash_Model() {
        super();
    }

    // A task is a JavaFX class that implements Runnable. Tasks are designed to
    // have attached listeners, which we can use to monitor their progress.
    final Task<Void> initializer = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
            this.updateProgress(1,  6);

            // Create the service locator to hold our resources
            serviceLocator = ServiceLocator.getInstance();
            this.updateProgress(2,  6);

            // Settings File auslesen
            serviceLocator.setSettings(new Settings());
            this.updateProgress(4,  6);

            // Default Translator initialisieren
            String language = serviceLocator.getSettings().getSetting(Settings.Setting.Language);
    		ServiceLocator.getInstance().setTranslator(new Translator(language));
            this.updateProgress(5,  6);
            
            // ... more resources would go here...
            this.updateProgress(6,  6);

            return null;
        }
    };

    public void initialize() {
        new Thread(initializer).start();
    }
   
}
