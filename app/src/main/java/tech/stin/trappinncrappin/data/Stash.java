package tech.stin.trappinncrappin.data;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Austin on 5/9/2017.
 */

public class Stash {

    private String user_id;
    private HashMap<String, Integer> mStash;

    public Stash(String user_id) {
        mStash = new HashMap<>();
        mStash.put("WEED", 2);
        this.user_id = user_id;
    }

    public Stash(String user_id, HashMap<String, Integer> stash) {
        this.user_id = user_id;
        mStash = stash;
    }

    public void addItem(String key, int value) {
        mStash.put(key, value);
    }

    public HashMap<String, Integer> getStash() {
        return mStash;
    }

    public String getUser_id() {
        return user_id;
    }
}
