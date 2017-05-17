package tech.stin.trappinncrappin.data;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Austin on 5/14/2017.
 */

public class Customer {

    private String _id;
    private String name;
    private String message;
    private long money;
    private Random rnd;

    public Customer() {
        rnd = new Random();
        rnd.setSeed(System.nanoTime());
        this._id = UUID.randomUUID().toString();
        this.name = rnd.nextDouble() > 0.5 ? "Bobby" : "Johnny";
        this.message = rnd.nextDouble() > 0.5 ? "howdy" : "ayy lmao";
        this.money = rnd.nextDouble() > 0.5 ? 160 : 240;
        // @TODO add demand
    }

    public Customer(String _id, String name, String message, long money) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.money = money;
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
}
