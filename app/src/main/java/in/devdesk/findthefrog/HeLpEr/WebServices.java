package in.devdesk.findthefrog.HeLpEr;

import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginRequest;
import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by richardandrews on 08/07/17.
 */

public interface WebServices {

    @POST("api/login")
    Call<LoginResponse.MainPojo> loginAccount(@Body LoginRequest loginRequest);

}
