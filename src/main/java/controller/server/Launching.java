package controller.server;

import model.forthecontroller.SessionManager;

public class Launching {
    public void launch(int port) { new Server(new SessionManager(), port).main(); }
}
