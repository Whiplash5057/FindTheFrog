package in.devdesk.findthefrog.MyPager.Map.pojo;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;

/**
 * Created by richardandrews on 10/07/17.
 */

public class MapNewFrogResponse{

    public class MainPojo implements Serializable{
        @Expose
        private String message;
        @Expose
        private String locationName;
        @Expose
        private String dateTime;

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        @Expose
        private MapNewFrogResponse.ResponsePojo response;

        public MapNewFrogResponse.ResponsePojo getResponse() {
            return response;
        }

        public void setResponse(MapNewFrogResponse.ResponsePojo response) {
            this.response = response;
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
        private String latLngId;

        public String getlatLngId() {
            return latLngId;
        }

        public void setlatLngId(String latLngId) {
            this.latLngId = latLngId;
        }


    }
}
