package controller.client;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientSender extends Thread {
    private boolean runWasLaunchedFromStart = true;
    private ObjectOutputStream out;
    private Object dataFromClient;

    ClientSender(ObjectOutputStream out) {
        this.out = out;
        start();
    }

    @Override
    public void run() {
        if (runWasLaunchedFromStart) {
            runWasLaunchedFromStart = false;
            return;
        }
        try {
            out.writeObject(dataFromClient);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendDataToServer(Object data) {
        dataFromClient = data;
        run();
    }
}
