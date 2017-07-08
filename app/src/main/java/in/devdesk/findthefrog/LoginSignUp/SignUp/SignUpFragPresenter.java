package in.devdesk.findthefrog.LoginSignUp.SignUp;

/**
 * Created by richardandrews on 09/07/17.
 */

public class SignUpFragPresenter implements SignUp_MVP.Presenter{
    SignUpFragment signUpFragment;

    public SignUpFragPresenter(SignUpFragment signUpFragment) {
        this.signUpFragment = signUpFragment;
    }

    @Override
    public void testSignUpValue(Object o) {
        SignUpFragModel signUpFragModel = new SignUpFragModel(this);
        signUpFragModel.getTestSignUpValue(o);
    }

    @Override
    public void sendTestSignUpViewBack(Object o) {
        signUpFragment.signUpTestReturn(o);
    }
}
