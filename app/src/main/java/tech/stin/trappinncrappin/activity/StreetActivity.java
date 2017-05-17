package tech.stin.trappinncrappin.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.activity.helper.CustomerPageView;
import tech.stin.trappinncrappin.app.FragConfig;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Dealer;

/**
 * Created by Austin on 5/11/2017.
 *
 * For viewing potential customers
 */

public class StreetActivity extends AppCompatActivity implements StreetView.StreetListener {
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
        if (fm.findFragmentByTag(FragConfig.CUSTOMER_PAGE_VIEW) != null) {
            Log.d(TAG, "Customer page already loaded.");
        } else {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alt, menu);
        return true;
    }

    @Override
    public void goToCustomerPage(Dealer customer) {
        // change customer to sqlite
        FragmentManager fm = getFragmentManager();
        CustomerPageView cPageView = new CustomerPageView();
        cPageView.setCustomer(customer);
        fm.beginTransaction()
                .replace(R.id.dealer_container, cPageView, FragConfig.CUSTOMER_PAGE_VIEW)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
