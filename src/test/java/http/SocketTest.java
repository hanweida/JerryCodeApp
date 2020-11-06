package http;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket编程
 */
public class SocketTest {
    @Test
    public void Test(){
        serverScoket();
    }
    public static void serverScoket(){
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serverClient(){
        try {

            Socket socket = new Socket();
            ServerSocket serverSocket = new ServerSocket(8000);
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
