package in.devdesk.findthefrog.MyPager.Other.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 11/07/17.
 */

public class AllFrogsRequest implements Serializable {
    @Expose
    private String username;
    @Expose
    private String authToken;

    public AllFrogsRequest(String username, String authToken) {
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }
}
