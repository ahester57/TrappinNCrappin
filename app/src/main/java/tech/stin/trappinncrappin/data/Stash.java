package tech.stin.trappinncrappin.data;

import java.util.HashMap;

import tech.stin.trappinncrappin.app.DrugConfig;

/**
 * Created by Austin on 5/9/2017.
 */

public class Stash {

    private String user_id;
    private HashMap<String, Integer> mStash;

    public Stash(String user_id) {
        this.user_id = user_id;
        mStash = new HashMap<>();
        addItem(DrugConfig.WEED, 3);
    }

    public Stash(String user_id, HashMap<String, Integer> stash) {
        this.user_id = user_id;
        mStash = stash;
    }

    public void addItem(String key, int value) {
        try {
            int curVal = mStash.get(key);
            mStash.put(key, value + curVal);
        } catch (NullPointerException e) {
            // Does not have yet
            mStash.put(key, value);
        }
    }

    // returns success
    public boolean sellItem(String key, int value) {
        try {
            int curVal = mStash.get(key);
            if (curVal >= value) {
                mStash.put(key, curVal - value);
                return true;
            }
        } catch (NullPointerException e) {
            // Does not have yet
            return false;
        }
        return false;
    }


    public HashMap<String, Integer> getStash() {
        return mStash;
    }

    public String getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "Stash{" +
                "mStash=" + mStash.toString() +
                '}';
    }
}
