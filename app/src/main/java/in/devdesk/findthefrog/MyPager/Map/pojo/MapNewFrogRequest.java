package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 10/07/17.
 */

public class MapNewFrogRequest  implements Serializable {
    @Expose
    private String username;
    @Expose
    private double lat;
    @Expose
    private double lng;

    @Expose
    private String locationName;

    public MapNewFrogRequest(String username, double lat, double lng, String reverseGeoString) {
        this.username = username;
        this.lat = lat;
        this.lng = lng;
        locationName = reverseGeoString;
    }

    public String getUsername() {
        return username;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getReverseGeoString() {
        return locationName;
    }
}
