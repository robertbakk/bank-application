package model;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

public class Action {
    private String id;

    private String idUser;

    private String type;

    private Date date;

    private Time time;

    private String idClient;

    private String idAccount;

    private String idTransfer;

    public Action(String id, String idUser, String type, Date date, Time time, String idClient, String idAccount, String idTransfer) {
        this.id = id;
        this.idUser = idUser;
        this.type = type;
        this.date = date;
        this.time = time;
        this.idClient = idClient;
        this.idAccount = idAccount;
        this.idTransfer = idTransfer;
    }

    public Action(String idUser, String type, String idClient, String idAccount, String idTransfer) {
        this.id = UUID.randomUUID().toString();
        this.idUser = idUser;
        this.type = type;
        this.date = new Date(System.currentTimeMillis());
        this.time = new Time(System.currentTimeMillis());
        this.idClient = idClient;
        this.idAccount = idAccount;
        this.idTransfer = idTransfer;
    }

    public String getId() {
        return id;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getIdTransfer() {
        return idTransfer;
    }

    public String getIdAccount() {
        return idAccount;
    }

}
