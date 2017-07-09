package in.devdesk.findthefrog.MyPager.Other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import in.devdesk.findthefrog.R;

/**
 * Created by richardandrews on 09/07/17.
 */

public class OtherFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
