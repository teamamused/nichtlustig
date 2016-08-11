package teamamused.server.gui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractController;
import teamamused.server.Server;
import teamamused.server.lib.GameManipulator;

/**
 * Diese Controller-Klasse nimmt die Benutzerinteraktionen des Server-GUI's
 * entgegen.
 * 
 * @author Michelle
 *
 */
public class ServerController extends AbstractController<ServerModel, ServerView> {

	private static final Logger LOG = ServiceLocator.getInstance().getLogger();

	public ServerController(ServerModel model, ServerView view) {
		super(model, view);

		// Wird auf den Restart-Button geklickt, wird die Methode für den
		// Neustart des Servers aufgerufen.
		view.restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				view.loggingTxtArea.clear();
				LOG.info("Server wird neu gestartet");
				Server.resetServer();
			}
		});

		// Wird auf den Vorantreiben-Button geklickt, wird das Spiel durch den
		// Aufruf der entsprechenden Methode vorangetrieben.
		view.deployCardsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GameManipulator.deployCardsRandomly();
			}
		});
	}
}
