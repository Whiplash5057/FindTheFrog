package in.devdesk.findthefrog.LoginSignUp.SignUp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import in.devdesk.findthefrog.R;

/**
 * Created by richardandrews on 08/07/17.
 */

public class SignUpFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
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
}
