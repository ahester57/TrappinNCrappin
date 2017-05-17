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
    private boolean isDealer;
    private Random rnd;

    // give true if dealer, false if customer
    public Dealer(boolean dealer) {
        rnd = new Random();
        rnd.setSeed(System.nanoTime());
        this._id = UUID.randomUUID().toString();
        this.isDealer = dealer;
        if (isDealer) {
            this.name = rnd.nextDouble() > 0.5 ? "Dealer Jim" : "Duller Jam";
            this.message = rnd.nextDouble() < 0.5 ? "Wassup" : "Fuck off Jem";
            this.money = 9000;
        } else {
            this.name = rnd.nextDouble() > 0.5 ? "Bobby" : "Johnny";
            this.message = rnd.nextDouble() > 0.5 ? "howdy" : "ayy lmao";
            this.money = rnd.nextDouble() > 0.5 ? 160 : 240;
        }
        generateStash();
    }

    public Dealer(String _id, String name, String message, long money, Stash stash, boolean dealer) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.money = money;
        this.stash = stash;
        this.isDealer = dealer;
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

    public Stash getStash() {
        return stash;
    }

    public boolean isDealer() { return isDealer; }

    private void generateStash() {
        this.stash = new Stash(_id);
        if (isDealer) {
            addItem(DrugConfig.WEED, (int) (rnd.nextDouble() * 70) + 14);
            addItem(DrugConfig.LSD, (int) (rnd.nextDouble() * 100) + 10);
            addItem(DrugConfig.MDMA, (int) (rnd.nextDouble() * 56));
        } else {
            addItem(DrugConfig.WEED, 0);
            addItem(DrugConfig.LSD, 0);
            addItem(DrugConfig.MDMA, 0);
        }
    }
}
