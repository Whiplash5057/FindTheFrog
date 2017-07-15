package in.devdesk.findthefrog.LoginSignUp.SignUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.devdesk.findthefrog.MyPager.ParentTab;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.AUTHTOKEN;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.LOGINSHAREDP;
import static in.devdesk.findthefrog.HeLpEr.Constants.REFERENCE.USERNAME;
import static in.devdesk.findthefrog.HeLpEr.Utils.isNetworkAvailable;

/**
 * Created by richardandrews on 08/07/17.
 */

public class SignUpFragment extends Fragment implements SignUp_MVP.View{

    //-- blind strings
    String password, email, responseStatus, username, responseMessage, responseToken;
    HashMap<String, String> signUpDetails = new HashMap<>();
    HashMap<String, String> signUpDetailsReturn = new HashMap<>();

    //-- data binding
    @BindView(R.id.et_username_signuppage)
    EditText usernameView;

    @BindView(R.id.et_email_signuppage)
    EditText emailView;

    @BindView(R.id.et_passowrd_signuppage)
    EditText passwordView;

    @BindView(R.id.tv_signup_interneterror)
    TextView internetError;

    @BindView(R.id.tv_username_signuppage)
    TextView usernameError;

    @BindView(R.id.tv_email_signuppage)
    TextView emailError;

    @BindView(R.id.tv_password_signuppage)
    TextView passwordError;

    @BindView(R.id.btn_signup_signuppage)
    Button testBtn;

    //-- onClicks
    @OnClick(R.id.btn_signup_signuppage)
    public void submit(View view) {
        username = usernameView.getText().toString();
        email = emailView.getText().toString();
        password = passwordView.getText().toString();
        signUpDetails.put("username", username);
        signUpDetails.put("password", password);
        signUpDetails.put("email", email);
        SignUpFragPresenter signUpFragPresenter = new SignUpFragPresenter(this);
        Boolean errorValid = errorValidation();
        if(isNetworkAvailable(getActivity()) && errorValid)
        {
//            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            signUpFragPresenter.testSignUpValue(signUpDetails);
            internetError.setVisibility(View.GONE);
        }
        else
        {
            internetError.setVisibility(View.VISIBLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private Boolean errorValidation() {

        Boolean validInvalid;

        if(username.length() < 5)
        {
            usernameError.setVisibility(View.VISIBLE);
            validInvalid = false;
        }
        else
        {
            usernameError.setVisibility(View.GONE);
            validInvalid = true;
        }

        if(email.length() < 5)
        {
            passwordError.setVisibility(View.VISIBLE);
            validInvalid = false;
        }
        else
        {
            passwordError.setVisibility(View.GONE);
            validInvalid = true;
        }
        if(password.length() < 5)
        {
            passwordError.setVisibility(View.VISIBLE);
            validInvalid = false;
        }
        else
        {
            passwordError.setVisibility(View.GONE);
            validInvalid = true;
        }
        return validInvalid;
    }

    @Override
    public void signUpTestReturn(Object o) {
        signUpDetailsReturn = (HashMap<String, String>) o;
        responseStatus = signUpDetailsReturn.get("response");
        responseMessage = signUpDetailsReturn.get("message");
        responseToken = "";
        if(responseStatus == "success")
            responseToken = signUpDetailsReturn.get("authToken");

        Snackbar snackbar = Snackbar.make(testBtn, responseMessage, Snackbar.LENGTH_SHORT);
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if(isNetworkAvailable(getActivity()) && responseStatus == "success")
                {
                    Toast.makeText(getActivity(), "Now", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), ParentTab.class));
                    SharedPreferences sharedPrefs = getActivity().getSharedPreferences(LOGINSHAREDP, Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sharedPrefs.edit();
                    ed.putString(USERNAME, username);
                    ed.putString(AUTHTOKEN, responseToken);
                    ed.apply();
                }
            }
        });
        View sb = snackbar.getView();
        TextView tv = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ascentTextColor));
        tv.setHeight(170);
        tv.setTextSize(16);
        snackbar.show();
    }
}
