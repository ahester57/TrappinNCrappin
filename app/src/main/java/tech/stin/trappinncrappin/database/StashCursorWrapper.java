package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;

import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/9/2017.
 */

public class StashCursorWrapper extends CursorWrapper {

    StashCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    Stash getStash() {
        Stash stash = new Stash();

        moveToFirst();
        if (getCount() > 0) {
            String _id = getString(getColumnIndex(PlayerSchema.KEY_UID));
            String name = getString(getColumnIndex(PlayerSchema.KEY_NAME));
            String message = getString(getColumnIndex(PlayerSchema.KEY_MESSAGE));
            long money = getLong(getColumnIndex(PlayerSchema.KEY_MONEY));
            int cycles = getInt(getColumnIndex(PlayerSchema.KEY_CYCLES));
            //stash = new Stash(_id, name, message, money, cycles, stash);
        }
        return stash;
    }

    static ContentValues createStashValues(Stash stash) {
        ContentValues values = new ContentValues();
//        values.put(PlayerSchema.KEY_UID, stash.get_id());
//        values.put(PlayerSchema.KEY_NAME, stash.getName());
//        values.put(PlayerSchema.KEY_MESSAGE, stash.getMessage());
//        values.put(PlayerSchema.KEY_MONEY, stash.getMoney());
//        values.put(PlayerSchema.KEY_CYCLES, stash.getCycles());

        return values;
    }
}
