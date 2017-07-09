package in.devdesk.findthefrog.MyPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.devdesk.findthefrog.MyPager.Map.MapFragment;
import in.devdesk.findthefrog.MyPager.Other.OtherFragment;
import in.devdesk.findthefrog.MyPager.Profile.ProfileFragment;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.AUTHTOKEN;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.USERNAME;

public class ParentTab extends AppCompatActivity {


    //-- blind strings
    private int[] tabIcons = {
            R.drawable.user_white,
            R.drawable.pin_white,
            R.drawable.glasses_white
    };
    ViewPagerAdapter adapter;


    //-- data binding
    @BindView(R.id.tbl_parent_parenttab)
    TabLayout tabLayout;

    @BindView(R.id.vpg_parent_parenttab)
    ViewPager viewPager;


    //-- onClicks


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_tab);
        ButterKnife.bind(this);
//        SharedPreferences sharedPrefs = getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
//        String username = sharedPrefs.getString(USERNAME, null);
//        String authToken = sharedPrefs.getString(AUTHTOKEN, null);
//        Toast.makeText(this, "Welcome, " + username + " " + authToken, Toast.LENGTH_SHORT).show();
//        setUpPagerAndTabs();
        setupViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        tabLayout.setSelectedTabIndicatorHeight(15);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ProfileFragment(), "PROFILEfragment");
        adapter.addFrag(new MapFragment(),"MAPfragment");
        adapter.addFrag(new OtherFragment(), "OTHERfragment");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mfragmentlist =new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mfragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return mfragmentlist.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mfragmentlist.add(fragment);
            mFragmentTitleList.add(title);
        }

    }
}
