package in.devdesk.findthefrog.MyPager.Other.pojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllFrogsResponse implements Serializable
{

    @SerializedName("totalScore")
    @Expose
    private Integer totalScore;
    @SerializedName("streakLength")
    @Expose
    private Integer streakLength;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("response")
    @Expose
    private List<Response> response = null;
    @SerializedName("nextWalkDate")
    @Expose
    private String nextWalkDate;
    private final static long serialVersionUID = 3773697023054582843L;

    /**
     * No args constructor for use in serialization
     *
     */
    public AllFrogsResponse() {
    }

    /**
     *
     * @param response
     * @param message
     * @param totalScore
     * @param streakLength
     * @param nextWalkDate
     */
    public AllFrogsResponse(Integer totalScore, Integer streakLength, String message, List<Response> response, String nextWalkDate) {
        super();
        this.totalScore = totalScore;
        this.streakLength = streakLength;
        this.message = message;
        this.response = response;
        this.nextWalkDate = nextWalkDate;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getStreakLength() {
        return streakLength;
    }

    public void setStreakLength(Integer streakLength) {
        this.streakLength = streakLength;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getNextWalkDate() {
        return nextWalkDate;
    }

    public void setNextWalkDate(String nextWalkDate) {
        this.nextWalkDate = nextWalkDate;
    }


    public static class Response implements Serializable
    {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("__v")
        @Expose
        private Integer v;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("isValid")
        @Expose
        private Boolean isValid;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("isComplete")
        @Expose
        private Boolean isComplete;
        @SerializedName("locationName")
        @Expose
        private String locationName;
        @SerializedName("coordinates")
        @Expose
        private List<Double> coordinates = null;
        private final static long serialVersionUID = -461538422579751488L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Response() {
        }

        /**
         *
         * @param id
         * @param v
         * @param isComplete
         * @param username
         * @param locationName
         * @param date
         * @param isValid
         * @param coordinates
         */
        public Response(String id, Integer v, String username, Boolean isValid, String date, Boolean isComplete, String locationName, List<Double> coordinates) {
            super();
            this.id = id;
            this.v = v;
            this.username = username;
            this.isValid = isValid;
            this.date = date;
            this.isComplete = isComplete;
            this.locationName = locationName;
            this.coordinates = coordinates;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Boolean getIsValid() {
            return isValid;
        }

        public void setIsValid(Boolean isValid) {
            this.isValid = isValid;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Boolean getIsComplete() {
            return isComplete;
        }

        public void setIsComplete(Boolean isComplete) {
            this.isComplete = isComplete;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }

    }

}