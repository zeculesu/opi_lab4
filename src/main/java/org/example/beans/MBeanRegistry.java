package org.example.beans;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;


public class MBeanRegistry {
    private static final Map<Object, ObjectName> registeredBeans = new HashMap<>();


    public static void registerBean(Object bean, String name) {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            String domain = bean.getClass().getPackageName();
            String type = bean.getClass().getSimpleName();
            ObjectName objectName = new ObjectName(String.format("%s:type=%s,name=%s", domain, type, name));

            if (mBeanServer.isRegistered(objectName)) {
                mBeanServer.unregisterMBean(objectName);
            }

            mBeanServer.registerMBean(bean, objectName);
            registeredBeans.put(bean, objectName);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to register MBean", ex);
        }
    }


    public static void unregisterBean(Object bean) {
        try {
            ObjectName objectName = registeredBeans.get(bean);
            if (objectName != null) {
                ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName);
                registeredBeans.remove(bean);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to unregister MBean", ex);
        }
    }

}
