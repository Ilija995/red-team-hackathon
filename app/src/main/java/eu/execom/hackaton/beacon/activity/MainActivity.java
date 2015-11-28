package eu.execom.hackaton.beacon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.model.GameType;
import eu.execom.hackaton.beacon.model.Hint;
import eu.execom.hackaton.beacon.model.HintRepo;
import eu.execom.hackaton.beacon.model.Location;
import eu.execom.hackaton.beacon.model.Util;
import eu.execom.hackaton.beacon.service.BeaconDiscoveryService;
import eu.execom.hackaton.beacon.service.BeaconDiscoveryService_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

	private static final int THRESHOLD = -65;

	private static final int HINTS_IN_ROUND = 3;

	private static final int ARCADE_START_TIME = 120;

	// Hack
	public static boolean shouldWork;

	private Timer timer;

	private int timeCounter;

	private boolean timerRunning;

	@ViewById
	TextView hint;

	@ViewById
	TextView classicTime;

	@ViewById
	TextView arcadeTime;

	@ViewById
	TextView arcadeClues;

	private HintRepo hintRepo = HintRepo.getInstance();

	private Hint currentHint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		hint.setText("Loading...");

		System.out.println("onResume");

		classicTime.setText("");
		arcadeClues.setText("");
		arcadeTime.setText("");
		if (hintRepo.getGameType() == GameType.CLASSIC) {
			System.out.println("time counter == 1");
			timeCounter = 1;
		}
		else {
			if (hintRepo.getScore() == 0) {
				hintRepo.setTime(ARCADE_START_TIME);
			}
			timeCounter = hintRepo.getTime();
		}
		timerRunning = false;

		if (hintRepo.getGameType() == GameType.DISCOVERY) {
			hint.setText("Coming soon...");
		}
	}

	@Override
	public void onBackPressed() {
		shouldWork = false;
		currentHint = null;
		if (hintRepo.getGameType() != GameType.DISCOVERY) {
			timer.cancel();
		}
		finish();
	}
	@AfterViews
	void startService() {
		BeaconDiscoveryService_.intent(this).start();
	}

	private static int cnt = 0;

	@Receiver(actions = BeaconDiscoveryService.NEW_BEACON_SIGHTING)
	void onBeaconSighted() {
		if (!shouldWork)
			return;

		System.out.println("Enter beacon signed");

		List<Location> locations = BeaconDiscoveryService.getLocations();

		if (currentHint == null) {
			currentHint = hintRepo.getHint();
			if (currentHint == null) {
				timer.cancel();
				Intent intent = new Intent(MainActivity.this, GameFinishedActivity_.class);
				startActivity(intent);
				return;
			}
		}

		int index = 0;
		while (index < locations.size() && !locations.get(index).uuid.equals(currentHint.getId())) ++ index;
		if (index == locations.size()) {
			hint.setText("Loading...");
			return;
		}

		System.out.println("Count " + cnt + "\n");

		if (!timerRunning && hintRepo.getGameType() == GameType.CLASSIC) {
			System.out.print("Setting timer\n");
			cnt++;
			timerRunning = true;
			timer = new Timer();
			timer.scheduleAtFixedRate(
					new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									classicTime.setText(String.valueOf(Util.timeFormat(timeCounter)));
									timeCounter++;
								}
							});
						}
					}, 1000, 1000
			);
		}

		if (!timerRunning && hintRepo.getGameType() == GameType.ARCADE) {
			timerRunning = true;
			timer = new Timer();
			timer.scheduleAtFixedRate(
					new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								public void run() {
									arcadeTime.setText(String.valueOf(Util.timeFormat(timeCounter)));
									timeCounter--;
								}
							});
						}
					}, 1000, 1000
			);
		}

		if (hintRepo.getGameType() == GameType.ARCADE && timeCounter <= 0) {
			shouldWork = false;
			currentHint = null;
			timer.cancel();
			Intent intent = new Intent(MainActivity.this, GameFinishedActivity_.class);
			startActivity(intent);
			finish();
			return;
		}

		if (locations.get(index).signalStrength > THRESHOLD) {
			hintRepo.setSolved(currentHint.getId(), timeCounter);
			currentHint = null;
			timer.cancel();

			if (hintRepo.getGameType() == GameType.CLASSIC && hintRepo.getSolvedInCurrentRound() >= HINTS_IN_ROUND) {
				System.out.println("\n2");
				shouldWork = false;
				Intent intent = new Intent(MainActivity.this, GameFinishedActivity_.class);
				startActivity(intent);
				finish();
			}
			else {
				shouldWork = false;
				hintRepo.setTime(timeCounter);
				Intent intent = new Intent(MainActivity.this, SolvedActivity_.class);
				startActivity(intent);
			}
		}
		else {
			if (hintRepo.getGameType() == GameType.ARCADE) {
				arcadeClues.setText("Clues: " + hintRepo.getScore());
			}

			hint.setText(currentHint.getHint());
		}
	}

}
