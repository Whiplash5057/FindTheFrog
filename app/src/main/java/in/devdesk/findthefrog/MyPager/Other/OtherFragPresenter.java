package in.devdesk.findthefrog.MyPager.Other;

import android.util.Log;

import java.util.List;

import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;

/**
 * Created by richardandrews on 11/07/17.
 */

public class OtherFragPresenter implements Other_MVP.Presenter {

    private OtherFragment otherFragment;

    public OtherFragPresenter(OtherFragment otherFragment) {
        this.otherFragment = otherFragment;
    }

    @Override
    public void sendUserDataForFrogList(String username, String authToken) {
        OtherFragModel otherFragModel = new OtherFragModel(this);
        otherFragModel.acceptUserDataForFrogList(username, authToken);
    }

    @Override
    public void returnUserDataForFrogListResponse(String message, List<AllFrogsResponse.Response> responsePojo, int streakLength,int totalScore,String nextWalkDate) {
//        Log.i("responseValue1", responsePojo.get(0).getId() + " : is the 1st response id");
        otherFragment.showUserDataForFrogList(message, responsePojo, streakLength, totalScore, nextWalkDate);
    }
}
