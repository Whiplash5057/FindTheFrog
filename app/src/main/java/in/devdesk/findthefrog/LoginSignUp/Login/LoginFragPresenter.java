package in.devdesk.findthefrog.LoginSignUp.Login;

/**
 * Created by richardandrews on 08/07/17.
 */

public class LoginFragPresenter implements Login_MVP.Presenter {

    private LoginFragment loginFragment;

    public LoginFragPresenter(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    @Override
    public void testLoginValue(Object o) {
        LoginFragModel loginFragModel = new LoginFragModel(this);
        loginFragModel.getTestLoginValue(o);
    }

    @Override
    public void sendTestLoginViewBack(Object o) {
        loginFragment.loginTestReturn(o);
    }
}
