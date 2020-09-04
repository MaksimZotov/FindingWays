package model.forthecontroller;

import controller.commitments.SessionManagerCommitments;
import controller.server.ClientHandler;

import java.net.Socket;
import java.util.ArrayList;

public class SessionManager implements SessionManagerCommitments {
    private ArrayList<Session> sessions = new ArrayList<>();
    private int sessionIndex;

    @Override
    public void addSocket(Socket socket) {
        sessions.add(new Session());
        sessions.get(sessionIndex).addClient(new ClientHandler(socket, sessions.get(sessionIndex), 0));
        sessionIndex++;
    }

    @Override
    public void removeSession(int sessionIndex) {
        sessions.remove(sessionIndex);
        sessionIndex--;
    }
}