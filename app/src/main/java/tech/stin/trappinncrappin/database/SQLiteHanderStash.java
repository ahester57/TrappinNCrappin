package tech.stin.trappinncrappin.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/9/2017.
 */

public class SQLiteHanderStash extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandlerPlayer.class.getSimpleName();
    // db name
    private static final String DB_NAME = "tbl_users";
    private static final String TABLE_NAME = "stash";
    private static final int DB_VERSION = 1;

    private static SQLiteHanderStash sPersistence;

    public static SQLiteHanderStash sharedInstance(Context context) {
        if (sPersistence == null) {
            sPersistence = new SQLiteHanderStash(context);
        }
        return sPersistence;
    }

    public SQLiteHanderStash(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private String createStashTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + StashSchema.KEY_ID + " INTEGER PRIMARY KEY, "
                + StashSchema.KEY_UID + " TEXT UNIQUE) ";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createStashTable());
            Log.d(TAG, "Database tables created");
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't create user tables.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            // drop old tables and...
            // create them again
            onCreate(db);
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't create user tables.");
        }
    }

    /// get user data
    public Stash getStashForUser(final String _id) { // change to return user object
        final String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                StashSchema.KEY_UID + "=\"" + _id + "\";StashSchema";
        Stash stash = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);
            StashCursorWrapper sCursor = new StashCursorWrapper(cursor);

            stash = sCursor.getStash();

            cursor.close();
            db.close();

            if (stash != null) {
                Log.d(TAG, "Fectching player from Sqlite: " + stash.toString());
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't get player.");
        }
        return stash;
    }
}
