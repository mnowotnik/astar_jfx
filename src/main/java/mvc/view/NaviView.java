package mvc.view;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import util.Geometry;

import common.Point;
import common.Segment;

public class NaviView {

	private final Scene scene;
	private BorderPane root;
	private double stageWidth, stageHeight;
	private GraphPane graphPane;
	private ScrollPane graphWindow;
	private Button backBtn, rollBtn, resetBtn, computeBtn;
	private Popup popup;
	private Label popupLabel;
	private DecimalFormat popupFormat;
	private final static double POP_X_OFF = 20, POP_Y_OFF = 20, POP_D_OFF = 60;

	public NaviView(double stageWidth, double stageHeight, int nodes) {

		this.stageHeight = stageHeight;
		this.stageWidth = stageWidth;

		double boxWidth, boxHeight, boxScaleFactor;
		boxWidth = boxHeight = 0.8 * stageHeight;
		boolean scrollable = false;
		if (nodes > 100) {
			boxScaleFactor = boxHeight / 100;
			boxWidth = boxHeight = nodes * boxScaleFactor;
			scrollable = true;
		}
		scene = makeScene(scrollable, boxWidth, boxHeight);

		popup = new Popup(); // popup info
		popupLabel = new Label();
		popupLabel.setId("label");
		popup.setAutoFix(false);
		popup.setHideOnEscape(true);
		popup.getContent().add(popupLabel);
		popupFormat = new DecimalFormat("#0.000");
		popupFormat.setRoundingMode(RoundingMode.HALF_UP);

	}

	public void showOptimalRoute(List<Point> optimalRoute) {
		graphPane.showOptimalRoute(optimalRoute);
	}

	public void addButtonEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler, Buttons button) {
		Button b;
		switch (button) {
		case BACK:
			b = backBtn;
			break;
		case ROLL:
			b = rollBtn;
			break;
		case RESET:
			b = resetBtn;
			break;
		case COMPUTE:
			b = computeBtn;
			break;
		default:
			b = null;
			break;
		}
		if (b != null) {
			b.addEventHandler(eventType, eventHandler);
		} else {
			System.err.println("No such button. NaviView.#addButtonEvent.");
		}

	}

	public void addEdgeEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		graphPane.addEdgeEvent(eventType, eventHandler);

	}

	public void addNodeExitEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		graphPane.addEdgeEvent(eventType, eventHandler);
		graphPane.addPointEvent(eventType, eventHandler);
	}

	public void addPointEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		graphPane.addPointEvent(eventType, eventHandler);
	}

	public void clearGraph() {
		graphPane.clearGraph();
	}

	public boolean clickPoint(VisiblePoint vPoint) {
		return graphPane.clickPoint(vPoint);
	}

	public List<Point> getClickedPoints() {
		return graphPane.getClickedPoints();
	}

	public Scene getScene() {
		return scene;
	}

	public void hidePopup() {
		popup.hide();
		popupLabel.setText("");
	}

	public void removePointEvent(EventType<MouseEvent> eventType,
			EventHandler<MouseEvent> eventHandler) {
		graphPane.removePointEvent(eventType, eventHandler);
	}

	public void resetGraph() {
		graphPane.reset();
	}

	public void setEdges(List<Segment> edgeList) {
		graphPane.setEdges(edgeList);
		graphPane.pointsToFront();

	}

	public void setPoints(List<Point> pointList) {
		graphPane.setPoints(pointList);
	}

	public void showPopup(VisibleEdge visE) {

		String distance = popupFormat.format(visE.getRealEdge().getDistance());
		popupLabel.setText(distance);
		Point gA = visE.getPointA();
		Point gB = visE.getPointB();
		Point A = new Point(gA.getX() + getGraphPaneOffsetX(), gA.getY()
				+ getGraphPaneOffsetY());
		Point B = new Point(gB.getX() + getGraphPaneOffsetX(), gB.getY()
				+ getGraphPaneOffsetY());
		double x = (A.getX() + B.getX()) / 2;
		double y = (A.getY() + B.getY()) / 2;

		Segment ordered = Geometry.order(A, B);
		double sin = Geometry.calculateSin(ordered.getA(), ordered.getB());
		double cos = Geometry.calculateCos(ordered.getA(), ordered.getB());
		double dx = sin * POP_D_OFF;
		double dy = -cos * POP_D_OFF;

		if (ordered.getA().getY() > ordered.getB().getY()) {
			dx = 1.5 * dx;
			dy = 1.5 * dy;
		}
		x += dx;
		y += dy;

		popup.show(graphPane, x, y);

	}

	public void showPopup(VisiblePoint visP) {
		double xR = visP.getPoint().getX();
		double yR = visP.getPoint().getY();
		String xStr = popupFormat.format(xR);
		String yStr = popupFormat.format(yR);
		popupLabel.setText("(" + xStr + " " + yStr + ")");
		double xOffset = this.getGraphPaneOffsetX();
		double yOffset = this.getGraphPaneOffsetY();
		double x = visP.getCenter().getX();
		double y = visP.getCenter().getY();
		x += xOffset + POP_X_OFF;
		y += yOffset - 2 * POP_Y_OFF;
		popup.show(graphPane, x, y);

	}

	private double getGraphPaneOffsetX() {
		return (scene.getWidth() - graphPane.getWidth()) / 2;
	}

	private double getGraphPaneOffsetY() {
		return (scene.getHeight() - graphPane.getHeight()) / 2;
	}

	private Scene makeScene(boolean scrollable, double boxWidth,
			double boxHeight) {
		root = new BorderPane();
		double STRANGE_OFFSET = 30;
		root.setPrefSize(stageWidth, stageHeight - STRANGE_OFFSET);
		graphPane = new GraphPane(boxWidth, boxHeight);
		graphPane.setMaxSize(boxWidth, boxHeight);
		graphPane.setId("graphPane");
		graphPane.getChildren().add(new Ruler(boxWidth, boxHeight, 15, 10));
		if (scrollable == false) {
			root.setCenter(graphPane);

		} else {
			graphWindow = new ScrollPane();
			graphWindow.setMaxSize(stageHeight * 0.8, stageHeight * 0.8);
			graphWindow.setPannable(true);

			BorderPane outerBox = new BorderPane();
			outerBox.setMinSize(boxWidth, boxHeight);
			outerBox.setCenter(graphPane);
			graphWindow.setContent(outerBox);

			root.setCenter(graphWindow);
		}

		backBtn = new Button("Back");
		rollBtn = new Button("Roll");
		resetBtn = new Button("Reset");
		computeBtn = new Button("Compute");

		HBox buttons = new HBox(50);

		buttons.getChildren().addAll(backBtn, rollBtn, resetBtn, computeBtn);
		buttons.setAlignment(Pos.BOTTOM_CENTER);

		buttons.getStyleClass().add("contrastPanel");
		BorderPane.setMargin(buttons, new Insets(0, 0, 1, 0));
		buttons.setPadding(new Insets(0.03 * stageHeight, 0,
				0.01 * stageHeight, 0));

		root.setBottom(buttons);
		Scene scene = new Scene(root);
		String menuStyle = NaviView.class.getResource("navi-panel.css")
				.toExternalForm();
		String commonStyle = NaviView.class.getResource("common.css")
				.toExternalForm();
		scene.getStylesheets().add(menuStyle);
		scene.getStylesheets().add(commonStyle);
		return scene;

	}

}
