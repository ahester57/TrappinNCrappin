package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.HashMap;

import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/9/2017.
 */

public class StashCursorWrapper extends CursorWrapper {

    StashCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    Stash getStash() {
        Stash stash;
        HashMap<String, Integer> hMap = new HashMap<>();
        String _id = null;
        moveToFirst();
        if (getCount() > 0) {
            do {
                _id = getString(getColumnIndex(StashSchema.KEY_UID));
                String type = getString(getColumnIndex(StashSchema.KEY_TYPE));
                int value = getInt(getColumnIndex(StashSchema.KEY_VALUE));
                hMap.put(type, value);
            } while (moveToNext());
        }
        stash = new Stash(_id, hMap);
        return stash;
    }

    static ContentValues createStashValues(String uid, String type, int value) {
        ContentValues values = new ContentValues();
        values.put(StashSchema.KEY_UID, uid);
        values.put(StashSchema.KEY_TYPE, type);
        values.put(StashSchema.KEY_VALUE, value);

        return values;
    }
}
