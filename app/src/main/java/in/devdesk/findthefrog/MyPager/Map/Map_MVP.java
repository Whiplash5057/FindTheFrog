package in.devdesk.findthefrog.MyPager.Map;

/**
 * Created by richardandrews on 09/07/17.
 */

public interface Map_MVP {
    public interface Model
    {
        public void acceptLatLngToApi(double lat, double lng, String username);

        public void acceptFrogLatLngToApi(double lat, double lng, String username);
    }
    public interface Presenter
    {
        public void sendLatLngToApi(double lat, double lng, String username);
        public void returnLatLngResponse(double lat, double lng, String message);

        public void sendnewFrogLatLngToApi(double lat, double lng, String username);
        public void returnFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId);
    }
    public interface View
    {
        public void showResponseValue(double lat, double lng, String message);

        public void showFrogLatLngResponse(double lat, double lng, String message, String newFrogLocationId);
    }

}
