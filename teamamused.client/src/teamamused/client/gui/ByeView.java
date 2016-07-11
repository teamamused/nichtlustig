package teamamused.client.gui;

import teamamused.common.gui.AbstractView;

public class ByeView extends AbstractView<ByeModel> {

	public ByeView(Stage stage, LogInModel model) {
		super(stage, model);
	}

	protected Scene createGUI() {

		// Create the labels
		Label labelUser = new Label("Benutzername");
		Label labelPassword = new Label("Passwort");
		Label labelNeu = new Label("Neu bei uns?");

		textUser = new TextField();
		textPassword = new TextField();
		
		// 
		btnLogin = new Button();
		btnLogin.setText("Login");

		// 
		linkReg = new Hyperlink();
		linkReg.setText("Registrieren?");

		ChoiceBox cbLang = new ChoiceBox();
		cbLang.setItems(FXCollections.observableArrayList("Deutsch", "English"));
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Nicht-Lustig.jpg"));
			iview.setFitWidth(200);
			iview.setFitWidth(200);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_RIGHT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(100, 100, 100, 100));

		grid.add(labelUser, 2, 0);
		grid.add(textUser, 2, 1);
		grid.add(labelPassword, 2, 2);
		grid.add(textPassword, 2, 3);
		grid.add(btnLogin, 2, 4);
		grid.add(labelNeu, 2, 8);
		grid.add(linkReg, 2, 9);
		grid.add(cbLang, 2, 10);
		grid.add(iview, 0, 0);

		// Add the layout pane to a scene
		Scene scene = new Scene(grid, 800, 600);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		return scene;
	}

	public void start() {
		stage.show();
	}

	public void stop() {
		stage.hide();
	}

}
