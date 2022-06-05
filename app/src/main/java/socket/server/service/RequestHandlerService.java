package socket.server.service;

import socket.server.io.RequestObject;
import socket.server.manager.ParallelPrimeCalculationManager;
import socket.server.manager.PrimeCalculationManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

            int nPrimes = (int)ParallelPrimeCalculationManager.findPrimes(Integer.parseInt(object.args.get("n")));

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
