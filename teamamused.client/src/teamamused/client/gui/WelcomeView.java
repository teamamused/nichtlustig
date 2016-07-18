package teamamused.client.gui;

import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import teamamused.common.ResourceLoader;
import teamamused.common.ServiceLocator;
import teamamused.common.gui.AbstractView;

public class WelcomeView extends AbstractView<WelcomeModel> {
	
	protected Button btnSingle;
	protected Button btnMulti;
	protected Button btnTrophy;
	protected Button btnRanking;

	public WelcomeView(Stage stage, WelcomeModel model) {
		super(stage, model);
	
	}

	@Override
	protected Scene createGUI() {

		// Create the labels
		Label labelWelcome = new Label("Herzlich Willkommen XYZ");
		Label labelPlay = new Label("Was möchtest du spielen?");
		
		// 
		btnSingle = new Button();
		btnSingle.setText("Single-Player");
		
		// 
		btnMulti = new Button();
		btnMulti.setText("Multi-Player");
		
		// 
		btnRanking = new Button();
		btnRanking.setText("Ranking");
		
		//
		//btnTrophy = new Button();
		//btnTrophy.setGraphic(new ImageView (iview3));
		
		ImageView iview = null;
		try {
			iview = new ImageView(ResourceLoader.getImage("Single-Player.jpg"));
			iview.setFitWidth(250);
			iview.setFitWidth(250);
			iview.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview2 = null;
		try {
			iview2 = new ImageView(ResourceLoader.getImage("Multi-Player.jpg"));
			iview2.setFitWidth(250);
			iview2.setFitWidth(250);
			iview2.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview3 = null;
		try {
			iview3 = new ImageView(ResourceLoader.getImage("Pokal.png"));
			iview3.setFitWidth(50);
			iview3.setFitWidth(50);
			iview3.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		ImageView iview4 = null;
		try {
			iview4 = new ImageView(ResourceLoader.getImage("Exit.png"));
			iview4.setFitWidth(50);
			iview4.setFitWidth(50);
			iview4.setPreserveRatio(true);
		} catch (FileNotFoundException e1) {
			ServiceLocator.getInstance().getLogger().severe(e1.toString());
		}
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_LEFT);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(50, 50, 50, 50));

		grid.add(labelWelcome, 0, 0);
		grid.add(labelPlay, 0, 2);
		grid.add(btnSingle, 0, 4);
		grid.add(btnMulti, 12, 4);
		grid.add(iview, 0, 8);
		grid.add(iview2, 12, 8);
		//grid.add(iview3, 16, 12);
		grid.add(btnRanking, 16, 12);
		grid.add(iview4, 16, 0);
		
		Scene scene = new Scene(grid, 800, 600);
		
		stage.setTitle("Nicht Lustig: Welcome");
		
		return scene;
	}

}
