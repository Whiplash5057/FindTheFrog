package in.devdesk.findthefrog.MyPager.Other;

import android.util.Log;
import java.util.List;
import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateRequest;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsRequest;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 11/07/17.
 */

public class OtherFragModel implements Other_MVP.Model {

    private AllFrogsRequest allFrogsRequest;
    private RestManager mManager;
    private OtherFragPresenter otherFragPresenter;

    public OtherFragModel(OtherFragPresenter otherFragPresenter) {
        this.otherFragPresenter = otherFragPresenter;
    }

    @Override
    public void acceptUserDataForFrogList(String username, String authToken) {
//        Log.e("User2", "user: " + username + " " + authToken);
        mManager = new RestManager();
        allFrogsRequest = new AllFrogsRequest(username, authToken);
        callGetAllFrogs();
    }

    private void callGetAllFrogs() {
        Call<AllFrogsResponse> responseCall = mManager.getmItemService().getAllFrogs(allFrogsRequest);
        responseCall.enqueue(new Callback<AllFrogsResponse>() {
            String message;
            int streakLength;
            int totalScore;
            String nextWalkDate;
            List<AllFrogsResponse.Response> responsePojo;
            @Override
            public void onResponse(Call<AllFrogsResponse> call, Response<AllFrogsResponse> response) {

                if (response.isSuccessful())
                {
                    message = response.body().getMessage();
                    responsePojo = response.body().getResponse();

                    streakLength = response.body().getStreakLength();
                    totalScore = response.body().getTotalScore();
                    nextWalkDate = response.body().getNextWalkDate();
//                    Log.i("responseValue", response.body().getStreakLength() + " : is the message");
                }
                else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");

                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");

                            break;
                        case 402:
                            Log.e("Error 402", "Enter your correct username and password'");

                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            break;
                    }
                    message = "error";
                }

                otherFragPresenter.returnUserDataForFrogListResponse(message, responsePojo, streakLength, totalScore, nextWalkDate);
            }

            @Override
            public void onFailure(Call<AllFrogsResponse> call, Throwable t) {
                Log.i("responseNew", "error");
            }
        });
    }
}
