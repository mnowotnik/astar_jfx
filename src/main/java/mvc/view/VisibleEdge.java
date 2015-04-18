package mvc.view;

import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import util.Geometry;

import common.Point;
import common.Segment;

public class VisibleEdge extends Group {
	private final VisiblePoint p1, p2;
	private final Segment edge;
	private Line line;
	private static final double ARROW_WIDTH = 10, ARROW_HEIGHT = 10;
	private Polygon arrowHead;

	public VisibleEdge(VisiblePoint p1, VisiblePoint p2, Segment e) {
		this.p1 = p1;
		this.p2 = p2;
		this.edge = e;
		line = makeLine();
		super.getChildren().add(line);
	}

	public void directArrow(Point start, Point end) {
		Point vStart = null, vEnd = null;
		
		double distance=Geometry.calculateDistance(start,end);
		if(distance<p1.getRadius()){super.getChildren().clear();}

		if (start.equals(p1.getPoint()) && end.equals(p2.getPoint())) {
			vStart = p1.getCenter();
			vEnd = p2.getCenter();

		} else if (start.equals(p2.getPoint()) && end.equals(p1.getPoint())) {
			vStart = p2.getCenter();
			vEnd = p1.getCenter();

		} else {
			// ERROR
			System.exit(-1);
			System.err.println("error. No such point in visEdge.");
		}

		arrowHead = new Polygon();
		arrowHead.getPoints().setAll(getArrowHeadPoints(vStart, vEnd));

		Point lineEnd = Geometry.movePoint(p1.getRadius() + ARROW_HEIGHT, vEnd,
				vStart);
		if (vEnd.equals(p2.getCenter())) {
			line.setEndX(lineEnd.getX());
			line.setEndY(lineEnd.getY());
		} else {
			line.setStartX(lineEnd.getX());
			line.setStartY(lineEnd.getY());
		} // skracanie linii

		super.getChildren().clear();
		Shape arrow = Path.union(arrowHead, line);
		arrow.setId("edgeChosen");
		super.getChildren().add(arrow);
	}

	public void fade() {
		super.setOpacity(0.6f);
		line.setStrokeWidth(1.0);
	}

	public Point getPointA() {
		return p1.getCenter();
	}

	public Point getPointB() {
		return p2.getCenter();
	}

	public Segment getRealEdge() {
		return edge;
	}

	public void reset() {
		super.getChildren().clear();
		line = makeLine();
		super.getChildren().add(line);
		super.toBack();
	}

	private Double[] getArrowHeadPoints(Point arrowStart, Point arrowEnd) {
		double cos = Geometry.calculateCos(arrowStart, arrowEnd);
		double sin = Geometry.calculateSin(arrowStart, arrowEnd);
		Point end = Geometry
				.movePoint(p1.getRadius() + 2, arrowEnd, arrowStart);
		Point start = Geometry.movePoint(ARROW_HEIGHT, end, arrowStart);

		double dx = ARROW_WIDTH / 2 * cos;
		double dy = ARROW_WIDTH / 2 * sin;
		double tmp = dx;
		dx = dy;
		dy = -tmp; // wektor prostopadły
		Double[] points = new Double[] { start.getX() + dx, start.getY() + dy,
				start.getX() - dx, start.getY() - dy, end.getX(), end.getY() };

		return points;
	}

	private Line makeLine() {
		Line line = new Line(p1.getCenterX(), p1.getCenterY(), p2.getCenterX(),
				p2.getCenterY());
		line.setId("edge");
		return line;
	}

}
