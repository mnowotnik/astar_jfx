package mvc.model;

import navigator.logic.Algorithm;
import navigator.logic.Solution;

public class GraphSolution extends Solution<GraphPoint, GraphEdge> {

	public GraphSolution(Algorithm<GraphPoint, GraphEdge> algorithm,
			GraphPoint nodeStart, GraphPoint nodeEnd) {
		super(algorithm, nodeStart, nodeEnd,new GraphEstimator());
	}

}
