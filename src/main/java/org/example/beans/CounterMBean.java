package org.example.beans;

public interface CounterMBean {
    int getTotalAttempts();
    int getTotalHits();
    void updateStats(double x, double y, double r, boolean hit);
}
