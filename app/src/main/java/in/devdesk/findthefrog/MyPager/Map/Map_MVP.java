package in.devdesk.findthefrog.MyPager.Map;

import java.util.List;

import in.devdesk.findthefrog.MyPager.Other.pojo.AllFrogsResponse;

/**
 * Created by richardandrews on 09/07/17.
 */

public interface Map_MVP {
    public interface Model
    {
        public void acceptLatLngToApi(double lat, double lng, String username);

        public void acceptFrogLatLngToApi(double lat, double lng, String username, String reverseGeoString);

        public void acceptFrogFoundToApi(String markerId, String username);

        public void acceptUserDataForFrogList(String username, String authToken);
    }
    public interface Presenter
    {
        public void sendLatLngToApi(double lat, double lng, String username);
        public void returnLatLngResponse(double lat, double lng, String message);

        public void sendnewFrogLatLngToApi(double lat, double lng, String username, String reverseGeoString);
        public void returnFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId, String locationName, String dateTime);

        public void sendFrogFoundToApi(String markerId, String username);
        public void returnFrogFoundResponse(String message, String markerId, int streakLength, int totalScore);

        public void sendUserDataForFrogList(String username, String authToken);
        public void returnUserDataForFrogListResponse(String message, List<AllFrogsResponse.Response> responsePojo);

    }
    public interface View
    {
        public void showResponseValue(double lat, double lng, String message);

        public void showFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId, String locationName, String dateTime);

        public void showFrogFoundResponse(String message, String markerId, int streakLength, int totalScore);

        public void showUserDataForFrogList(String message, List<AllFrogsResponse.Response> responsePojo);
    }

}
