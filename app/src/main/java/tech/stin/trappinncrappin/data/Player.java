package tech.stin.trappinncrappin.data;

import java.util.UUID;

/**
 * Created by Austin on 5/9/2017.
 */

public class Player {

    private String _id;
    private String name;
    private String message;
    private long money;
    private int cycles;
    private Stash stash;

    public Player(String name, String message) {
        this._id = UUID.randomUUID().toString();
        this.name = name;
        this.message = message;
        this.money = 20;
        this.cycles = 0;
        this.stash = new Stash(_id);
    }

    public Player(String _id, String name, String message, long money, int cycles, Stash stash) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.money = money;
        this.cycles = cycles;
        this.stash = stash;
    }

    public void addItem(String key, int amt) {
        stash.addItem(key, amt);
    }

    public void addMoney(int amount) {
        money += amount;
    }

    // returns success
    public boolean sellItem(String key, int amt) {
        return stash.sellItem(key, amt);
    }

    // returns success
    public boolean takeMoney(int amt) {
        if (money > amt) {
            money -= amt;
            return true;
        }
        return false;
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

    public int getCycles() {
        return cycles;
    }

    public Stash getStash() {
        return stash;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", money=" + money +
                ", cycles=" + cycles +
                '}';
    }
}
