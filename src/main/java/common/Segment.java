package common;

import util.Geometry;

public class Segment {
	private final Point a, b;
	private final double distance;

	public Segment(Point a, Point b) {
		distance = Geometry.calculateDistance(a, b);
		this.a = a;
		this.b = b;
	}

	public Segment(Point a, Point b, double d) {
		distance = d;
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
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
		if (Double.doubleToLongBits(distance) != Double
				.doubleToLongBits(other.distance))
			return false;
		return true;
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public Point getOtherPoint(Point n) {
		if (n.equals(a)) {
			return b;
		} else {
			return a;
		}
	}

	public double getDistance() {
		return distance;
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
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
