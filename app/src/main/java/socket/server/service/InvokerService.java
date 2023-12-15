package socket.server.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class InvokerService {
    private static final String PACKAGE_MANAGER = "socket.server.manager.";

    public static Object classInvoker(String managerName) {
        Object obj = null;

        try {
            Class<?> c = Class.forName(PACKAGE_MANAGER + managerName);
            Constructor<?> cons = c.getConstructor();
            obj = cons.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return obj;
        }

    }

    public static Method methodInvoker(Object obj, String methodName) {
        Method m = null;

        try {
            Class objClass = obj.getClass();
            System.out.println(objClass.getName());

            Class[] argTypes = {int.class};
            m = objClass.getMethod(methodName, argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            return m;
        }

    }

    public static int invokeMethod(Object obj, Method m, String argsN) {
        int nPrimes = 0;

        try {
            nPrimes = (int) m.invoke(obj, Integer.parseInt(argsN));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return nPrimes;
        }
    }
}
