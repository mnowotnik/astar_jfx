package mvc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Point;
import common.Segment;

public class Graph {
	private List<GraphPoint> graphPoints;
	private List<GraphEdge> graphEdges;

	public Graph() {
		graphPoints = new ArrayList<>();
		graphEdges = new ArrayList<>();
	}

	public void addEdge(GraphEdge e) {
		graphEdges.add(e);
	}

	public void addPoint(GraphPoint p) {
		graphPoints.add(p);
	}

	public void clear() {
		graphPoints.clear();
		graphEdges.clear();
	}

	List<GraphPoint> getGraphPoints() {
		return graphPoints;
	}

	List<GraphEdge> getGraphEdges() {
		return Collections.unmodifiableList(graphEdges);
	}

	public List<Segment> getEdgesList() {
		List<Segment> edges = new ArrayList<>();
		for (GraphEdge gEdge : graphEdges) {
			edges.add(gEdge.getSegment());
		}
		return Collections.unmodifiableList(edges);
	}

	public List<Point> getPoints() {
		List<Point> points = new ArrayList<>();
		for (GraphPoint gPoint : graphPoints) {
			points.add(gPoint.getPoint());
		}
		return Collections.unmodifiableList(points);
	}

}
