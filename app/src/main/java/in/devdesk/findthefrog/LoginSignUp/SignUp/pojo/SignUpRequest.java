package in.devdesk.findthefrog.LoginSignUp.SignUp.pojo;

import com.google.gson.annotations.Expose;

/**
 * Created by richardandrews on 09/07/17.
 */

public class SignUpRequest {
    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public SignUpRequest(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


}


/**
 * {
 * "email": "rachel@test.com",
 * "password": "rachel",
 * "username": "rachel",
 * "geometry": {
 * "type": "Point",
 * "coordinates": [12, 24]
 * }
 * }
 */