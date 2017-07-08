package in.devdesk.findthefrog.LoginSignUp.Login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.devdesk.findthefrog.R;

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

    //-- onClicks
    @OnClick(R.id.btn_login_loginpage)
    public void submit(View view) {
        username = usernameView.getText().toString();
        password = passwordView.getText().toString();
        loginDetails.put("username", username);
        loginDetails.put("password", password);
        LoginFragPresenter loginFragPresenter = new LoginFragPresenter(this);
        loginFragPresenter.testLoginValue(loginDetails);
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
        Toast.makeText(getActivity(), loginDetailsReturn.get("username") + " " + loginDetailsReturn.get("password"), Toast.LENGTH_SHORT).show();
    }
}
