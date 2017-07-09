package in.devdesk.findthefrog.HeLpEr;

import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginRequest;
import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpRequest;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpResponse;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapHomeUpdateResponse;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogRequest;
import in.devdesk.findthefrog.MyPager.Map.pojo.MapNewFrogResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by richardandrews on 08/07/17.
 */

public interface WebServices {

    @POST("api/login")
    Call<LoginResponse.MainPojo> loginAccount(@Body LoginRequest loginRequest);

    @POST("api/newUser")
    Call<SignUpResponse.MainPojo> createAccount(@Body SignUpRequest signUpRequest);

    @PUT("api/updateHomeLocation")
    Call<MapHomeUpdateResponse> updateAccountLocation(@Body MapHomeUpdateRequest mapHomeUpdateRequest);

    @PUT("api/addNewLocation")
    Call<MapNewFrogResponse.MainPojo> addNewFrogLocation(@Body MapNewFrogRequest mapNewFrogRequest);

}
