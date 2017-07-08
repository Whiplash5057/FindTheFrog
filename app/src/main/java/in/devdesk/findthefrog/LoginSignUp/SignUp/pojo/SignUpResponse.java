package in.devdesk.findthefrog.LoginSignUp.SignUp.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;

/**
 * Created by richardandrews on 09/07/17.
 */

public class SignUpResponse implements Serializable {
    public class MainPojo implements Serializable{
        @Expose
        private String message;
        @Expose
        private String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public LoginResponse.ResponsePojo getResponse() {
            return response;
        }

        public void setResponse(LoginResponse.ResponsePojo response) {
            this.response = response;
        }

        @Expose
        private LoginResponse.ResponsePojo response;
    }

    public class ResponsePojo implements Serializable{
        @Expose
        private String authToken;

        public String getAuthToken() {
            return authToken;
        }

        public void setAuthToken(String authToken) {
            this.authToken = authToken;
        }
    }
}


/**
 * {
 * "status": "failure",
 * "message": "Username already exists",
 * "response": {
 * "authToken": ""
 * }
 * }
 * <p>
 * {
 * "message": "success",
 * "status": "success",
 * "response": {
 * "authToken": "$2a$10$qFNZIqjwFk.von0Xz1oAjOjnirN5tXpPYjZ6jLO15a9UlPqLiTfgG596137d43336eb26faa11a9e"
 * }
 * }
 */
