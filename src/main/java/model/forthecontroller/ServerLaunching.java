package model.forthecontroller;

import controller.server.Launching;

public class ServerLaunching {
    public static void main(String[] args) {
        Launching launching = new Launching();
        launching.launch(666);
    }
}
