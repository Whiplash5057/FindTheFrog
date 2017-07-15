package in.devdesk.findthefrog.HeLpEr;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


//import static in.devdesk.findthefrog.MyPager.Map.MapFragment.toCountMarkerValidId;
//import static in.devdesk.findthefrog.MyPager.Map.MapFragment.toRecognizeMarkerPosRcyclView;

/**
 * Created by richardandrews on 01/07/17.
 */

public class MapHelperClasses {

    private static HashMap<String ,Marker> hashMapMarker = new HashMap<>();
    Marker marker;
    private String TAG = MapHelperClasses.class.getSimpleName();

    public CircleOptions circleCreation(int alpha, int r, int g, int b, int alpha2, int radius, Double latitude, Double longitude)
    {
        return new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(radius)
                .strokeColor(Color.argb(alpha,r,g,b))
                .fillColor(Color.argb(alpha2,r,g,b));
    }

    public GoogleMap userMarker(GoogleMap mGoogleMaps, Context context, Double latitude, Double longitude, int markerType, MarkerOptions markerOptions, String locationKeyString)
    {

        switch (markerType){
            case 1:  // home/base
                mGoogleMaps.addMarker(markerOptions)
                        .showInfoWindow();
            break;
            case 2:  // current location


                if(hashMapMarker.get("currentLocation") != null)
                {
                    marker = hashMapMarker.get("currentLocation");
                    marker.remove();
                    hashMapMarker.remove("currentLocation");
                }
                marker = mGoogleMaps.addMarker(markerOptions);
//                marker.setTag();
                hashMapMarker.put("currentLocation",marker);
                marker.showInfoWindow();
            break;
            case 3:  // new target incomplete challange


                if(hashMapMarker.get(locationKeyString) != null)
                {
                    marker = hashMapMarker.get(locationKeyString);
                    marker.remove();
                    hashMapMarker.remove(locationKeyString);
                }
                marker = mGoogleMaps.addMarker(markerOptions);
//                toCountMarkerValidId += 1;
//                toRecognizeMarkerPosRcyclView.put(marker, toCountMarkerValidId);
                hashMapMarker.put(locationKeyString,marker);
                marker.showInfoWindow();
                marker.setTag(locationKeyString);
//                marker = mGoogleMaps.addMarker(markerOptions);
//                mGoogleMaps.addMarker(markerOptions)
//                        .showInfoWindow();
            break;
            case 4:  // new target complete challange

                if(hashMapMarker.get(locationKeyString) != null)
                {
                    marker = hashMapMarker.get(locationKeyString);
                    marker.remove();
                    hashMapMarker.remove(locationKeyString);
                }
                marker = mGoogleMaps.addMarker(markerOptions);
                hashMapMarker.put(locationKeyString,marker);
                marker.showInfoWindow();
                marker.setTag(locationKeyString);

//                marker = mGoogleMaps.addMarker(markerOptions);
//                mGoogleMaps.addMarker(markerOptions)
//                        .showInfoWindow();
            break;
            case 6:  // just add marker point for home
                mGoogleMaps.addMarker(markerOptions)
                        .showInfoWindow();
                break;
            case 7:  // just add marker points for unfinished locations

                if(hashMapMarker.get(locationKeyString) != null)
                {
                    marker = hashMapMarker.get(locationKeyString);
                    marker.remove();
                    hashMapMarker.remove(locationKeyString);
                }
                marker = mGoogleMaps.addMarker(markerOptions);
//                toCountMarkerValidId += 1;
//                toRecognizeMarkerPosRcyclView.put(marker, toCountMarkerValidId);
                hashMapMarker.put(locationKeyString,marker);
                marker.showInfoWindow();
                marker.setTag(locationKeyString);
//                mGoogleMaps.addMarker(markerOptions)
//                        .showInfoWindow();
                break;
            case 8:  // just add marker points for finished locations

                if(hashMapMarker.get(locationKeyString) != null)
                {
                    marker = hashMapMarker.get(locationKeyString);
                    marker.remove();
                    hashMapMarker.remove(locationKeyString);
                }
                marker = mGoogleMaps.addMarker(markerOptions);
//                toCountMarkerValidId += 1;
//                toRecognizeMarkerPosRcyclView.put(marker, toCountMarkerValidId);
                hashMapMarker.put(locationKeyString,marker);
                marker.showInfoWindow();
                marker.setTag(locationKeyString);
//                mGoogleMaps.addMarker(markerOptions)
//                        .showInfoWindow();
                break;
            default:
            break;
        }


        return mGoogleMaps;
    }

    public void purpleRedYellowGreen(GoogleMap mGoogleMaps, Double latitude, Double longitude) {

        Circle circle = mGoogleMaps.addCircle(circleCreation(100,147,196,125, 100, 200, latitude, longitude));
        Circle circleTwo = mGoogleMaps.addCircle(circleCreation(100,255,217,102, 60, 400, latitude, longitude));
        Circle circleThree = mGoogleMaps.addCircle(circleCreation(100,234,153,153, 30, 800, latitude, longitude));
        Circle circleFour = mGoogleMaps.addCircle(circleCreation(100,180,167,214, 15, 1200, latitude, longitude));
    }

    public String getGeoLocation(Context context, Double latitude, Double longitude)
    {
        String ADDRESS = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(listAddresses != null && listAddresses.size() > 0)
            {
                String addressLine,country, location;
//                Log.i(TAG, listAddresses.get(0).toString());
                addressLine = listAddresses.get(0).getAddressLine(0); //ROAD
                country = listAddresses.get(0).getCountryName();
                location = listAddresses.get(0).getLocality(); //MUMBAI
                if(addressLine != null && addressLine.length() > 0)
                    ADDRESS += addressLine + ", ";
                if(location != null && location.length() > 0)
                    ADDRESS += location + ", ";
                if(country != null && country.length() > 0)
                    ADDRESS += country;
                return ADDRESS;
            } else{
                return ADDRESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ADDRESS;
        }
    }
}
