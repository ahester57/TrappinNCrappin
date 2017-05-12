package tech.stin.trappinncrappin.data;

import java.util.UUID;

/**
 * Created by Austin on 5/12/2017.
 */

public class Dealer {

    private String _id;
    private String name;
    private String message;
    private long money;
    private Stash stash;

    public Dealer() {
        this._id = UUID.randomUUID().toString();
        this.name = "Dealer Jim";
        this.message = "Wassup";
        this.money = 9000;
        this.stash = new Stash(_id);
    }

    public Dealer(String _id, String name, String message, Stash stash) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.stash = stash;
    }

    public void addItem(String key, int value) {
        stash.addItem(key, value);
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public long getMoney() {
        return money;
    }

    public Stash getStash() {
        return stash;
    }
}
