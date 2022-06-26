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

            String managerName = object.managerName;
            String methodName = object.method;
            String argsN  = object.args.get("n");

            Object obj = InvokerService.classInvoker(managerName);
            Method m = InvokerService.methodInvoker(obj,methodName);
            int nPrimes = InvokerService.invokeMethod(obj,m,argsN);

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
