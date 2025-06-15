package org.example.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import javax.management.*;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


@Named
@SessionScoped
public class Counter implements CounterMBean, NotificationBroadcaster, Serializable {
    private final AtomicInteger totalAttempts = new AtomicInteger();
    private final AtomicInteger totalHits = new AtomicInteger();

    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();

    @Override
    public int getTotalAttempts() {
        return totalAttempts.get();
    }

    @Override
    public int getTotalHits() {
        return totalHits.get();
    }

    @Override
    public void updateStats(double x, double y, double r, boolean hit) {
        totalAttempts.incrementAndGet();
        if (hit) {
            totalHits.incrementAndGet();
        }
        alertOutOfZone(x, y);
    }

    public void alertOutOfZone(double x, double y) {
        if (Math.abs(x) > 3 || Math.abs(y) > 3) {
            broadcaster.sendNotification(new Notification(
                    "where",
                    this,
                    System.currentTimeMillis(),
                    "The point hit out of zone"
            ));
        }
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        broadcaster.addNotificationListener(listener, filter, handback);
    }


    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }


    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[]{"where"};
        String name = Notification.class.getName();
        String description = "Notification sent when point hit out of zone";
        return new MBeanNotificationInfo[]{new MBeanNotificationInfo(types, name, description)};
    }
}

