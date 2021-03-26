package model;

import java.util.UUID;

public class Client {

    private String id;

    private String name;

    private String address;

    private String cnp;

    public Client(String id, String name, String address, String cnp) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cnp = cnp;
    }

    public Client(String name, String address, String cnp) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.cnp = cnp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCnp() {
        return cnp;
    }

}
