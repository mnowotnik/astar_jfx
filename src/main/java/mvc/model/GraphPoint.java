package mvc.model;
import navigator.logic.Vertex;

import common.Point;

public class GraphPoint extends Vertex<GraphPoint, GraphEdge> {

	public GraphPoint(double x, double y) {
		super(x,y);
	}

	public GraphPoint(Point p) {
		super(p.getX(),p.getY());
	}

	public Point getPoint() {
		return new Point((double)super.parameters.get(0),(double)super.parameters.get(1));
	}

	public double getX() {
		return (double)super.parameters.get(0);
	}

	public double getY() {
		return (double)super.parameters.get(1);
	}

}
