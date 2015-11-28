package eu.execom.hackaton.beacon.model;

import java.util.ArrayList;
import java.util.List;

public class HintRepo {

	private static HintRepo instance;

	private List<Hint> hints;

	private Hint lastSolved;

	private int solvedInCurrentRound;

	private int score;

	private GameType gameType;

	private int time;

	private HintRepo() {
		hints = new ArrayList<>();
		lastSolved = null;
	}

	public static HintRepo getInstance() {
		if (instance == null) {
			instance = new HintRepo();
		}

		return instance;
	}

	public void add(String beaconId, String title, String hint) {
		hints.add(new Hint(beaconId, title, hint));
	}

	public Hint getHint() {

		int index = 0;
		while (index < hints.size() && hints.get(index).isSolved()) ++index;

		if (index < hints.size()) {
			return hints.get(index);
		}

		return null;
	}

	public Hint getLastSolved() {
		return lastSolved;
	}

	public GameType getGameType() {
		return gameType;
	}

	public void setGameType(GameType gameType) {
		this.gameType = gameType;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setSolved(String id, int timeToSolve) {
		int index = 0;
		while (index < hints.size() && !hints.get(index).getId().equals(id)) ++index;

		if (index < hints.size()) {
			hints.get(index).setSolved(true, timeToSolve);
			lastSolved = hints.get(index);
			solvedInCurrentRound++;
			if (gameType == GameType.CLASSIC) {
				score += timeToSolve;
			}
			else if (gameType == GameType.ARCADE) {
				score++;
			}
		}
	}

	public int getSolvedInCurrentRound() {
		return solvedInCurrentRound;
	}

	public int getScore() {
		return score;
	}

	public void prepareNextRound() {
		solvedInCurrentRound = 0;
		score = 0;
	}
}
