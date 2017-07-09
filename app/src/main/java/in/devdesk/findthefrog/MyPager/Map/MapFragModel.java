package in.devdesk.findthefrog.MyPager.Map;

import android.util.Log;

import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateResponse;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapFragModel implements Map_MVP.Model {

    private MapHomeUpdateRequest mapHomeUpdateRequest;
    private MapNewFrogRequest mapNewFrogRequest;
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

    @Override
    public void acceptFrogLatLngToApi(double lat, double lng, String username) {
        this.lat = lat;
        this.lng = lng;
        Log.i("LAt", lat +" is the lat");
        mManager = new RestManager();
        mapNewFrogRequest = new MapNewFrogRequest(username, lat, lng);
        callnewFrogLocationWebService();
    }

    private void callnewFrogLocationWebService() {
        Call<MapNewFrogResponse.MainPojo> responseCall = mManager.getmItemService().addNewFrogLocation(mapNewFrogRequest);
        responseCall.enqueue(new Callback<MapNewFrogResponse.MainPojo>() {
            String message;
            String newFrogLocationId;
            @Override
            public void onResponse(Call<MapNewFrogResponse.MainPojo> call, Response<MapNewFrogResponse.MainPojo> newResponse) {

                if (newResponse.isSuccessful())
                {
//                    Log.e("Success", newResponse.body().getMessage());
                    message = "success";
                    newFrogLocationId = newResponse.body().getResponse().getlatLngId();

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
                mapFragPresenter.returnFrogLatLngResponse(lat, lng, message, newFrogLocationId);
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
