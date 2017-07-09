package in.devdesk.findthefrog.MyPager.Map;

import android.location.LocationListener;
import android.util.Log;

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
}
