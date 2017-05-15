package tech.stin.trappinncrappin.data;

import java.util.Random;
import java.util.UUID;

import tech.stin.trappinncrappin.app.DrugConfig;

/**
 * Created by Austin on 5/12/2017.
 */

public class Dealer {

    private String _id;
    private String name;
    private String message;
    private long money;
    private Stash stash;
    private Random rnd;

    public Dealer() {
        this._id = UUID.randomUUID().toString();
        this.name = "Dealer Jim";
        this.message = "Wassup";
        this.money = 9000;
        rnd = new Random();
        rnd.setSeed(System.nanoTime());
        generateStash();
    }

    public Dealer(String _id, String name, String message, long money, Stash stash) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.money = money;
        this.stash = stash;
    }

    public void addItem(String key, int amt) {
        stash.addItem(key, amt);
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

    private void generateStash() {
        this.stash = new Stash(_id);
        addItem(DrugConfig.WEED, (int) (rnd.nextDouble() * 70) + 14);
        addItem(DrugConfig.LSD, (int) (rnd.nextDouble() * 100)  + 10);
        addItem(DrugConfig.MDMA, (int) (rnd.nextDouble() * 56));
    }
}
