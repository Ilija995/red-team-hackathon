package eu.execom.hackaton.beacon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.HighScores;

public class HighScoresActivity extends AppCompatActivity {

	private HighScores highScores = HighScores.getInstance();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);

		TextView classicHighScore = (TextView) findViewById(R.id.classicHighScore);
		classicHighScore.setText(highScores.getClassic());

		TextView arcadeHighScore = (TextView) findViewById(R.id.arcadeHighScore);
		arcadeHighScore.setText(highScores.getArcade());
	}

}
