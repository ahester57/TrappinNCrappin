package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;

import tech.stin.trappinncrappin.data.Dealer;
import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/16/2017.
 */

public class DealerCursorWrapper extends CursorWrapper {
    private static final String TAG = DealerCursorWrapper.class.getSimpleName();
    private SQLiteHanderStash sDB;

    DealerCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        sDB = SQLiteHanderStash.sharedInstance(context);
    }

    ArrayList<Dealer> getDealers() {
        ArrayList<Dealer> dealers = null;
        moveToFirst();
        if (getCount() > 0) {
            dealers = new ArrayList<>();
            do {
                String _id = getString(getColumnIndex(DealerSchema.KEY_UID));
                String name = getString(getColumnIndex(DealerSchema.KEY_NAME));
                String message = getString(getColumnIndex(DealerSchema.KEY_MESSAGE));
                long money = getLong(getColumnIndex(DealerSchema.KEY_MONEY));
                boolean isDealer = getInt(getColumnIndex(DealerSchema.KEY_IS_DEALER)) == 1;
                Stash stash = sDB.getStashForUser(_id);
                dealers.add(new Dealer(_id, name, message, money, stash, isDealer));
            } while (moveToNext());
        }
        return dealers;
    }

    static ContentValues createDealerValues(Dealer dealer) {
        ContentValues values = new ContentValues();
        values.put(DealerSchema.KEY_UID, dealer.get_id());
        values.put(DealerSchema.KEY_NAME, dealer.getName());
        values.put(DealerSchema.KEY_MESSAGE, dealer.getMessage());
        values.put(DealerSchema.KEY_MONEY, dealer.getMoney());
        values.put(DealerSchema.KEY_IS_DEALER, dealer.isDealer() ? 1 : 0);

        return values;
    }

}
