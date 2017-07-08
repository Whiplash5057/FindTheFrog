package in.devdesk.findthefrog.HeLpEr;

import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginRequest;
import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpRequest;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by richardandrews on 08/07/17.
 */

public interface WebServices {

    @POST("api/login")
    Call<LoginResponse.MainPojo> loginAccount(@Body LoginRequest loginRequest);

    @POST("api/newUser")
    Call<SignUpResponse.MainPojo> createAccount(@Body SignUpRequest signUpRequest);

}
