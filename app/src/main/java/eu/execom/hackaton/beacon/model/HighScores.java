package eu.execom.hackaton.beacon.model;

public class HighScores {

	private static HighScores ourInstance = new HighScores();

	private int classic;

	private int arcade;

	public static HighScores getInstance() {
		return ourInstance;
	}

	private HighScores() {
		classic = Integer.MAX_VALUE;
		arcade = Integer.MIN_VALUE;
	}

	public String getClassic() {
		if (classic == Integer.MAX_VALUE) {
			return "Not played";
		}

		return Util.timeFormat(classic) + " seconds";
	}

	public boolean trySetClassic(int classic) {
		if (this.classic > classic) {
			this.classic = classic;
			return true;
		}

		return false;
	}

	public String getArcade() {
		if (arcade == Integer.MIN_VALUE) {
			return "Not played";
		}

		return arcade + " clue" + ((arcade == 1) ? "" : "s");
	}

	public boolean trySetArcade(int arcade) {
		if (this.arcade < arcade) {
			this.arcade = arcade;
			return true;
		}

		return false;
	}
}

