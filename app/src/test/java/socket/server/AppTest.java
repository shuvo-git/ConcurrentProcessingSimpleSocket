/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package socket.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import socket.server.io.RequestObject;
import socket.server.manager.PrimeCalculationManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

class AppTest {
    InetAddress host;
    private static final int PORT = 9876;

    @BeforeEach
    void initialize() throws IOException {
        Runnable runnable = () -> {
            try {
                new App().startServerAndAcceptRequest();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        };
        Thread serverThread = new Thread(runnable);
        serverThread.start();
        host = InetAddress.getLocalHost();
    }

    @Test
    void sendRequest() throws IOException, ClassNotFoundException, InterruptedException {
        int[] ns = new int[]{
                200,300,400,500,600,
                700,800,900,1000,10000,
                50000,100000,200000,500000,1000000,
                20000000,50000000,100000000,200000000,500000000,
                1000000000,2000000000
        };
        for (int i = 0; i < 15; i++) {
            // opening new socket for every request here. we can also send multiple requests with one socket.
            Socket socket = new Socket(host.getHostName(), PORT);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


            RequestObject object = new RequestObject();
            object.managerName = "PrimeCalculationManager";
            object.method = "findPrimes";
            object.args = new HashMap<>();
            object.args.put("n",ns[i]+"");

            objectOutputStream.writeObject(object);





            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            String message = (String) objectInputStream.readObject().toString();
            System.out.println("Message from server: " + message);

            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
            Thread.sleep(100);
        }
    }

    @AfterEach
    void cleanup() throws IOException, InterruptedException {
        Socket socket = new Socket(host.getHostName(), PORT);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject("EXIT");
        Thread.sleep(100);
        objectOutputStream.close();
        socket.close();
    }
}
