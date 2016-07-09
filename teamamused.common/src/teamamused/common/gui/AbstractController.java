package teamamused.common.gui;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public abstract class AbstractController<M extends AbstractModel, V extends AbstractView<M>> {
    protected M model;
    protected V view;
    
    protected AbstractController(M model, V view) {
        this.model = model;
        this.view = view;
    }
}
