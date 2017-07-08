package in.devdesk.findthefrog.LoginSignUp.SignUp;

import android.util.Log;

import java.util.HashMap;

import in.devdesk.findthefrog.HeLpEr.RestManager;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpRequest;
import in.devdesk.findthefrog.LoginSignUp.SignUp.pojo.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by richardandrews on 09/07/17.
 */

public class SignUpFragModel implements SignUp_MVP.Model{
    private SignUpFragPresenter signUpFragPresenter;
    private RestManager mManager;
    private HashMap<String, String> signUpDetailsModified = new HashMap<>();
    private SignUpRequest signUpRequest;
    String username, email, password;

    public SignUpFragModel(SignUpFragPresenter signUpFragPresenter) {
        this.signUpFragPresenter = signUpFragPresenter;
    }

    @Override
    public void getTestSignUpValue(Object o) {
        signUpDetailsModified = (HashMap<String, String>) o;
        mManager = new RestManager();
        username = signUpDetailsModified.get("username");
        email = signUpDetailsModified.get("email");
        password = signUpDetailsModified.get("password");
        signUpRequest = new SignUpRequest(username, email, password);
        callSignUpWebService();
    }

    private void callSignUpWebService() {
//        Log.e("Enter", "enter");
        Call<SignUpResponse.MainPojo> responseCall = mManager.getmItemService().createAccount(signUpRequest);
        responseCall.enqueue(new Callback<SignUpResponse.MainPojo>() {
            @Override
            public void onResponse(Call<SignUpResponse.MainPojo> call, Response<SignUpResponse.MainPojo> response) {
                if (response.isSuccessful())
                {
                    String authToken = response.body().getResponse().getAuthToken();
//                    Log.e("Success", authToken);
                    signUpDetailsModified.put("response", "success");
                    signUpDetailsModified.put("message", "Welcome!!");
                    signUpDetailsModified.put("authToken", authToken);
                }
                else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
//                            Log.e("Error 400", "Bad Request");
                            signUpDetailsModified.put("response", "error");
                            signUpDetailsModified.put("message", "Bad Request");
                            break;
                        case 404:
//                            Log.e("Error 404", "Not Found");
                            signUpDetailsModified.put("response", "error");
                            signUpDetailsModified.put("message", "Request Not Found");
                            break;
                        case 422:
                            Log.e("Error 422", "Enter your correct username and password'");
                            signUpDetailsModified.put("response", "error");
                            signUpDetailsModified.put("message", "Username already exists");
                            break;
                        case 402:
//                            Log.e("Error 402", "Enter your correct username and password'");
                            signUpDetailsModified.put("response", "error");
                            signUpDetailsModified.put("message", "Username already exists");
                            break;
                        default:
                            signUpDetailsModified.put("response", "error");
                            signUpDetailsModified.put("message", "Something went wrong");
//                            Log.e("Error", "Generic Error");
                    }
                }
                signUpFragPresenter.sendTestSignUpViewBack(signUpDetailsModified);
            }

            @Override
            public void onFailure(Call<SignUpResponse.MainPojo> call, Throwable t) {
                signUpDetailsModified.put("response", "error");
                signUpDetailsModified.put("message", "error");
//                Log.e("Error", "no..");
                signUpFragPresenter.sendTestSignUpViewBack(signUpDetailsModified);
            }
        });
    }
}
