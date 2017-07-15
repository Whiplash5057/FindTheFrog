package in.devdesk.findthefrog.MyPager.Map;


import java.util.List;

import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapFragPresenter implements Map_MVP.Presenter{

    MapFragment mapFragment;
    public MapFragPresenter(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    @Override
    public void sendLatLngToApi(double lat, double lng, String username) {
        MapFragModel mapFragModel = new MapFragModel(this);
        mapFragModel.acceptLatLngToApi(lat, lng, username);
    }

    @Override
    public void returnLatLngResponse(double lat, double lng, String message) {
        mapFragment.showResponseValue(lat, lng, message);
    }

    @Override
    public void sendnewFrogLatLngToApi(double lat, double lng, String username, String reverseGeoString) {
        MapFragModel mapFragModel = new MapFragModel(this);
//        Log.i("LAtt", reverseGeoString);
        mapFragModel.acceptFrogLatLngToApi(lat, lng, username, reverseGeoString);
    }

    @Override
    public void returnFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId, String locationName, String dateTime) {
//        Log.d("Latiti", "pres" + lat);
//        Log.d("Longigi", "pres" + lng);
        mapFragment.showFrogLatLngResponse(lat, lng, message, newFrogLocationId, locationName, dateTime);

    }

    @Override
    public void sendFrogFoundToApi(String markerId, String username) {
        MapFragModel mapFragModel = new MapFragModel(this);
        mapFragModel.acceptFrogFoundToApi(markerId, username);
    }

    @Override
    public void returnFrogFoundResponse(String message, String markerId, int streakLength, int totalScore) {
        mapFragment.showFrogFoundResponse(message, markerId, streakLength, totalScore);
    }

    @Override
    public void sendUserDataForFrogList(String username, String authToken) {
        MapFragModel mapFragModel = new MapFragModel(this);
        mapFragModel.acceptUserDataForFrogList(username, authToken);
    }

    @Override
    public void returnUserDataForFrogListResponse(String message, List<AllFrogsResponse.Response> responsePojo) {
        mapFragment.showUserDataForFrogList(message, responsePojo);
    }
}
