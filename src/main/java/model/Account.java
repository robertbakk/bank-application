package model;

import java.sql.Date;
import java.util.Random;
import java.util.UUID;

public class Account {

    private String id;

    private String number;

    private String type;

    private float balance;

    private Date creationDate;

    public Account(String id, String number, String type, float balance, Date creationDate) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    public Account(String id, String number, String type, float balance) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.balance = balance;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Account(String number, String type, float balance) {
        this.id = UUID.randomUUID().toString();
        this.number = number;
        this.type = type;
        this.balance = balance;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public Account(String type, float balance) {
        this.id = UUID.randomUUID().toString();
        Random r = new Random();
        number = "RO" + r.nextInt(10) + r.nextInt(10) + "BANK";
        for (int i = 0; i < 15; i++) {
            number += r.nextInt(10);
        }
        this.type = type;
        this.balance = balance;
        this.creationDate = new Date(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getBalance() {
        return balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
