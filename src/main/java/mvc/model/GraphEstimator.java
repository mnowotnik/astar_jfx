package mvc.model;

import navigator.logic.Estimator;
import util.Geometry;

public class GraphEstimator implements Estimator<GraphPoint, GraphEdge>{

	@Override
	public double heuristicFunction(GraphPoint v0, GraphPoint v1) {
		
		return Geometry.calculateDistance(v0.getPoint(), v1.getPoint());
	}

}
