package mvc.view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import mvc.model.GraphPoint;

import common.Point;

public class VisiblePoint extends Circle {

	private final Point realPoint;
	private boolean checked;

	public VisiblePoint(double x, double y, Point point) {
		super.setCenterX(x);
		super.setCenterY(y);
		super.setRadius(DEFAULT_RADIUS);
		super.setOpacity(0.3f);
		super.setId("circle");
		realPoint = point;
		checked = false;
		updateState();
	}

	public void check() {
		checked = !checked;
		updateState();
	}

	public Point getCenter() {
		return new Point(super.getCenterX(), super.getCenterY());
	}

	public Point getPoint() {
		return realPoint;
	}

	public GraphPoint getVisiblePoint() {
		return new GraphPoint(super.getCenterX(), super.getCenterY());
	}

	private void updateState() {
		if (checked) {
			super.setFill(Paint.valueOf("#FF1800"));
		} else {
			super.setFill(Paint.valueOf("#00B2FF"));
		}
	}

	static final double DEFAULT_RADIUS = 10f;

}
