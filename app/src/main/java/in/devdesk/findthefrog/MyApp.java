package in.devdesk.findthefrog;

import android.app.Application;
import android.os.SystemClock;

/**
 * Created by richardandrews on 08/07/17.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(2000);
    }
}
