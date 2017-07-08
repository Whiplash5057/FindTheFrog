package in.devdesk.findthefrog.LoginSignUp.Login;

import android.graphics.Color;
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
import in.devdesk.findthefrog.LoginSignUp.MainActivity;
import in.devdesk.findthefrog.R;

import static in.devdesk.findthefrog.HeLpEr.Utils.isNetworkAvailable;

/**
 * Created by richardandrews on 08/07/17.
 */

public class LoginFragment extends Fragment implements Login_MVP.View{

    //-- blind strings
    String username, password;
    HashMap<String, String> loginDetails = new HashMap<>();
    HashMap<String, String> loginDetailsReturn = new HashMap<>();


    //-- data binding
    @BindView(R.id.et_login_loginpage)
    EditText usernameView;

    @BindView(R.id.et_password_loginpage)
    EditText passwordView;

    @BindView(R.id.tv_login_interneterror)
    TextView internetError;

    @BindView(R.id.tv_login_usernameerror)
    TextView usernameError;

    @BindView(R.id.tv_login_passworderror)
    TextView passwordError;

    @BindView(R.id.btn_login_loginpage)
    Button testBtn;


    //-- onClicks
    @OnClick(R.id.btn_login_loginpage)
    public void submit(View view) {
        username = usernameView.getText().toString();
        password = passwordView.getText().toString();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
        LoginFragPresenter loginFragPresenter = new LoginFragPresenter(this);

        if(isNetworkAvailable(getActivity()))
        {
            loginFragPresenter.testLoginValue(loginDetails);
            internetError.setVisibility(View.GONE);
        }
        else
        {
            internetError.setVisibility(View.VISIBLE);
        }

    }

    //-- other methods
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loginTestReturn(Object o) {
        loginDetailsReturn = (HashMap<String, String>) o;
        String responseStatus = loginDetailsReturn.get("response");
        String responseMessage = loginDetailsReturn.get("message");
        String responseToken = "";
        if(responseStatus == "success")
            responseToken = loginDetailsReturn.get("authToken");

        Snackbar snackbar = Snackbar.make(testBtn, responseMessage, Snackbar.LENGTH_SHORT);
        View sb = snackbar.getView();
        TextView tv = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getContext(), R.color.ascentTextColor));
        tv.setHeight(160);
        tv.setTextSize(20);
        snackbar.show();
    }
}
