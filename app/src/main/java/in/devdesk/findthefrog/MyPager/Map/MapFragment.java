package in.devdesk.findthefrog.MyPager.Map;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import in.devdesk.findthefrog.HeLpEr.GeocodingLocation;
import in.devdesk.findthefrog.HeLpEr.MapHelperClasses;
import in.devdesk.findthefrog.HeLpEr.Utils;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LATITUDE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LONGITUDE;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.MY_PERMISSIONS_REQUEST_LOCATION;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.USERNAME;
import static in.devdesk.findthefrog.HeLpEr.Utils.isNetworkAvailable;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, Map_MVP.View {

    //-- blind strings
    private GoogleMap mGoogleMaps;
    private MapHelperClasses mapHelperClasses;
    Animation animPopupEnter;
    Animation animPopupExit;
    private String homeLocn;
    String responseMessage;
    Snackbar snackbar;
    private LocationManager locationManager;
    private Boolean locationCheck = true;
    private LocationListener locationListener;
    HashMap<String, Double> latLong = new HashMap<>();

    //-- data binding
    @BindView(R.id.mapv_map_mapfrag)
    MapView mMapView;

    @BindView(R.id.setHomeValue)
    LinearLayout setHomeLayout;

    @BindView(R.id.btn_map_homeloc)
    Button setHomeBtn;

    @BindView(R.id.et_map_homeloc)
    EditText homeLocationEditText;

    @BindView(R.id.et_map_address)
    EditText locationEditText;


    //-- onClicks
    @OnClick(R.id.btn_map_homeloc)
    public void homeLocationSearch(View view)
    {
        homeLocn = homeLocationEditText.getText().toString();
        if(homeLocn.length() > 0)
        {
            GeocodingLocation locationAddress = new GeocodingLocation();
            locationAddress.getAddressFromLocation(homeLocn,
                    getActivity(), new GeocoderHandler());
//            Toast.makeText(getActivity(), homeLocn, Toast.LENGTH_SHORT).show();

        }
    }

    @OnClick(R.id.btn_map_shuffle)
    public void homeCurrentLocationSwitch(View view)
    {
        String buttonState = (String) view.getTag();
//        Toast.makeText(getActivity(), buttonState, Toast.LENGTH_SHORT).show();

        if(buttonState == getResources().getString(R.string.home_location)) // home
        {
            view.setTag(getResources().getString(R.string.current_location));
            setHomeLayout.setVisibility(View.GONE);
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
            double latitude = Utils.getDouble(sharedPrefs, LATITUDE, 0);
            double longitude = Utils.getDouble(sharedPrefs, LONGITUDE, 0);
            setUpMapLocation(latitude, longitude, 0);
        }
        else
        {
            view.setTag(getResources().getString(R.string.home_location));
            initializeMap();
        }
    }

    @OnTextChanged(R.id.et_map_address)
    public void setFrogLocation(Editable s)
    {
        if(s.length() > 0)
        {
            String address = locationEditText.getText().toString();
            GeocodingLocation locationAddress = new GeocodingLocation();
            locationAddress.getAddressFromLocation(address,
                    getActivity(), new GeocoderHandler());
        }

    }

    //-- methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //-- animation setup
        animPopupEnter= AnimationUtils.loadAnimation(getActivity(),R.anim.bounce_animation);
        animPopupExit = AnimationUtils.loadAnimation(getActivity(),R.anim.unbouncing_animation);
        setHomeLayout.setAlpha(0);


        initializeViews();
    }

    private void initializeViews() {
//        setHomeLayout.setTranslationY(800f);
        mapHelperClasses = new MapHelperClasses();
        if (mMapView != null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //-- initialise map attributes
//        LatLng sydney = new LatLng(-34, 151);
//        mGoogleMaps.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mGoogleMaps.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mGoogleMaps = googleMap;
        mGoogleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMaps.setBuildingsEnabled(true);
        mGoogleMaps.setIndoorEnabled(true);
        initializeMapListener();

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
        if(!sharedPrefs.contains(LATITUDE))
        {
            //-- animation
            setHomeLayout.setAlpha(1);
            setHomeLayout.startAnimation(animPopupEnter);
        }
        else
        {
            setHomeLayout.setVisibility(View.GONE);
            double latitude = Utils.getDouble(sharedPrefs, LATITUDE, 0);
            double longitude = Utils.getDouble(sharedPrefs, LONGITUDE, 0);
            setUpMapLocation(latitude, longitude, 6);
        }


        //-- initialise the map
        initializeMap();

    }

    private void initializeMapListener() {

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latLong.put("latitude", location.getLatitude());
                latLong.put("longitude", location.getLongitude());
                setUpMapLocation(latLong.get("latitude"), latLong.get("longitude"), 2);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}

        };

        mGoogleMaps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                setUpMapLocation(latLng.latitude, latLng.longitude, 0);

            }
        });

        mGoogleMaps.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
                String username = sharedPrefs.getString(USERNAME, "wrongUser");
                if(!sharedPrefs.contains(LATITUDE) && isNetworkAvailable(getActivity()))
                {


                    setUpMapLocation(latLng.latitude, latLng.longitude, 1);
                    updateHomeLocationInServer(latLng.latitude, latLng.longitude, username);
                }
                else
                {
//                    Toast.makeText(getActivity(), Utils.getDouble(sharedPrefs, LATITUDE, 000) + "is the latitude", Toast.LENGTH_SHORT).show();
//                    double latitude = Utils.getDouble(sharedPrefs, LATITUDE, 0);
//                    double longitude = Utils.getDouble(sharedPrefs, LONGITUDE, 0);
//                    setUpMapLocation(latitude, longitude, 6);
                    setUpMapLocation(latLng.latitude, latLng.longitude, 3);
                    addNewFrogLocationInServer(latLng.latitude, latLng.longitude, username);
                }

            }
        });

    }

    private void addNewFrogLocationInServer(double latitude, double longitude, String username) {
        Log.i("LAt", latitude +" is the lat");
        MapFragPresenter loginFragPresenter = new MapFragPresenter(this);
        loginFragPresenter.sendnewFrogLatLngToApi( latitude, longitude, username);
    }

    private void updateHomeLocationInServer(double latitude, double longitude, String username) {
        MapFragPresenter loginFragPresenter = new MapFragPresenter(this);
        loginFragPresenter.sendLatLngToApi( latitude, longitude, username);
    }

    private void initializeMap() {
        Boolean isGreaterThanMM = Utils.isGreaterThanMarshMeslo();
        if(isGreaterThanMM)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        else
        {
            if(ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
//                Toast.makeText(getActivity(), "deny", Toast.LENGTH_SHORT).show();
                locationCheck = false;
            }
            else
            {
//                Toast.makeText(getActivity(), "accept", Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                setUpMapLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 2);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!locationCheck)
            initializeMap();
    }

    private void setUpMapLocation(Double latitude, Double longitude, int locationType) {
        CameraPosition Liberty;

        // 1 - home / base
        MarkerOptions markerOptions;
        // 2. - current location
        // 3. - incomplete walk
        // 4. - complete walk
        // 6. - just add marker point for home
        // 7. - just add marker points for unfinished locations
        // 8. - just add marker points for finished locations
        // def- plain search

        switch (locationType) {
            case 1:
                mapHelperClasses.purpleRedYellowGreen(mGoogleMaps, latitude, longitude);
                markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.user_home_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home));
                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , locationType, markerOptions);
                Liberty = CameraPosition.builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(17).bearing(0)
                        .tilt(85).build();
                break;
            case 2:  // current location
                markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.user_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder_map));


                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , 2, markerOptions);
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(17).bearing(0).tilt(85).build();
                break;
            case 3:  // incomplete walk
                markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.incomplete_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.frog_unfound));
                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , locationType, markerOptions);
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(19).bearing(0).tilt(85).build();
                break;
            case 4:  // complete walk
                markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.complete_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.frog_found));
                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , locationType, markerOptions);
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(17).bearing(0).tilt(85).build();
                break;
            case 6:
                mapHelperClasses.purpleRedYellowGreen(mGoogleMaps, latitude, longitude);
                markerOptions = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.user_home_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home));
                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , locationType, markerOptions);
                Liberty = CameraPosition.builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(17).bearing(0)
                        .tilt(85).build();  //not usefull for this case.
                break;
            default:
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(17).bearing(0).tilt(85).build();
                break;
        }


        if(locationType < 6)
        {
            mGoogleMaps.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
        }


    }

    @Override
    public void showResponseValue(double lat, double lng, String message) {

        if(message == "success")
        {
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPrefs.edit();
            Utils.putDouble(ed, LATITUDE, lat);
            Utils.putDouble(ed, LONGITUDE, lng);
            ed.apply();
            responseMessage = "Awesome, you have a home in the app:-)";
            setHomeLayout.startAnimation(animPopupExit);
            animPopupExit.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    setHomeLayout.setAlpha(0);
                    setHomeLayout.setVisibility(View.GONE);
                    snackbar = Snackbar.make(setHomeBtn , responseMessage, Snackbar.LENGTH_SHORT);
                    snackbar.addCallback(new Snackbar.Callback(){

                    });
                    View sb = snackbar.getView();
                    TextView tv = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ascentTextColor));
                    tv.setHeight(160);
                    tv.setTextSize(18);
                    snackbar.show();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else
        {
            responseMessage = "Sorry, something went wrong:-(";
        }

    }

    @Override
    public void showFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId) {
        if(message == "success")
        {
            SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
            Set<String> set = new HashSet<String>();
            set.add(Double.toString(lat));
            set.add(Double.toString(lng));
            SharedPreferences.Editor ed = sharedPrefs.edit();
            ed.putStringSet(newFrogLocationId, set);
            ed.apply();
//            Set<String> fetch = editor.getStringSet("List", null);
//            for(int i = 0 ; i < list.size() ; i++){
//            Log.d("fetching values", "fetch value " + list.get(i));
//              }

            responseMessage = "New frog added! You have 7 days to get it.";
        }
        else
        {
            responseMessage = "Sorry, something went wrong:-(";
        }
        snackbar = Snackbar.make(setHomeBtn , responseMessage, Snackbar.LENGTH_SHORT);
//        snackbar.addCallback(new Snackbar.Callback(){
//
//        });
        View sb = snackbar.getView();
        TextView tv = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ascentTextColor));
        tv.setHeight(160);
        tv.setTextSize(18);
        snackbar.show();
    }

    private class GeocoderHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            Double editTextLat, editTextLng;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    editTextLat = bundle.getDouble("latitude");
                    editTextLng = bundle.getDouble("longitude");
                    if(editTextLat != null && editTextLng != null)
                    {
//                        Toast.makeText(getActivity(), editTextLat.toString() + " " + editTextLng.toString(), Toast.LENGTH_SHORT).show();
                        setUpMapLocation(editTextLat, editTextLng, 0);
                    }

                    break;
                default:
                    locationAddress = null;
            }
//            bouncingTextMessage.setText(locationAddress);

        }
    }

}
