package navigator.logic;

public interface Estimator<V extends Vertex<V, E>, E extends Edge<V, E>> {

	 public abstract double heuristicFunction(V v0,V v1);

}
