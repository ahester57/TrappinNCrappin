package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import tech.stin.trappinncrappin.data.Player;

/**
 * Created by Austin on 5/9/2017.
 */

public class SQLiteHandlerPlayer extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandlerPlayer.class.getSimpleName();

    // db name
    private static final String DB_NAME = "tbl_users";
    private static final String TABLE_NAME = "player";
    private static final int DB_VERSION = 1;
    private Context mContext;
    private static SQLiteHandlerPlayer sPersistence;

    public static SQLiteHandlerPlayer sharedInstance(Context context) {
        if (sPersistence == null) {
            sPersistence = new SQLiteHandlerPlayer(context);
        }
        return sPersistence;
    }

    public SQLiteHandlerPlayer(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    private String createPlayerTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + PlayerSchema.KEY_ID + " INTEGER PRIMARY KEY, "
                + PlayerSchema.KEY_UID + " TEXT UNIQUE, "
                + PlayerSchema.KEY_NAME + " TEXT, "
                + PlayerSchema.KEY_MESSAGE + " TEXT, "
                + PlayerSchema.KEY_MONEY + " BIGINT, "
                + PlayerSchema.KEY_CYCLES + " TEXT" + ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createPlayerTable());
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


    // add new user to database
    public void addPlayer(final Player user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(createPlayerTable());
            String uid = user.get_id();
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                    PlayerSchema.KEY_UID + "=\"" + uid + "\";");

            ContentValues values = PlayerCursorWrapper.createUserValues(user);
            // inserting row
            long id = db.insert(TABLE_NAME, null, values);

            //Add stash
            SQLiteHanderStash sDB = SQLiteHanderStash.sharedInstance(mContext);
            sDB.addStash(user.getStash());

            db.close();
            Log.d(TAG, "New user inserted into sqlite: " + values.getAsString(PlayerSchema.KEY_NAME ));
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't add user.");
        }
    }

    /// get user data
    public Player getCurrentPlayer() {
        final String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Player user = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            PlayerCursorWrapper uCursor = new PlayerCursorWrapper(cursor, mContext);

            user = uCursor.getPlayer();

            cursor.close();
            db.close();

            if (user != null) {
                Log.d(TAG, "Fectching player from Sqlite: " + user.toString());
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't get player.");
        }
        return user;
    }

    public void deleteAllPlayers() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete all users
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            db.delete(TABLE_NAME, null, null);
            db.close();

            Log.d(TAG, "Deleted all users from database" + TABLE_NAME);
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't delete all users.");
        }
    }
}
