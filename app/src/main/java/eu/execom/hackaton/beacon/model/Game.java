package eu.execom.hackaton.beacon.model;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.util.List;
import java.util.Map;

import eu.execom.hackaton.beacon.trilateration.NonLinearLeastSquaresSolver;
import eu.execom.hackaton.beacon.trilateration.TrilaterationFunction;

public class Game {

	private static final double EPSILON = 1;

	private static final double A = 0.008401;

	private static final double B = -0.0736;

	private Map<String, Point> beacons;

	private Point target;

	private Point currPos;

	private boolean finished;

	private int points;

	private double distanceFromTarget;

	public Game(Map<String, Point> beacons) {
		this.beacons = beacons;

		this.target = nextTarget();
		this.points = 0;
		this.finished = false;
		this.distanceFromTarget = Double.MAX_VALUE;
	}

	public Point getTarget() {
		return target;
	}

	public Point getCurrPos() {
		return currPos;
	}

	public boolean isFinished() {
		return finished;
	}

	public int getPoints() {
		return points;
	}

	public double getDistanceFromTarget() {
		return distanceFromTarget;
	}

	private double signalStrengthToDistance(int signalStrength) {
		return A * Math.exp(B * signalStrength);
	}

	private Point calculateCurrentPosition(List<Location> locations) {

		double[][] beaconPos = new double[beacons.size()][];
		double[] distances = new double[beaconPos.length];

		System.out.println("\nLocations [" + locations.size() + "] ");

		int index = 0;
		for (Location location : locations) {
			beaconPos[index] = beacons.get(location.uuid).getCoordinates();
			distances[index] = signalStrengthToDistance(location.signalStrength);
			System.out.println("[" + index + "] x: " + (int) (beaconPos[index][0] * 100.0) / 100.0 + ", y: " + (int) (beaconPos[index][1] * 100.0) / 100.0 + ", dist: " + (int) (distances[index] * 100.0) / 100.0);
			++index;
		}

		if (locations.size() == beacons.size()) {
			System.out.println("Beacon position [" + beaconPos.length + "]\nDistances [" + distances.length + "]\n");
			NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(beaconPos, distances), new LevenbergMarquardtOptimizer());
			LeastSquaresOptimizer.Optimum optimum = solver.solve();

			// the answer
			double[] centroid = optimum.getPoint().toArray();

			// error and geometry information; may throw SingularMatrixException depending the threshold argument provided
			RealVector standardDeviation = optimum.getSigma(0);
			RealMatrix covarianceMatrix = optimum.getCovariances(0);

			return new Point(centroid[0], centroid[1]);
		}

		return null;
	}

	private Point nextTarget() {
		return new Point(Math.random() * 15 - 5, Math.random() * 15 - 1);
	}

	public void update(List<Location> locations) {
		System.out.println("\nUpdating\n");
		currPos = calculateCurrentPosition(locations);
		System.out.println("Calculated position\n");
		if (currPos != null) {

			distanceFromTarget = currPos.dist(target);

			if (distanceFromTarget < EPSILON) {
				target = nextTarget();
				points += 10;
				finished = points > 50;
			}
		}
	}
}
