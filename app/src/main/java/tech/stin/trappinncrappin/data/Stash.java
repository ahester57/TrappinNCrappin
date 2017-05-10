package tech.stin.trappinncrappin.data;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Austin on 5/9/2017.
 */

public class Stash {

    private String _id;
    private HashMap<String, Integer> mStash;

    public Stash() {
        mStash = new HashMap<>();
        _id = UUID.randomUUID().toString();
    }

    public Stash(HashMap<String, Integer> stash) {
        mStash = stash;
    }

    public HashMap<String, Integer> getStash() {
        return mStash;
    }

    public String get_id() {
        return _id;
    }
}
