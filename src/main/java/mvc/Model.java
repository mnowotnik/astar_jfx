package mvc;

import java.util.List;

import mvc.model.NaviModel;

import common.Point;
import common.Segment;

public class Model {
	private NaviModel navi;

	public Model() {
	}

	public List<Point> computeSolution(Point a, Point b) {
		return navi.computeSolution(a, b);
	}

	public List<Segment> getSegments() {
		return navi.getEdges();
	}

	public List<Point> getPoints() {
		return navi.getPoints();
	}

	public void newGraph()// losuj ponownie punkty i krawędzie
	{
		navi.newGraph();
	}

	public void newNaviModel(int n, double r) // nowa instancja
	{
		navi = new NaviModel(n, r);
	}

}
