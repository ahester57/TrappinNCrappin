package tech.stin.trappinncrappin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import tech.stin.trappinncrappin.data.Player;
import tech.stin.trappinncrappin.data.Stash;

/**
 * Created by Austin on 5/9/2017.
 */

public class PlayerCursorWrapper extends CursorWrapper {
    private static final String TAG = PlayerCursorWrapper.class.getSimpleName();
    private SQLiteHanderStash sDB;

    PlayerCursorWrapper(Cursor cursor, Context context) {
        super(cursor);
        sDB = SQLiteHanderStash.sharedInstance(context);
    }

    Player getPlayer() {
        Player player = null;

        moveToFirst();
        if (getCount() > 0) {
            String _id = getString(getColumnIndex(PlayerSchema.KEY_UID));
            String name = getString(getColumnIndex(PlayerSchema.KEY_NAME));
            String message = getString(getColumnIndex(PlayerSchema.KEY_MESSAGE));
            long money = getLong(getColumnIndex(PlayerSchema.KEY_MONEY));
            int cycles = getInt(getColumnIndex(PlayerSchema.KEY_CYCLES));
            Stash stash = sDB.getStashForUser(_id);
            player = new Player(_id, name, message, money, cycles, stash);
        }
        return player;
    }

    static ContentValues createUserValues(Player player) {
        ContentValues values = new ContentValues();
        values.put(PlayerSchema.KEY_UID, player.get_id());
        values.put(PlayerSchema.KEY_NAME, player.getName());
        values.put(PlayerSchema.KEY_MESSAGE, player.getMessage());
        values.put(PlayerSchema.KEY_MONEY, player.getMoney());
        values.put(PlayerSchema.KEY_CYCLES, player.getCycles());

        return values;
    }
}
