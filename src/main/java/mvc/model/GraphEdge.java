package mvc.model;

import navigator.logic.Edge;
import util.Geometry;

import common.Segment;

public class GraphEdge extends Edge<GraphPoint, GraphEdge> {

	public GraphEdge(GraphPoint a, GraphPoint b) {
		super(a, b, Geometry.calculateDistance(a.getPoint(), b.getPoint()));
	}

	public Segment getSegment() {
		return new Segment(getA().getPoint(), getB().getPoint(), getWeight());
	}
}