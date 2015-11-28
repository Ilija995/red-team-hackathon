package eu.execom.hackaton.beacon.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Receiver;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import eu.execom.hackaton.beacon.R;
import eu.execom.hackaton.beacon.adapter.LocationListAdapter;
import eu.execom.hackaton.beacon.model.Game;
import eu.execom.hackaton.beacon.model.Point;
import eu.execom.hackaton.beacon.service.BeaconDiscoveryService;
import eu.execom.hackaton.beacon.service.BeaconDiscoveryService_;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

	@ViewById
	ListView locationList;

	@ViewById
	Toolbar toolbar;

	@ViewById
	TextView points;

	@ViewById
	TextView distance;

	@ViewById
	TextView target;

	@ViewById
	TextView currPos;

	@Bean
	LocationListAdapter adapter;

	private Game game;

	@AfterViews
	void startService() {

		Map<String, Point> beacons = new HashMap<>();

		// Beacon 01 - Distance ~ 0.9m, Angle ~ 20 deg
		beacons.put("Q2WN-ZSZPY", new Point(0.81567700833298, 0.38035643556663));

		// Beacon 02 - Distance ~ 3m, Angle ~ 155 deg
		beacons.put("QMPG-BHA14", new Point(-2.7189233611099, 1.2678547852221));

		// Beacon 03 - Distance ~ 6m, Angle ~ 350 deg
		beacons.put("37VF-SHK22", new Point(5.9088465180732, -1.041889066002));

		// Beacon 04 - Distance ~ 7.5m, Angle ~ 35 deg
		beacons.put("894U-PP28S", new Point(6.1436403321674, 4.3018232726328));

		// Beacon 05 - Distance ~ 10m, Angle ~ 50 deg
		beacons.put("NV56-5TPU5", new Point(6.427876096865, 7.6604444311898));

		game = new Game(beacons);

		BeaconDiscoveryService_.intent(this).start();
	}

	@AfterViews
	void setViews() {
		setSupportActionBar(toolbar);
		locationList.setAdapter(adapter);
	}

	private double roundTwo(double val) {
		return (int) (val * 100.0) / 100.0;
	}

	@Receiver(actions = BeaconDiscoveryService.NEW_BEACON_SIGHTING)
	void onBeaconSighted() {
		adapter.update(BeaconDiscoveryService.getLocations());
		game.update(BeaconDiscoveryService.getLocations());
		if (game != null) {
			points.setText("Points: " + game.getPoints());
			distance.setText("Distance: " + game.getDistanceFromTarget());
			target.setText("Target [" + roundTwo(game.getTarget().getX()) + ", " + roundTwo(game.getTarget().getY()) + "]");
			if (game.getCurrPos() != null) {
				currPos.setText("Current position [" + roundTwo(game.getCurrPos().getX()) + ", " + roundTwo(game.getCurrPos().getY()) + "]");
			}
			else {
				currPos.setText("Current position not defined");
			}
		}
	}

}
