package model.forthecontroller;

import controller.server.Server;

public class ServerLaunching {
    public static void main(String[] args) {
        new Server(new SessionManager(), 666).main();
    }
}
