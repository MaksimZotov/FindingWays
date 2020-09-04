package controller.client;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReader extends Thread {
    private Client client;
    private ObjectInputStream in;

    ClientReader(Client client) {
        this.client = client;
        in = client.getIn();
        start();
    }

    @Override
    public void run() {
        Object data;
        try {
            while (true) {
                data = in.readObject();
                client.handleDataFromServer(data);
            }
        } catch (IOException | ClassNotFoundException ignored) { }
    }
}