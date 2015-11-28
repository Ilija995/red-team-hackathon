package eu.execom.hackaton.beacon.model;

public class Hint {

	private String id;

	private String title;

	private String hint;

	private boolean solved;

	private int timeToSolve;

	public Hint(String id, String title, String hint) {
		this.id = id;
		this.title = title;
		this.hint = hint;

		this.solved = false;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getHint() {
		return hint;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved, int timeToSolve) {
		this.solved = solved;
		this.timeToSolve = timeToSolve;
	}
}
