package com.example.sreed;

/**
 * Created by karsk on 25/04/2018.
 */

public class ModelFeed {

    int id, etat;
    String name, time, status;

    public ModelFeed(int id, String name, String time, String status, int etat) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.status = status;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}
