package navigator.logic;

abstract public class Edge<V extends Vertex<V, E>, E extends Edge<V, E>> {
	private final V a, b;
	private final double weight;

	public Edge(V a, V b, double w) {
		weight = w;
		this.a = a;
		this.b = b;
	}

	public V getA() {
		return a;
	}

	public V getB() {
		return b;
	}

	public V getOther(V n) {
		if (n.equals(a)) {
			return b;
		} else {
			return a;
		}
	}

	public double getWeight() {
		return weight;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?, ?> other = (Edge<?, ?>) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		}
		if (b == null) {
			if (other.b != null)
				return false;
		}
		if ((a.equals(other.a) && b.equals(other.b)) == false) {
			if ((a.equals(other.b) && b.equals(other.a)) == false) {
				return false;
			}
		}

		if (Double.doubleToLongBits(weight) != Double
				.doubleToLongBits(other.weight))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ (((a == null) ? 0 : a.hashCode()) ^ ((b == null) ? 0 : b
						.hashCode()));
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

}
