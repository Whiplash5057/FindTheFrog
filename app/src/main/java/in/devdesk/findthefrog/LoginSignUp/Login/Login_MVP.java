package in.devdesk.findthefrog.LoginSignUp.Login;

/**
 * Created by richardandrews on 08/07/17.
 */

public interface Login_MVP {
    public interface View{
        public void loginTestReturn(Object o);
    }
    public interface Presenter{
        public void testLoginValue(Object o);
        public void sendTestLoginViewBack(Object o);
    }
    public interface Model{
        public void getTestLoginValue(Object o);
    }
}
