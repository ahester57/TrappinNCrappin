package tech.stin.trappinncrappin.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.stin.trappinncrappin.R;

/**
 * Created by Austin on 5/11/2017.
 */

public class DealerView extends Fragment {
    private static final String TAG = DealerView.class.getSimpleName();


    interface DealerListener {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dealer, container, false);

        return view;
    }
}
