package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

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
                + StashSchema.KEY_UID + " TEXT, "
                + StashSchema.KEY_TYPE + "TEXT, "
                + StashSchema.KEY_VALUE + "INTEGER" + ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createStashTable());
            Log.d(TAG, "Database tables created");
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't create stash tables.");
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
            Log.d(TAG, "Couldn't create stash tables.");
        }
    }

    // add new user to database
    public void addStash(final Stash stash) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(createStashTable());
            String uid = stash.getUser_id();
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                            StashjjkjkjklSchema.KEY_UID + "=\"" + uid + "\";");

            HashMap<String, Integer> hMap = stash.getStash();

            for (Map.Entry<String, Integer> entry : hMap.entrySet()) {
                String type = entry.getKey();
                int value = entry.getValue();
                ContentValues values = StashCursorWrapper.createStashValues(uid, type, value);
                // inserting row
                long id = db.insert(TABLE_NAME, null, values);
            }

            db.close();
            Log.d(TAG, "New stash inserted into sqlite: " + stash.getStash().entrySet().toString());
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't add stash.");
        }
    }

    /// get user data
    public Stash getStashForUser(final String _id) { // change to return user object
        final String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                StashSchema.KEY_UID + "=\"" + _id + "\";";
        Stash stash = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);
            StashCursorWrapper sCursor = new StashCursorWrapper(cursor);

            stash = sCursor.getStash();

            cursor.close();
            db.close();

            if (stash != null) {
                Log.d(TAG, "Fectching stash from Sqlite: " + stash.getStash().entrySet().toString());
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't get stash.");
        }
        return stash;
    }
}
