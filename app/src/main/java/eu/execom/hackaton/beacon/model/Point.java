package eu.execom.hackaton.beacon.model;

public class Point {

	private double x;

	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double[] getCoordinates() {
		return new double[] { x, y };
	}

	public double dist(Point that) {
		return Math.sqrt(Math.pow(this.x - that.x, 2) + Math.pow(this.y - that.y, 2));
	}
}
