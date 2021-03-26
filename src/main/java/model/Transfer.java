package model;

import java.sql.Date;
import java.util.UUID;

public class Transfer {

    private String id;

    private String idUser;

    private String idAccountFrom;

    private String idAccountTo;

    private float amount;

    private Date date;

    public Transfer(String id, String idUser, String idAccountFrom, String idAccountTo, float amount, Date date) {
        this.id = id;
        this.idUser = idUser;
        this.idAccountFrom = idAccountFrom;
        this.idAccountTo = idAccountTo;
        this.amount = amount;
        this.date = date;
    }

    public Transfer(String idUser, String idAccountFrom, String idAccountTo, float amount) {
        this.id = UUID.randomUUID().toString();
        this.idUser = idUser;
        this.idAccountFrom = idAccountFrom;
        this.idAccountTo = idAccountTo;
        this.amount = amount;
        this.date = new Date(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdAccountFrom() {
        return idAccountFrom;
    }

    public String getIdAccountTo() {
        return idAccountTo;
    }

    public float getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

}
