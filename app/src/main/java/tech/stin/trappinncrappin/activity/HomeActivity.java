package tech.stin.trappinncrappin.activity;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import tech.stin.trappinncrappin.R;
import tech.stin.trappinncrappin.app.FragConfig;
import tech.stin.trappinncrappin.app.SessionManager;
import tech.stin.trappinncrappin.data.Player;

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

        Player player = session.getCurrentPlayer();

        if (player == null) {
            newPlayerDialog();
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
            case R.id.action_check_mark:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newPlayerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_token_input, null);
        final EditText tokenIn = (EditText) dialogView.findViewById(R.id.player_name_input);
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth)
                .setTitle(Html.fromHtml("<h2>New Player</h2>"))
                .setMessage(Html.fromHtml("Enter your name" +
                        ": "))
                .setView(dialogView)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (tokenIn != null) {
                            String token = tokenIn.getText().toString();
                            if (token.length() >= 4) {
                                Player player = new Player(token, "message");
                                session.addPlayer(player);
                                if (hView != null) {
                                    hView.setNameText(player.getName());
                                    hView.setStashAdapter(player.getStash());
                                }
                            } else {
                                newPlayerDialog();
                                Toast.makeText(getApplicationContext(),
                                        "Name must be greater than 4 characters.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            Log.d(TAG, "Quiz token input: " + token);
                        }
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_menu_directions)
                .show();
    }

}
