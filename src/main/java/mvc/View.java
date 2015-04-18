package mvc;

import java.util.List;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import mvc.view.Buttons;
import mvc.view.MenuView;
import mvc.view.NaviView;

import common.Point;
import common.Segment;

public class View {
	private final Stage stage;
	private NaviView naviView;
	private MenuView menuView;

	public View(Stage stage) {
		this.stage = stage;
	}

	public void addBtnEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler, Buttons button) {
		switch (button) {
		case NEXT:
			menuView.addButtonEvent(eventType, eventHandler);
			break;
		case BACK:
		case ROLL:
		case RESET:
		case COMPUTE:
			naviView.addButtonEvent(eventType, eventHandler, button);
			break;
		}
	}

	public MenuView getMenuView() {
		return menuView;
	}

	public NaviView getNaviView() {
		return naviView;
	}

	public void init() {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.setTitle("navigator");
		this.makeMenuView();
		this.switchToMenu();

	}

	public void makeMenuView() {
		menuView = new MenuView(stage.getWidth(), stage.getHeight());
	}

	public void makeNaviView(List<Point> pointList, List<Segment> edgeList) {
		naviView = new NaviView(stage.getWidth(), stage.getHeight(),
				pointList.size());
		naviView.setPoints(pointList);
		naviView.setEdges(edgeList);
	}

	public void show() {
		stage.show();

	}

	public void switchToMenu() {
		stage.setScene(menuView.getScene());
		naviView = null;
	}

	public void switchToNavi() {
		stage.setScene(naviView.getScene());
		menuView = null;
	}

}
