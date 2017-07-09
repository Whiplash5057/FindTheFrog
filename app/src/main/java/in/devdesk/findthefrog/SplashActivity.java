package in.devdesk.findthefrog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import in.devdesk.findthefrog.LoginSignUp.MainActivity;
import in.devdesk.findthefrog.MyPager.ParentTab;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.USERNAME;

/**
 * Created by richardandrews on 08/07/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        initializeElements();
    }

    private void initializeElements() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);
                if (isFirstStart) {
                    startActivity(new Intent(SplashActivity.this, MyIntro.class));
                    finish();
                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
                else
                {
                    SharedPreferences sharedPrefs = getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
                    if(!sharedPrefs.contains(USERNAME))
                    {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(SplashActivity.this, ParentTab.class));
                    }
                }
            }
        });
        thread.start();
    }
}
