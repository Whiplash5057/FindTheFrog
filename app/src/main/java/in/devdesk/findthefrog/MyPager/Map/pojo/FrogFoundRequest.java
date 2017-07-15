package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 10/07/17.
 */

public class FrogFoundRequest implements Serializable {
    @Expose
    private String username;
    @Expose
    private String latLngId;

    public FrogFoundRequest(String markerId, String username) {
        this.username = username;
        this.latLngId = markerId;
    }

    public String getUsername() {
        return username;
    }

    public String getMarkerId() {
        return latLngId;
    }
}
