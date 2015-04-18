package mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import navigator.logic.AlgorithmA;
import navigator.logic.Solution;

import common.Point;
import common.Segment;

public class NaviModel {
	private Graph graph;
	private final int n;
	private final double r;
	private final Map<Point, GraphPoint> gPointsMap;
	private Solution<GraphPoint, GraphEdge> solution;

	public NaviModel(int n, double r) {
		graph = new Graph();
		this.n = n;
		this.r = r;
		gPointsMap = new HashMap<>();
		this.newGraph();
	}

	public List<Segment> getEdges() {
		return graph.getEdgesList();
	}

	public List<Point> getPoints() {
		return graph.getPoints();
	}

	public List<Point> computeSolution(Point a, Point b) {
		solution = new GraphSolution(new AlgorithmA<GraphPoint, GraphEdge>(),
				gPointsMap.get(a), gPointsMap.get(b));
		List<Point> optimalRoute = new ArrayList<>();
		List<GraphPoint> route = solution.computeSolution();
		for (GraphPoint routePoint : route) {
			optimalRoute.add(routePoint.getPoint());
		}
		return optimalRoute;
	}

	public void newGraph() {
		graph.clear();
		Random random = new Random();
		for (int i = 0; i < n; i++) { // generacja punktów (<0,1><0,1>)
			GraphPoint p = new GraphPoint(random.nextDouble(),
					random.nextDouble());
			graph.addPoint(p);
			gPointsMap.put(p.getPoint(), p);
		}
		List<GraphPoint> tmpPoints = new ArrayList<>(graph.getGraphPoints());
		Iterator<GraphPoint> iterator = tmpPoints.iterator();

		while (iterator.hasNext()) // dwumian Newtona dla zbioru punktów
		{
			GraphPoint point1 = iterator.next();
			iterator.remove();

			for (GraphPoint point2 : tmpPoints) {

				double x = random.nextDouble();
				double var;
				double dx = point2.getX() - point1.getX();
				double dy = point2.getY() - point1.getY();
				double dist = Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 0.5);
				var = Math.exp(-dist / r); // warunek istnienia krawędzi z
											// treści
				if (x < var) {
					GraphEdge e = new GraphEdge(point1, point2);
					graph.addEdge(e);
					point1.addEdge(e);
					point2.addEdge(e);
				}

			}
		}

	}

}
