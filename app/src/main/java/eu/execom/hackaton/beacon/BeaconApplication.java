package eu.execom.hackaton.beacon;

import android.app.Application;

import com.gimbal.android.Gimbal;
import com.gimbal.logging.GimbalLogConfig;
import com.gimbal.logging.GimbalLogLevel;

import org.androidannotations.annotations.EApplication;

@EApplication
public class BeaconApplication extends Application {

    private static final String GIMBAL_API_KEY = "34712be4-47d8-4ec7-a53b-cc6bcafc3f17";

    @Override
    public void onCreate() {
        super.onCreate();

        Gimbal.setApiKey(this, GIMBAL_API_KEY);
        GimbalLogConfig.setLogLevel(GimbalLogLevel.INFO);
        GimbalLogConfig.enableFileLogging(this);
    }

}
