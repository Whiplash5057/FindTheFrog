package in.devdesk.findthefrog;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MyIntro extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_intro);
        addSlide(AppIntroFragment.newInstance(
                getResources().getString(R.string.slide_1_title),
                getResources().getString(R.string.slide_1_description),
                R.drawable.house,
                Color.parseColor("#b4a7d6")));

        addSlide(AppIntroFragment.newInstance(
                getResources().getString(R.string.slide_2_title),
                getResources().getString(R.string.slide_2_description),
                R.drawable.loupe,
                Color.parseColor("#8e7cc3")));

        addSlide(AppIntroFragment.newInstance(
                getResources().getString(R.string.slide_3_title),
                getResources().getString(R.string.slide_3_description),
                R.drawable.placeholder,
                Color.parseColor("#b6d7a8")));

        addSlide(AppIntroFragment.newInstance(
                getResources().getString(R.string.slide_4_title),
                getResources().getString(R.string.slide_4_description),
                R.drawable.safebox,
                Color.parseColor("#93c47d")));

        addSlide(AppIntroFragment.newInstance(
                getResources().getString(R.string.slide_5_title),
                getResources().getString(R.string.slide_5_description),
                R.drawable.memory,
                Color.parseColor("#6aa84f")));

        showStatusBar(false);
    }

    @Override
    public void onDonePressed() {
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
//        Toast.makeText(this, "Skip was pressed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
//        Toast.makeText(this, "Slide changed", Toast.LENGTH_SHORT).show();
    }

}
