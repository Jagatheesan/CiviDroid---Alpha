package you.in.spark.energy.cividroid;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import you.in.spark.energy.cividroid.R.string;

@ReportsCrashes(
        mailTo = "ijp@live.in",
        mode = ReportingInteractionMode.TOAST,
        resToastText = string.crash_toast_text)
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}
