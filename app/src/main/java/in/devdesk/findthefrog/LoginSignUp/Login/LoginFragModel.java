package in.devdesk.findthefrog.LoginSignUp.Login;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginRequest;
import in.devdesk.findthefrog.LoginSignUp.Login.pojo.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 08/07/17.
 */

public class LoginFragModel implements Login_MVP.Model {

    private LoginFragPresenter loginFragPresenter;
    private RestManager mManager;
    private HashMap<String, String> loginDetailsModified = new HashMap<>();
    private LoginRequest loginRequest;

    public LoginFragModel(LoginFragPresenter loginFragPresenter) {
        this.loginFragPresenter = loginFragPresenter;
    }

    @Override
    public void getTestLoginValue(Object o) {

        loginDetailsModified = (HashMap<String, String>) o;
        mManager = new RestManager();
        loginRequest = new LoginRequest(loginDetailsModified.get("username"), loginDetailsModified.get("password"));
        callLoginWebService();
    }

    private void callLoginWebService() {

        Call<LoginResponse.MainPojo> responseCall = mManager.getmItemService().loginAccount(loginRequest);
        responseCall.enqueue(new Callback<LoginResponse.MainPojo>() {
            @Override
            public void onResponse(Call<LoginResponse.MainPojo> call, Response<LoginResponse.MainPojo> response) {

                if (response.isSuccessful())
                {
                    String authToken = response.body().getResponse().getAuthToken();
//                    Log.e("Success", authToken);
                    loginDetailsModified.put("response", "success");
                    loginDetailsModified.put("message", "Welcome!!");
                    loginDetailsModified.put("authToken", authToken);
                }
                else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            loginDetailsModified.put("response", "error");
                            loginDetailsModified.put("message", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            loginDetailsModified.put("response", "error");
                            loginDetailsModified.put("message", "Request Not Found");
                            break;
                        case 402:
                            Log.e("Error 402", "Enter your correct username and password'");
                            loginDetailsModified.put("response", "error");
                            loginDetailsModified.put("message", "Enter your correct username and password");
                            break;
                        default:
                            loginDetailsModified.put("response", "error");
                            loginDetailsModified.put("message", "Something went wrong");
                            Log.e("Error", "Generic Error");
                    }
                }
                loginFragPresenter.sendTestLoginViewBack(loginDetailsModified);
            }

            @Override
            public void onFailure(Call<LoginResponse.MainPojo> call, Throwable t) {
                loginDetailsModified.put("response", "error");
                loginDetailsModified.put("message", "error");
                loginFragPresenter.sendTestLoginViewBack(loginDetailsModified);
            }
        });
    }
}
