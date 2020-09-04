package model.forthecontroller;

import controller.commitments.AbstractSession;
import model.componentsofthemodel.commitments.ModelOfFindingWaysCommitments;

import java.io.IOException;

public class Session extends AbstractSession {
    ModelOfFindingWaysCommitments modelCommitments;

    @Override
    public void handleDataFromClient(Object data, int id) throws IOException {
        String[] dataString = ((String) data).split("&&");
        switch (dataString[0]) {
            case "createField" : modelCommitments.createField(
                    Integer.parseInt(dataString[1]),
                    Integer.parseInt(dataString[2]),
                    Integer.parseInt(dataString[3])
            ); break;
            case "setNumberOfCell" : modelCommitments.setNumberOfCell(
                    Integer.parseInt(dataString[1]),
                    Integer.parseInt(dataString[2]),
                    Integer.parseInt(dataString[3])
            ); break;
            case "calculateWays" : modelCommitments.calculateWays(); break;
            case "showNextCalculatedWay" : modelCommitments.showNextCalculatedWay(); break;
            case "showPreviousCalculatedWay" : modelCommitments.showPreviousCalculatedWay();
        }
        sendDataToClient(modelCommitments.getState(), id);
    }
}
