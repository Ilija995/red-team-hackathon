package eu.execom.hackaton.beacon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.HintRepo;

@EActivity
public class SolvedActivity extends AppCompatActivity {

	private HintRepo hintRepo = HintRepo.getInstance();

	@ViewById
	TextView solvedMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solved);

		solvedMessage.setText("Congratulations, you've found " + hintRepo.getLastSolved().getTitle());
	}

	public void goToNextHint(View view) {
		MainActivity_.shouldWork = true;
		Intent intent = new Intent(SolvedActivity.this, MainActivity_.class);
		startActivity(intent);
	}

}
