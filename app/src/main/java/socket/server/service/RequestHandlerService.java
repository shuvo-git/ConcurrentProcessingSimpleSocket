package socket.server.service;

import socket.server.io.RequestObject;
import socket.server.manager.ParallelPrimeCalculationManager;
import socket.server.manager.PrimeCalculationManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class RequestHandlerService implements Runnable {
    private Socket socket;

    public RequestHandlerService(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //System.out.println("Waiting for the client request");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {


            System.out.println(Thread.currentThread().getName());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RequestObject object = (RequestObject) objectInputStream.readObject();
            System.out.println("Message from client: " + object.toString());

            //PrimeCalculationManager m = new PrimeCalculationManager();
            //int nPrimes = m.findPrimes(Integer.parseInt(object.args.get("n")));

            String managerName = object.managerName;
            String methodName = object.method;
            String argsN  = object.args.get("n");



                Class<?> c = Class.forName("socket.server.manager."+managerName);
                Constructor<?> cons = c.getConstructor();
                Object obj = cons.newInstance();

            Class objClass = obj.getClass();
            System.out.println(objClass.getName());

            Method[] methods = objClass.getMethods();

            Arrays.stream(methods).forEach(m-> System.out.println(m.getName()));

            //int nPrimes = (int)method.invoke(obj,Integer.parseInt(argsN));

            //System.out.println("nPrimes = "+nPrimes);

            int nPrimes = (int) ParallelPrimeCalculationManager.findPrimes(Integer.parseInt(argsN));

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(nPrimes);

            return;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                objectInputStream.close();
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;

        }
    }
}
