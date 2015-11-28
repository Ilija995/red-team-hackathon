package eu.execom.hackaton.beacon.model;

public class Util {

	private static String formatTimeUnit(int units) {
		if (units < 10) {
			return "0" + units;
		}

		return units + "";
	}

	public static String timeFormat(int seconds) {
		return formatTimeUnit(seconds / 60) + ":" + formatTimeUnit(seconds % 60);
	}
}
