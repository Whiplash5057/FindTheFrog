package in.devdesk.findthefrog.MyPager.Map;

import android.content.SharedPreferences;
import android.util.Log;

import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapFragModel implements Map_MVP.Model {

    private MapHomeUpdateRequest mapHomeUpdateRequest;
    private RestManager mManager;
    private double lat, lng;

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
