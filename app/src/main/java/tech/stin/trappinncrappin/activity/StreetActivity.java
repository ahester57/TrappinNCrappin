package tech.stin.trappinncrappin.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.app.FragConfig;
import tech.stin.trappinncrappin.app.SessionManager;

/**
 * Created by Austin on 5/11/2017.
 */

public class StreetActivity extends AppCompatActivity {
    private static final String TAG = StreetActivity.class.getSimpleName();

    private SessionManager session;
    private StreetView sView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        session = new SessionManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dealer);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(FragConfig.STREET_VIEW) != null) {
            sView = (StreetView) fm.findFragmentByTag(FragConfig.STREET_VIEW);
        } else {
            //Fragment not loaded
            sView = new StreetView();
            fm.beginTransaction()
                    .add(R.id.dealer_container, sView, FragConfig.STREET_VIEW)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alt, menu);
        return true;
    }
}
