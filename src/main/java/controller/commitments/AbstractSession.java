package controller.commitments;

import controller.server.ClientHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractSession {
    private ArrayList<ClientHandler> clients;

    public abstract void handleDataFromClient(Object data, int clientId) throws IOException;

    public void sendDataToClient(Object data, int id) {
        for (ClientHandler item : clients)
            item.sendDataToClient(data);
    }

    public void addClient(ClientHandler client) { clients.add(client); }

    public void removeClient(int clientId) {
        Iterator iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientHandler client = (ClientHandler) iterator.next();
            if (client.getClientId() == clientId) {
                iterator.remove();
                return;
            }
        }
    }

    public ClientHandler getClient(int clientId) {
        for (ClientHandler client : clients)
            if(client.getClientId() == clientId)
                return client;
        return null;
    }

}
