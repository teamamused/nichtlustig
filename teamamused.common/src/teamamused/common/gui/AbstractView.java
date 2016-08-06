package teamamused.common.gui;

import teamamused.common.LogHelper;
import teamamused.common.ResourceLoader;
import teamamused.common.interfaces.IUserView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public abstract class AbstractView<M extends AbstractModel> implements IUserView {
    protected Stage stage;
    protected Scene scene;
    protected M model;
    
    /**
     * Set any options for the stage in the subclass constructor
     * 
     * @param stage Stage Objekt in welchem die Scene dargestellt werden soll
     * @param model Datenmodel
     */
    protected AbstractView(Stage stage, M model) {
        this.stage = stage;
        this.model = model;
        this.initView();
        
        Scene scene = createGUI(); // Create all controls within "root"
        stage.setScene(scene);

        Image icon = this.getStateImage();
        if (icon != null) {
        	stage.getIcons().add(icon);
        }
    }

    protected void initView() {}
    
    protected Image getStateImage() {
		try {
			return ResourceLoader.getImage("Lemming.png");
		} catch (Exception ex) {
			LogHelper.LogException(ex);
		}
		return null;
    }

    protected abstract Scene createGUI();

    /**
     * Display the view
     */
    public void start() {
        stage.show();
    }
    
    /**
     * Hide the view
     */
    public void stop() {
        stage.hide();
    }
    
    /**
     * Getter for the stage, so that the controller can access window events
     * @return aktives Stage Objekt
     */
    public Stage getStage() {
        return stage;
    }
    
}
