package controller.client;

import controller.commitments.ViewCommitments;

import java.io.*;
import java.net.Socket;

public class Client {
    private ViewCommitments viewCommitments;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ClientSender sender;
    private ClientReader reader;
    private int port;

    public Client(ViewCommitments viewCommitments, int port) {
        this.viewCommitments = viewCommitments;
        this.port = port;
    }

    public void createConnection() throws IOException {
        clientSocket = new Socket("localhost", port);
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        sender = new ClientSender(out);
        reader = new ClientReader(this);
    }

    public void closeConnection() throws IOException {
        sender.sendDataToServer("closeConnection");
        clientSocket.close();
        in.close();
        out.close();
    }

    public void sendDataToServer(Object data) { sender.sendDataToServer(data); }

    void handleDataFromServer(Object data) { viewCommitments.getUpdatedDataAboutTheModel(data); }

    ObjectInputStream getIn() { return in; }
}
