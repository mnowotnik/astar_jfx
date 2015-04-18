package navigator;

import javafx.application.Application;
import javafx.stage.Stage;
import mvc.Controller;
import mvc.Model;
import mvc.View;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Model model = new Model();
			View view = new View(primaryStage);
			Controller controller = new Controller(model, view);
			view.init();
			controller.init();
			view.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
