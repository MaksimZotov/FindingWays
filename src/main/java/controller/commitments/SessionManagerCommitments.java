package controller.commitments;

import java.net.Socket;

public interface SessionManagerCommitments {
    void addSocket(Socket serverSocket);
    void removeSession(int sessionIndex);
}
