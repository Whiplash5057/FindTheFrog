package in.devdesk.findthefrog.MyPager.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.devdesk.findthefrog.HeLpEr.Utils;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.NEXTWALKDATE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.STREAKLENGTH;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.TOTALSCORE;

/**
 * Created by richardandrews on 09/07/17.
 */

public class OtherFragment extends Fragment implements Other_MVP.View{

    //-- blind strings
    public static ItemAdapter mItemAdapter;
    public static boolean isNotZero = false;
    LinearLayoutManager mLayoutManager;

    //-- data binding
    @BindView(R.id.rview_froglist_other)
    RecyclerView mRecyclerView;

    @BindView(R.id.rlay_froglist_other)
    RelativeLayout rLayProgressBarOther;

    @BindView(R.id.lbl_froglist_other)
    TextView errorMsg;

    //-- onClicks


    //-- methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Utils.isNetworkAvailable(getActivity()))
        {
            errorMsg.setText(getResources().getString(R.string.no_internet));
            rLayProgressBarOther.setVisibility(View.GONE);
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
            String username = sharedPrefs.getString("username", "wrongUserName");
            String authToken = sharedPrefs.getString("authToken", "wrongAuthToken");
            OtherFragPresenter otherFragPresenter = new OtherFragPresenter(this);
            otherFragPresenter.sendUserDataForFrogList(username, authToken);
        }
        else
        {
            rLayProgressBarOther.setVisibility(View.VISIBLE);
        }

    }


    public void getAllFrogsReturn(List<AllFrogsResponse.Response> responsePojo)
    {
        rLayProgressBarOther.setVisibility(View.GONE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        mLayoutManager.setReverseLayout(true);
//        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItemAdapter = new ItemAdapter(responsePojo);
        mRecyclerView.setAdapter(mItemAdapter);
        notifyNewValueAdded();
    }

    @Override
    public void showUserDataForFrogList(String message, List<AllFrogsResponse.Response> responsePojo, int streakLength,int totalScore,String nextWalkDate) {

//        Log.i("messageNew", message);
       if(message.equals("success"))
       {
           getAllFrogsReturn(responsePojo);
           if (responsePojo.size() == 0)
           {
               errorMsg.setText(getResources().getString(R.string.add_new_frogs));
               rLayProgressBarOther.setVisibility(View.VISIBLE);
           }
           else
           {
//               Log.e("respTotal", streakLength + " " + totalScore + " " + nextWalkDate + " " + responsePojo.get(0).getDate());
               SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
               SharedPreferences.Editor ed = sharedPrefs.edit();
               ed.putInt(STREAKLENGTH, streakLength);
               ed.putInt(TOTALSCORE, totalScore);
               ed.putString(NEXTWALKDATE, nextWalkDate);
               ed.apply();
           }
       }
        else
       {
           errorMsg.setText(getResources().getString(R.string.something_wrong));
           rLayProgressBarOther.setVisibility(View.VISIBLE);
       }
//        Log.i("responseValue2", responsePojo.get(0).getId() + " : is the 1st response id");
    }

    public void notifyNewValueAdded()
    {
        //To remove the notification of no frogs in the list...
        if(isNotZero)
        {
            rLayProgressBarOther.setVisibility(View.GONE);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser)
        {
//            Log.i("vis","visible" );
            notifyNewValueAdded();
//            mLayoutManager.scrollToPosition(0);
        }
    }
}
