package mvc.view;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuView {

	private Text errorText;
	private Button nextBtn;
	private TextField rParamField, nParamField;
	private final Scene scene;

	public MenuView(double stageWidth, double stageHeight) {
		scene = makeScene(stageWidth, stageHeight);
	}

	public void addButtonEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		nextBtn.addEventHandler(eventType, eventHandler);
	}

	public String getNValue() {
		return this.nParamField.getText();
	}

	public String getRValue() {
		return this.rParamField.getText();
	}

	public Scene getScene() {
		return scene;
	}

	public void showError() {
		errorText.setText("Enter valid number.");

	}

	private Scene makeScene(double stageWidth, double stageHeight) {
		final VBox mainPanel = new VBox(20);
		mainPanel.setPrefSize(stageWidth, stageHeight);
		mainPanel.setAlignment(Pos.CENTER);
		GridPane grid = new GridPane();

		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Navigator");
		scenetitle.setId("welcome-text");
		mainPanel.getChildren().add(scenetitle);

		Label nParamText = new Label("Parameter");
		grid.add(nParamText, 0, 0);

		Label paramN = new Label("N");
		paramN.getStyleClass().add("parameters");
		grid.add(paramN, 1, 0);

		nParamField = new TextField();
		nParamField.setMaxWidth(100);
		grid.add(nParamField, 2, 0);

		Label rParamText = new Label("Parameter");
		grid.add(rParamText, 0, 1);

		Label paramR = new Label("R");
		paramR.getStyleClass().add("parameters");
		grid.add(paramR, 1, 1);

		rParamField = new TextField();
		rParamField.setMaxWidth(100);
		grid.add(rParamField, 2, 1);

		nextBtn = new Button("Next");
		HBox hbBtn = new HBox(10);

		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(nextBtn);
		grid.add(hbBtn, 2, 4);
		mainPanel.getChildren().add(grid);
		errorText = new Text();
		errorText.setId("error");
		mainPanel.getChildren().add(errorText);

		grid.getStyleClass().add("contrastPanel");

		String menuStyle = MenuView.class.getResource("menu.css")
				.toExternalForm();
		String commonStyle = MenuView.class.getResource("common.css")
				.toExternalForm();

		Scene scene = new Scene(mainPanel, stageWidth, stageHeight);

		scene.getStylesheets().add(menuStyle);
		scene.getStylesheets().add(commonStyle);

		return scene;
	}

}
