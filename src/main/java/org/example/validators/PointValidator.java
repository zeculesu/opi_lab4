package org.example.validators;

public class PointValidator {
    public static Boolean isShoot(double x, double y, double r){
        if (x >= 0 && y >= 0) return y <= (r / 2) - (x / 2);
        if (x <= 0 && y >= 0) return x * x + y * y <= r * r / 4;
        if (x <= 0) return x >= -r && y >= -(r / 2);
        return false;
    }
}