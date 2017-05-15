package tech.stin.trappinncrappin.data;

import java.util.UUID;

/**
 * Created by Austin on 5/14/2017.
 */

public class Customer {

    private String _id;
    private String name;
    private String message;
    private long money;

    public Customer() {
        this._id = UUID.randomUUID().toString();
        this.name = "Customer Bobby";
        this.message = "howdy";
        this.money = 160;
    }

    public Customer(String _id, String name, String message, long money) {
        this._id = _id;
        this.name = name;
        this.message = message;
        this.money = money;
    }



}
