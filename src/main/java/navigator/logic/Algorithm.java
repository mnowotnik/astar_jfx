package navigator.logic;

import java.util.List;

public interface Algorithm<V extends Vertex<V, E>, E extends Edge<V, E>> {
	public List<V> compute(V vertexStart, V vertexEnd,Estimator<V,E>estimator);
}
