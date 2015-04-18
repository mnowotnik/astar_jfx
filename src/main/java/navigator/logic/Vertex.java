package navigator.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

abstract public class Vertex<V extends Vertex<V, E>, E extends Edge<V, E>> {

	private final List<E> edges;
	protected final List<Object> parameters;
	
	public Vertex(Object... parameters) {
		this.parameters=Arrays.asList(parameters);
		edges = new ArrayList<>();
	}

	public void addEdge(E e) {
		edges.add(e);
	}

	public List<E> getEdges() {
		return Collections.unmodifiableList(edges);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}



}
