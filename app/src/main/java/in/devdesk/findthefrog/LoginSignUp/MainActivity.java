package in.devdesk.findthefrog.LoginSignUp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.devdesk.findthefrog.LoginSignUp.Login.LoginFragment;
import in.devdesk.findthefrog.LoginSignUp.SignUp.SignUpFragment;
import in.devdesk.findthefrog.R;

public class MainActivity extends AppCompatActivity {


    @BindString(R.string.loginSignup_btn_txt) String signUpTag;

    @BindString(R.string.login_btn_hint) String logInTag;

    @BindView(R.id.lbl_signup_gotosinuplogin)
    Button gotosinuplogin;

    @OnClick(R.id.lbl_signup_gotosinuplogin)
    public void submit(View view) {

        if(gotosinuplogin.getText() == signUpTag)
        {
            setLoginFragment("signup");
            gotosinuplogin.setText(logInTag);
        }
        else if(gotosinuplogin.getText() == logInTag)
        {
            setLoginFragment("login");
            gotosinuplogin.setText(signUpTag);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setLoginFragment("login");
    }

    private void setLoginFragment(String fragSelection) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_animation, R.anim.exit_animation);

        LoginFragment loginFragment = new LoginFragment();
        SignUpFragment signUpFragment = new SignUpFragment();

        switch (fragSelection) {
            case "login":
                transaction.replace(R.id.frag_container_main, loginFragment, "LoginFragment");
                transaction.commit();
                break;
            case "signup":
                transaction.replace(R.id.frag_container_main, signUpFragment, "SignUpFragment");
                transaction.commit();
                break;
            default:
                break;
        }
    }
}
