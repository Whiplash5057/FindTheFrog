package in.devdesk.findthefrog.LoginSignUp.Login.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by richardandrews on 07/07/17.
 */

public class LoginResponse{


    public class MainPojo implements Serializable{
        @Expose
        private String message;
        @Expose
        private String status;
        @Expose
        private ResponsePojo response;

        public ResponsePojo getResponse() {
            return response;
        }

        public void setResponse(ResponsePojo response) {
            this.response = response;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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


/**
 {
 "message": "success",
 "status": "success",
 "response": {
 "authToken": "$2a$10$X0CrBk6UHy8w8jfvkMkur.jA3LhJTA2Ggpqh30FyWnQRqL0PGVVYO595f9e9daff25a49e35f8987"
 }
 }
 */
}