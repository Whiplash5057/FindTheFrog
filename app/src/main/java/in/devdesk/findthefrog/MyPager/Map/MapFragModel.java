package in.devdesk.findthefrog.MyPager.Map;

import android.util.Log;

import java.util.List;

import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.MyPager.Map.pojo.FrogFoundRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.FrogFoundResponse;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateResponse;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogResponse;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsRequest;
import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapFragModel implements Map_MVP.Model {

    private MapHomeUpdateRequest mapHomeUpdateRequest;
    private MapNewFrogRequest mapNewFrogRequest;
    private FrogFoundRequest frogFoundRequest;
    private AllFrogsRequest allFrogsRequest;
    private RestManager mManager;
    private double lat, lng;
    private String markerId;

    MapFragPresenter mapFragPresenter;
    public MapFragModel(MapFragPresenter mapFragPresenter) {
        this.mapFragPresenter = mapFragPresenter;
    }

    @Override
    public void acceptLatLngToApi(double lat, double lng, String username) {
        this.lat = lat;
        this.lng = lng;
//        Log.i("latLng", lat + " " + "" + lng + username);
        mManager = new RestManager();
        mapHomeUpdateRequest = new MapHomeUpdateRequest(username, lat, lng);
        callLocationUpdateWebService();
    }

    @Override
    public void acceptFrogLatLngToApi(double lat, double lng, String username, String reverseGeoString) {
//        Log.i("LAt", lat +" is the lat & " + lng + " is the lng");
//        Log.i("LAttt", reverseGeoString);
        this.lat = lat;
        this.lng = lng;
//        this.reverseGeoString = reverseGeoString;
        mManager = new RestManager();
        mapNewFrogRequest = new MapNewFrogRequest(username, lat, lng, reverseGeoString);
        callnewFrogLocationWebService();
    }

    @Override
    public void acceptFrogFoundToApi(String markerId, String username) {
        this.markerId = markerId;
        mManager = new RestManager();
        frogFoundRequest = new FrogFoundRequest(markerId, username);
        callFrogFoundWebService();
//        Log.i("MARKERid", markerId);
    }

    @Override
    public void acceptUserDataForFrogList(String username, String authToken) {
        Log.e("User2", "user: " + username + " " + authToken);
        mManager = new RestManager();
        allFrogsRequest = new AllFrogsRequest(username, authToken);
        callGetAllFrogs();
    }




    //calls

    private void callGetAllFrogs() {
        Call<AllFrogsResponse> responseCall = mManager.getmItemService().getAllFrogs(allFrogsRequest);
        responseCall.enqueue(new Callback<AllFrogsResponse>() {
            String message;
            List<AllFrogsResponse.Response> responsePojo;
            @Override
            public void onResponse(Call<AllFrogsResponse> call, Response<AllFrogsResponse> response) {

                if (response.isSuccessful())
                {
                    message = response.body().getMessage();
                    responsePojo = response.body().getResponse();
//                    Log.i("responseValue", responsePojo.get(0).getId() + " : is the 1st response id");
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

                mapFragPresenter.returnUserDataForFrogListResponse(message, responsePojo);
            }

            @Override
            public void onFailure(Call<AllFrogsResponse> call, Throwable t) {
                Log.i("responseNew", "error");
            }
        });
    }

    private void callFrogFoundWebService() {
        Call<FrogFoundResponse> responseCall = mManager.getmItemService().updateNewFrogFound(frogFoundRequest);
        responseCall.enqueue(new Callback<FrogFoundResponse>() {
            String message;
            int totalScore;
            int streakLength;

            @Override
            public void onResponse(Call<FrogFoundResponse> call, Response<FrogFoundResponse> newResponse) {

//                Log.e("messageNew", newResponse.body().getMessage());
                if (newResponse.isSuccessful())
                {
                    message = "success";
                    streakLength = newResponse.body().getStreakLength();
                    totalScore = newResponse.body().getTotalScore();
                    Log.e("Success", streakLength + " " + totalScore);

                }
                else {
                    int sc = newResponse.code();
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
                mapFragPresenter.returnFrogFoundResponse(message, markerId, streakLength, totalScore);
            }

            @Override
            public void onFailure(Call<FrogFoundResponse> call, Throwable t) {
                Log.e("messageNew", "Something went wrong");
            }
        });
    }

    private void callnewFrogLocationWebService() {
        Call<MapNewFrogResponse.MainPojo> responseCall = mManager.getmItemService().addNewFrogLocation(mapNewFrogRequest);
        responseCall.enqueue(new Callback<MapNewFrogResponse.MainPojo>() {
            String message;
            String newFrogLocationId;
            String locationName;
            String dateTime;
            @Override
            public void onResponse(Call<MapNewFrogResponse.MainPojo> call, Response<MapNewFrogResponse.MainPojo> newResponse) {

                if (newResponse.isSuccessful())
                {
                    Log.e("Success", newResponse.body().getLocationName() + " " + newResponse.body().getDateTime());
                    message = "success";
                    newFrogLocationId = newResponse.body().getResponse().getlatLngId();
                    locationName = newResponse.body().getLocationName();
                    dateTime = newResponse.body().getDateTime();
                }
                else {
                    int sc = newResponse.code();
                    switch (sc) {
                        case 400:
//                            Log.e("Error 400", "Bad Request");

                            break;
                        case 404:
//                            Log.e("Error 404", "Not Found");

                            break;
                        case 402:
//                            Log.e("Error 402", "Enter your correct username and password'");

                            break;
                        default:
//                            Log.e("Error", "Generic Error");
                            break;
                    }
                    message = "error";
                    newFrogLocationId= "";
                }
//                Log.e("latilog", "model1" + lat);
//                Log.e("longilat", "model1" + lng);
                mapFragPresenter.returnFrogLatLngResponse(lat, lng, message, newFrogLocationId, locationName, dateTime);
            }

            @Override
            public void onFailure(Call<MapNewFrogResponse.MainPojo> call, Throwable t) {
                Log.e("Error", "Something badly went wrong");
            }
        });
    }

    private void callLocationUpdateWebService() {
        Call<MapHomeUpdateResponse> responseCall = mManager.getmItemService().updateAccountLocation(mapHomeUpdateRequest);
        responseCall.enqueue(new Callback<MapHomeUpdateResponse>() {
            String message;
            @Override
            public void onResponse(Call<MapHomeUpdateResponse> call, Response<MapHomeUpdateResponse> response) {
                if (response.isSuccessful())
                {
//                    Log.e("Success", response.body().getMessage());
                    message = "success";
                }
                else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
//                            Log.e("Error 400", "Bad Request");

                            break;
                        case 404:
//                            Log.e("Error 404", "Not Found");

                            break;
                        case 402:
//                            Log.e("Error 402", "Enter your correct username and password'");

                            break;
                        default:
//                            Log.e("Error", "Generic Error");
                            break;
                    }
                    message = "error";
                }
                mapFragPresenter.returnLatLngResponse(lat, lng, message);
            }

            @Override
            public void onFailure(Call<MapHomeUpdateResponse> call, Throwable t) {
                Log.e("Error", "Something badly went wrong");
            }
        });
    }
}
