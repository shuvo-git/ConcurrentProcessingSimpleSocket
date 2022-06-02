package socket.server.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestHandlerService implements Runnable {
    private ServerSocket server;

    public RequestHandlerService(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {
        //System.out.println("Waiting for the client request");
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            Socket socket = this.server.accept();
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            String message = (String) objectInputStream.readObject();
            System.out.println("Message from client: " + message);


            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("Hi client : " + message);

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
