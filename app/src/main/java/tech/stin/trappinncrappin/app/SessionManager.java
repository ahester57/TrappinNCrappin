package tech.stin.trappinncrappin.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import tech.stin.trappinncrappin.data.Player;
import tech.stin.trappinncrappin.database.SQLiteHandlerPlayer;

/**
 * Created by Austin on 5/9/2017.
 */

public class SessionManager {
    private final static String TAG = SessionManager.class.getSimpleName();

    // Shared preferences for storing user login 'token'
    private SharedPreferences pref;
    private Context _context;

    private static final String PREF_NAME = "AndroidQuizLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
    }

    public void addPlayer(Player player) {
        SQLiteHandlerPlayer db = SQLiteHandlerPlayer.sharedInstance(_context);
        db.addPlayer(player);
    }

    public Player getCurrentPlayer() {
        SQLiteHandlerPlayer db = SQLiteHandlerPlayer.sharedInstance(_context);
        return db.getCurrentPlayer();
    }

    public void setLogin(boolean isLoggedIn) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.apply();

        Log.d(TAG, "User login session changed");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}
