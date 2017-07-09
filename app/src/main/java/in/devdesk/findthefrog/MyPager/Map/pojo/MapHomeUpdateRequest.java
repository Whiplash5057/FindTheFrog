package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 09/07/17.
 */

public class MapHomeUpdateRequest implements Serializable {
    @Expose
    private String username;
    @Expose
    private double lat;
    @Expose
    private double lng;

    public MapHomeUpdateRequest(String username, double lat, double lng) {
        this.username = username;
        this.lat = lat;
        this.lng = lng;
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

}
