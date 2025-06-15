package org.example.models;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;

@Named("abstractPoint")
@ApplicationScoped
public class Point implements Serializable {

    private Long id;

    private double x;

    private double y;

    private double r;

    private Boolean status;

    private String time;

    private long scriptTime;

    public Point() {
    }

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Point(Long id, double x, double y, double r, Boolean status, String time, long scriptTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.status = status;
        this.time = time;
        this.scriptTime = scriptTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getStatusWord(){
        return (status)
                ? "Попадание"
                : "Промах";
    }

    public String getStatusString(){
        return (status)
                ? "попадание"
                : "промах";
    }

    public String getStatusHTMLClass(){
        return (status)
                ? "green-status"
                : "red-status";
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getScriptTime() {
        return scriptTime;
    }

    public void setScriptTime(long scriptTime) {
        this.scriptTime = scriptTime;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + r;
    }
}