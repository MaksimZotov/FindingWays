package model.componentsofthemodel;

import java.io.*;

import controller.client.Client;
import controller.commitments.ViewCommitments;
import model.componentsofthemodel.commitments.ModelOfFindingWaysCommitments;

public class ModelOfFindingWaysToOnlineMode implements ModelOfFindingWaysCommitments {
    private Client client;

    public ModelOfFindingWaysToOnlineMode(ViewCommitments viewCommitments) {
        client = new Client(viewCommitments, 666);
    }

    @Override
    public void createField(int height, int width, int maxNumberOfMoves) throws IOException {
        client.createConnection();
        StringBuilder stringBuilder =
                new StringBuilder("createField&&").
                append(height).
                append("&&").
                append(width).
                append("&&").
                append(maxNumberOfMoves);
        client.sendDataToServer(stringBuilder.toString());
    }

    @Override
    public void calculateWays() {
        client.sendDataToServer("calculateWays");
    }

    @Override
    public void showNextCalculatedWay() {
        client.sendDataToServer("showNextCalculatedWay");
    }

    @Override
    public void showPreviousCalculatedWay() {
        client.sendDataToServer("showPreviousCalculatedWay");
    }

    @Override
    public void setNumberOfCell(int row, int column, int number) {
        StringBuilder stringBuilder =
                new StringBuilder("setNumberOfCell&&").
                        append(row).
                        append("&&").
                        append(column).
                        append("&&").
                        append(number);
        client.sendDataToServer(stringBuilder.toString());
    }

    @Override
    public State getState() {
        try {
            throw new Exception("When you work online, you can't directly get the model state. " +
                    "You have to wait until you receive information about the model state from the server. " +
                    "A class ClientReader is responsible for processing information from the server");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
