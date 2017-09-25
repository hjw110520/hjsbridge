package com.hjw.hjsbridge_core;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.host;

/**
 * Created by hjw on 2017/9/13.9:47
 */

public class BridgeUtils {
    private static Map<String, Injector> BridgeMap = new HashMap<>();

    public static void inject(Object bridgeClass) {
        String className = bridgeClass.getClass().getName();
        try {
            Injector injector = BridgeMap.get(className);
            if (injector == null) {
            Class<?> injectorClass = Class.forName(className + "$$Injector");
            injector = (Injector) injectorClass.newInstance();
            BridgeMap.put(className, injector);
        }
        injector.inject(host);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
