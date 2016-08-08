package teamamused.client.gui;

import teamamused.client.libs.Client;
import teamamused.client.libs.IClientListener;
import teamamused.common.gui.AbstractController;

public class ByeController extends AbstractController<ByeModel, ByeView> implements IClientListener {

	public ByeController(ByeModel model, ByeView view) {
		super(model, view);

		// Beim Client registrieren
		Client.getInstance().registerGui(this);
		
	}
}
