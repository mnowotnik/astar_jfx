package util;

import common.Point;
import common.Segment;
import common.Vector;

public class Geometry {
	public static double calculateDistance(Point a, Point b) {
		double d;
		double dx = (a.getX() - b.getX());
		double dy = (a.getY() - b.getY());
		d = Math.pow(dx * dx + dy * dy, 0.5);
		return d;
	}

	public static Vector getVector(double tan, double d) {
		double d2 = d * d;
		double dx2 = d2 / (1 + tan * tan);
		double dx = Math.pow(dx2, 0.5);
		double dy = Math.pow(d2 - dx2, 0.5);
		return new Vector(dx, dy);
	}

	public static Vector getVector(double dist, double sin, double cos) {
		return new Vector(dist * cos, dist * sin);
	}

	public static Vector getVector(double dist, Point start, Point end) {
		Segment ordered = Geometry.order(start, end);
		double sin = Geometry.calculateSin(ordered.getA(), ordered.getB());
		double cos = Geometry.calculateCos(ordered.getA(), ordered.getB());
		double dx = dist * cos;
		double dy = dist * sin;
		return new Vector(dx, dy);
	}

	public static double calculateTan(Point p1, Point p2) {
		double tan = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
		return tan;
	}

	public static double calculateSin(Point p1, Point p2) {
		double sin = (p2.getY() - p1.getY()) / (calculateDistance(p1, p2));
		return sin;
	}

	public static double calculateCos(Point p1, Point p2) {
		double cos = (p2.getX() - p1.getX()) / (calculateDistance(p1, p2));
		return cos;
	}

	public static double calculateSinCos(double cosin) {
		return Math.pow(1 - cosin * cosin, 0.5);
	}

	public static Segment order(Point p1, Point p2) {
		return p1.getX() < p2.getX() ? new Segment(p1, p2)
				: new Segment(p2, p1);
	}

	// przesuwa end w kierunku start o dist
	public static Point movePoint(double dist, Point fromPoint, Point toPoint) {
		Vector v = Geometry.getVector(dist, toPoint, fromPoint);
		double dx = v.getDx();
		double dy = v.getDy();
		if (toPoint.getX() < fromPoint.getX()) {
			dx = -dx;
			dy = -dy;
		}
		double x = fromPoint.getX(), y = fromPoint.getY();
		return new Point(x + dx, y + dy);
	}

}
