package tech.stin.trappinncrappin.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.app.FragConfig;
import tech.stin.trappinncrappin.app.SessionManager;

/**
 * Created by Austin on 5/11/2017.
 */

public class DealerActivity extends AppCompatActivity implements DealerView.DealerListener {

    private SessionManager session;
    private DealerView dView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());


        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(FragConfig.DEALER_VIEW) != null) {
            dView = (DealerView) fm.findFragmentByTag(FragConfig.DEALER_VIEW);
        } else {
            //Fragment not loaded
            dView = new DealerView();
            fm.beginTransaction()
                    .add(R.id.home_container, dView, FragConfig.DEALER_VIEW)
                    .commit();
        }
    }
}
