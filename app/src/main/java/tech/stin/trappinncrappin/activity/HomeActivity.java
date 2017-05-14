package tech.stin.trappinncrappin.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.app.FragConfig;
import tech.stin.trappinncrappin.app.SessionManager;

public class HomeActivity extends AppCompatActivity implements HomeView.HomeListener {

    private final static String TAG = HomeActivity.class.getSimpleName();

    private SessionManager session;
    private HomeView hView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_dealer);
        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(FragConfig.HOME_VIEW) != null) {
            hView = (HomeView) fm.findFragmentByTag(FragConfig.HOME_VIEW);
        } else {
            //Fragment not loaded
            hView = new HomeView();
            fm.beginTransaction()
                    .add(R.id.home_container, hView, FragConfig.HOME_VIEW)
                    .commit();
        }


    }

    @Override
    public void goToDealers() {
        Intent i = new Intent(this, DealerActivity.class);
        startActivity(i);
    }

    @Override
    public void goToStreets() {
        Intent i = new Intent(this, StreetActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_alt, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
