package in.devdesk.findthefrog.HeLpEr;

/**
 * Created by richardandrews on 06/07/17.
 */

public class Constants {

    public static final class HTTP {
//        public static final String  BASE_URL = "http://10.0.2.2:3050";
        public static final String BASE_URL = "https://findafrog.herokuapp.com";
    }

    public static final class DATABASE {

    }
    public static final class REFERENCE {
        public static final String  LOGINSHAREDP = Config.PACKAGE_NAME + "loginSharedPreference";
        public static final String USERNAME = "username";
        public static final String AUTHTOKEN = "authToken";
        public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String STREAKLENGTH = "streakLength";
        public static final String TOTALSCORE = "totalScore";
        public static final String NEXTWALKDATE = "nextWalkDate";
    }

    public static final class Config {
        public static final String  PACKAGE_NAME = "in.devdesk.findthefrog.";
    }
}
