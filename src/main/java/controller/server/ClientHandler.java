package controller.server;

import controller.commitments.AbstractSession;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private AbstractSession abstractSession;
    private int id;

    public ClientHandler(Socket socket, AbstractSession abstractSession, int id) {
        this.socket = socket;
        this.abstractSession = abstractSession;
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

    public void setAbstractSession(AbstractSession abstractSession) { this.abstractSession = abstractSession; }

    public int getClientId() { return id; }

    private void handleDataFromClient() {
        Object data;
        try {
            while (true) {
                data = in.readObject();
                abstractSession.handleDataFromClient(data, id);
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
