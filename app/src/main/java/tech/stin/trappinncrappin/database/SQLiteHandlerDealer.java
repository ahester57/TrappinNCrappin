package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import tech.stin.trappinncrappin.data.Dealer;
import tech.stin.trappinncrappin.data.Player;

/**
 * Created by Austin on 5/16/2017.
 */

public class SQLiteHandlerDealer extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandlerDealer.class.getSimpleName();

    // db name
    private static final String DB_NAME = "tbl_users";
    private static final String TABLE_NAME = "dealer";
    private static final int DB_VERSION = 1;
    private Context mContext;
    private static SQLiteHandlerDealer sPersistence;

    public static SQLiteHandlerDealer sharedInstance(Context context) {
        if (sPersistence == null) {
            sPersistence = new SQLiteHandlerDealer(context);
        }
        return sPersistence;
    }

    public SQLiteHandlerDealer(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    private String createDealerTable() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + DealerSchema.KEY_ID + " INTEGER PRIMARY KEY, "
                + DealerSchema.KEY_UID + " TEXT UNIQUE, "
                + DealerSchema.KEY_NAME + " TEXT, "
                + DealerSchema.KEY_MESSAGE + " TEXT, "
                + DealerSchema.KEY_MONEY + " BIGINT, "
                + DealerSchema.KEY_IS_DEALER + " INT" + ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createDealerTable());
            Log.d(TAG, "Database tables created");
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't create dealer tables.");
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
            Log.d(TAG, "Couldn't create dealer tables.");
        }
    }

    // add new dealer to database
    public void addDealer(final Dealer dealer) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(createDealerTable());
            String uid = dealer.get_id();
            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " +
                    DealerSchema.KEY_UID + "=\"" + uid + "\";");
            ContentValues values = DealerCursorWrapper.createDealerValues(dealer);
            // inserting row
            long id = db.insert(TABLE_NAME, null, values);

            //Add stash
            SQLiteHanderStash sDB = SQLiteHanderStash.sharedInstance(mContext);
            sDB.addStash(dealer.getStash());

            db.close();
            Log.d(TAG, "New dealer inserted into sqlite: " + values.getAsString(DealerSchema.KEY_NAME));
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't add dealer.");
        }
    }

    public ArrayList<Dealer> getDealers() {
        final String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                DealerSchema.KEY_IS_DEALER + "=" + 1 + ";";
        ArrayList<Dealer> dealers = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            DealerCursorWrapper uCursor = new DealerCursorWrapper(cursor, mContext);

            dealers = uCursor.getDealers();

            cursor.close();
            db.close();

            if (dealers != null) {
                Log.d(TAG, "Fectching dealers from Sqlite: " + dealers.toString());
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't get dealers.");
        }
        return dealers;
    }

    public ArrayList<Dealer> getCustomers() {
        final String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                DealerSchema.KEY_IS_DEALER + "=" + 0 + ";";
        ArrayList<Dealer> dealers = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            DealerCursorWrapper uCursor = new DealerCursorWrapper(cursor, mContext);

            dealers = uCursor.getDealers();

            cursor.close();
            db.close();

            if (dealers != null) {
                Log.d(TAG, "Fectching dealers from Sqlite: " + dealers.toString());
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't get dealers.");
        }
        return dealers;
    }

    public void deleteAllDealers() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // delete all users
            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            db.delete(TABLE_NAME, null, null);
            db.close();

            Log.d(TAG, "Deleted all dealers from database" + TABLE_NAME);
        } catch (SQLiteException e) {
            Log.d(TAG, "Couldn't delete all dealers.");
        }
    }
}
