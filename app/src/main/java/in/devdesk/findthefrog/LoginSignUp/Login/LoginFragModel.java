package in.devdesk.findthefrog.LoginSignUp.Login;

import java.util.HashMap;

/**
 * Created by richardandrews on 08/07/17.
 */

public class LoginFragModel implements Login_MVP.Model {

    LoginFragPresenter loginFragPresenter;

    public LoginFragModel(LoginFragPresenter loginFragPresenter) {
        this.loginFragPresenter = loginFragPresenter;
    }

    @Override
    public void getTestLoginValue(Object o) {
        HashMap<String, String> loginDetailsModified = new HashMap<>();
        loginDetailsModified = (HashMap<String, String>) o;
        loginDetailsModified.put("username", "I have changed the username");
        loginDetailsModified.put("password", "I have changed the password");

        loginFragPresenter.sendTestLoginViewBack(loginDetailsModified);
    }
}
