package navigator.logic;

import java.util.List;

abstract public class Solution<V extends Vertex<V, E>, E extends Edge<V, E>> {
	private final Algorithm<V, E> algorithm;
	private final V nodeStart, nodeEnd;
	private final Estimator<V,E> estimator;

	public Solution(Algorithm<V, E> algorithm, V nodeStart, V nodeEnd,Estimator<V,E>estimator) {
		this.algorithm = algorithm;
		this.nodeStart = nodeStart;
		this.nodeEnd = nodeEnd;
		this.estimator=estimator;
	}

	public List<V> computeSolution() {
		return algorithm.compute(nodeStart, nodeEnd,estimator);
	}

}
