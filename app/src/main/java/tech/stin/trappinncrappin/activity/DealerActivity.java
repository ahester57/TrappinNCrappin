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
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/11/2017.
 *
 * Dealer Activity
 */

public class DealerActivity extends AppCompatActivity implements DealerView.DealerListener {
    private static final String TAG = DealerActivity.class.getSimpleName();

    private SessionManager session;
    private DealerView dView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);
        session = new SessionManager(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dealer);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(FragConfig.DEALER_VIEW) != null) {
            dView = (DealerView) fm.findFragmentByTag(FragConfig.DEALER_VIEW);
        } else {
            //Fragment not loaded
            dView = new DealerView();
            fm.beginTransaction()
                    .add(R.id.dealer_container, dView, FragConfig.DEALER_VIEW)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alt, menu);
        return true;
    }

    @Override
    public void goToDealerPage(Dealer dealer) {
        // change dealer to sqlite
        FragmentManager fm = getFragmentManager();
        DealerPageView dPageView = new DealerPageView();
        dPageView.setDealer(dealer);
        fm.beginTransaction()
                .replace(R.id.dealer_container, dPageView, FragConfig.DEALER_PAGE_VIEW)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
