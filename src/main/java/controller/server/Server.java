package controller.server;

import controller.commitments.SessionManagerCommitments;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private SessionManagerCommitments sessionManagerCommitments;
    private ServerSocket serverSocket;
    private int port;

    public Server(SessionManagerCommitments sessionManagerCommitments, int port) {
        this.sessionManagerCommitments = sessionManagerCommitments;
        this.port = port;
    }

    public void main() {
        try {
            try {
                serverSocket = new ServerSocket(port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    sessionManagerCommitments.addSocket(socket);
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
