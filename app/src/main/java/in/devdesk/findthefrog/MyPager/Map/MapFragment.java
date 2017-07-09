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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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


    //-- onClicks
    @OnClick(R.id.btn_map_homeloc)
    public void homeLocationClick(View view)
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

        //-- animation
        setHomeLayout.setAlpha(1);
        setHomeLayout.startAnimation(animPopupEnter);

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
                if(!sharedPrefs.contains(LATITUDE) && isNetworkAvailable(getActivity()))
                {

                    String username = sharedPrefs.getString(USERNAME, "wrongUser");
                    setUpMapLocation(latLng.latitude, latLng.longitude, 1);
                    updateHomeLocationInServer(latLng.latitude, latLng.longitude, username);
                }
                else
                {
                    Toast.makeText(getActivity(), Utils.getDouble(sharedPrefs, LATITUDE, 000) + "is the latitude", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
                Toast.makeText(getActivity(), "deny", Toast.LENGTH_SHORT).show();
                locationCheck = false;
            }
            else
            {
                Toast.makeText(getActivity(), "accept", Toast.LENGTH_SHORT).show();
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
        MarkerOptions markerOptionsHome;
        // 2. - current location
        // 3. - incomplete walk
        // 4. - complete walk
        // def- plain search

        switch (locationType) {
            case 1:
                mapHelperClasses.purpleRedYellowGreen(mGoogleMaps, latitude, longitude);
                markerOptionsHome = new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(getActivity().getString(R.string.user_home_locn))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.home));
                mGoogleMaps = mapHelperClasses.userMarker(mGoogleMaps, getActivity(), latitude, longitude , 2, markerOptionsHome);
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(17).bearing(0).tilt(85).build();
                break;
            default:
                Liberty = CameraPosition.builder().target(new LatLng(latitude, longitude)).zoom(17).bearing(0).tilt(85).build();
                break;
        }


        Toast.makeText(getActivity(), latitude + " " + longitude + " " + locationType, Toast.LENGTH_SHORT).show();
        mGoogleMaps.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

    @Override
    public void showResponseValue(double lat, double lng, String message) {
        String responseMessage;
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
        Snackbar snackbar = Snackbar.make(setHomeBtn , responseMessage, Snackbar.LENGTH_SHORT);
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
                        Toast.makeText(getActivity(), editTextLat.toString() + " " + editTextLng.toString(), Toast.LENGTH_SHORT).show();
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
