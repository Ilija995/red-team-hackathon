package eu.execom.hackaton.beacon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.GameType;
import eu.execom.hackaton.beacon.model.HighScores;
import eu.execom.hackaton.beacon.model.HintRepo;

@EActivity
public class GameFinishedActivity extends AppCompatActivity {

	private HintRepo hintRepo = HintRepo.getInstance();

	private HighScores highScores = HighScores.getInstance();

	@ViewById
	TextView gameFinishedMessage;

	@ViewById
	TextView scoreMessage;

	@ViewById
	TextView newHighScore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_finished);

		if (hintRepo.getGameType() == GameType.CLASSIC) {
			gameFinishedMessage.setText("Congratulations, round finished!");
			scoreMessage.setText("Time: " + hintRepo.getScore() + " seconds");
			if (highScores.trySetClassic(hintRepo.getScore())) {
				newHighScore.setText("New high score!");
			} else {
				newHighScore.setText("");
			}
		}
		else if (hintRepo.getGameType() == GameType.ARCADE) {
			gameFinishedMessage.setText("Time elapsed");
			scoreMessage.setText("Clues: " + hintRepo.getScore());
			if (highScores.trySetArcade(hintRepo.getScore())) {
				newHighScore.setText("New high score!");
			} else {
				newHighScore.setText("");
			}
		}
	}

	public void goHome(View view) {
		Intent intent = new Intent(GameFinishedActivity.this, StartActivity.class);
		startActivity(intent);
	}

}
