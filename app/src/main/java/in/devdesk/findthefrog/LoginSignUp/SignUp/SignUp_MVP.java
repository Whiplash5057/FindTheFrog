package in.devdesk.findthefrog.LoginSignUp.SignUp;

/**
 * Created by richardandrews on 08/07/17.
 */

public interface SignUp_MVP {

    public interface View{
        public void signUpTestReturn(Object o);
    }
    public interface Presenter{
        public void testSignUpValue(Object o);
        public void sendTestSignUpViewBack(Object o);
    }
    public interface Model{
        public void getTestSignUpValue(Object o);
    }
}
