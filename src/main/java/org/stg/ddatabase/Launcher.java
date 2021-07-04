package org.stg.ddatabase;

import com.sun.javafx.application.LauncherImpl;
import com.sun.javafx.util.Logging;
import org.stg.ddatabase.application.DDatabase;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Launcher {
    public static void main(String[] args) {
        disableWarning();
        Logging.getJavaFXLogger().disableLogging();
        LauncherImpl.launchApplication(DDatabase.class,args);
    }

    private static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);
            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception ignored) {
        }
    }
}
