package in.devdesk.findthefrog.MyPager.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.AUTHTOKEN;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LATITUDE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.NEXTWALKDATE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.STREAKLENGTH;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.TOTALSCORE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.USERNAME;

/**
 * Created by richardandrews on 09/07/17.
 */

public class ProfileFragment extends Fragment {


    @BindView(R.id.tv_username_profile)
    TextView userName;

    @BindView(R.id.tv_usernamelevel_profile)
    TextView userLevel;

    @BindView(R.id.tv_userscore_profile)
    TextView userScore;

    @BindView(R.id.tv_nv_desc)
    TextView nv_desc;

    @BindView(R.id.tv_nv_value)
    TextView nv_value;

    @BindView(R.id.tv_lv_desc)
    TextView lv_desc;

    @BindView(R.id.tv_lv_value)
    TextView lv_value;

    @BindView(R.id.tv_streak_value)
    TextView streak_value;

    @OnClick(R.id.iv_profile_newwalk)
    public void moveToMap()
    {
        ViewPager vp=(ViewPager) getActivity().findViewById(R.id.vpg_parent_parenttab);
        vp.setCurrentItem(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if(isVisibleToUser)
//        {
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);

            if (sharedPrefs.contains(USERNAME)) {
                String username = sharedPrefs.getString(USERNAME, "wrongUserName");
                String authToken = sharedPrefs.getString(AUTHTOKEN, "wrongAuthToken");
                String nextPrevWalkDate =  sharedPrefs.getString(NEXTWALKDATE, "0#noFrogAddress%0#noFrogAddress");
                int streakLength = sharedPrefs.getInt(STREAKLENGTH, 0);
                int totalScore = sharedPrefs.getInt(TOTALSCORE, 0);
                String totalScoreString = Integer.toString(totalScore);
                String[] nextPrevParts = nextPrevWalkDate.split("%");
                String dateAddressPart1 = nextPrevParts[0];
                Log.i("test", nextPrevWalkDate);
                String dateAddressPart2 = nextPrevParts[1];
                String[] dateAddressPart1DateAddress = dateAddressPart1.split("#");
                String dateAddressPart1Date = dateAddressPart1DateAddress[0];
                String dateAddressPart1Address = dateAddressPart1DateAddress[1];
                String[] dateAddressPart2DateAddress = dateAddressPart2.split("#");
                int dateAddressPart2Date = Integer.parseInt(dateAddressPart2DateAddress[0]) + 7;
                String dateAddressPart2Address = dateAddressPart2DateAddress[1];

                Log.i("vis",dateAddressPart1Date +" | "+ dateAddressPart1Address +" | "+ dateAddressPart2Date +" | "+ dateAddressPart2Address);

                userName.setText(username.toUpperCase());
                nv_value.setText(Integer.toString(dateAddressPart2Date));
                nv_desc.setText(dateAddressPart2Address);
                lv_desc.setText(dateAddressPart1Address);
                lv_value.setText(dateAddressPart1Date);
                streak_value.setText(Integer.toString(streakLength));

                userScore.setText(totalScoreString);

                if(totalScore < 200)
                {
                    userLevel.setText("NOVIST");
                }
                else if(totalScore >= 200 && totalScore <= 800 )
                {
                    userLevel.setText("SEMI-CHAMP");
                }
                else
                {
                    userLevel.setText("AWESOME");
                }

            }
//        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }
}
