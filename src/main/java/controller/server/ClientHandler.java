package controller.server;

import controller.commitments.AbstractSession;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private AbstractSession session;
    private int id;

    public ClientHandler(Socket socket, AbstractSession session, int id) {
        this.socket = socket;
        this.session = session;
        this.id = id;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() { handleDataFromClient(); }

    public void sendDataToClient(Object data) {
        try {
            out.writeObject(data);
            out.flush();
            out.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSession(AbstractSession session) { this.session = session; }

    public int getClientId() { return id; }

    private void handleDataFromClient() {
        Object data;
        try {
            while (true) {
                data = in.readObject();
                session.handleDataFromClient(data, id);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
