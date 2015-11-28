package eu.execom.hackaton.beacon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.GameType;
import eu.execom.hackaton.beacon.model.HintRepo;

public class StartActivity extends AppCompatActivity {

	private HintRepo hintRepo = HintRepo.getInstance();

	private void addHints() {
		hintRepo.add("Q2WN-ZSZPY", getString(R.string.coffee_hint_title), getString(R.string.coffee_hint));
		hintRepo.add("QMPG-BHA14", getString(R.string.white_board_hint_title), getString(R.string.white_board_hint));
		hintRepo.add("37VF-SHK22", getString(R.string.flowers_hint_title), getString(R.string.flowers_hint));
		hintRepo.add("894U-PP28S", getString(R.string.front_door_coat_hanger_hint_title), getString(R.string.front_door_coat_hanger_hint));
		hintRepo.add("NV56-5TPU5", getString(R.string.toilet_hint_title), getString(R.string.toilet_hint));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		addHints();
	}

	@Override
	protected void onResume() {
		super.onResume();

		hintRepo.prepareNextRound();
	}

	public void goToMain(View view) {
		Intent intent = new Intent(StartActivity.this, MainActivity_.class);
		startActivity(intent);
	}

	public void goClassic(View view) {
		MainActivity_.shouldWork = true;
		hintRepo.setGameType(GameType.CLASSIC);
		goToMain(view);
	}

	public void goArcade(View view) {
		MainActivity_.shouldWork = true;
		hintRepo.setGameType(GameType.ARCADE);
		goToMain(view);
	}

	public void goDiscover(View view) {
		MainActivity_.shouldWork = false;
		hintRepo.setGameType(GameType.DISCOVERY);
		goToMain(view);
	}

	public void goToHighScores(View view) {
		Intent intent = new Intent(StartActivity.this, HighScoresActivity.class);
		startActivity(intent);
	}

}
