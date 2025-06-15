package org.example.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class AreaResult implements AreaResultMBean, Serializable {
    private double area = 0;

    @Override
    public void calculateArea(double r) {
        area = (r * r/2) + ((r * r/2) / 2) + ((Math.PI * Math.pow(r / 2, 2)) / 4);
    }

    @Override
    public double getArea() {
        return area;
    }
}