package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;


public class IssueOrder extends Phase {
    LogEntryBuffer d_Leb;


    public IssueOrder(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        try {
            d_Leb = new LogEntryBuffer();
            d_Leb.setResult("This is the Issue Order Phase");
            d_Ge.getPlayerController().playerIssueOrder();
            d_Ge.setPhase(new ExecuteOrder(p_Ge, p_Vw));
        } catch (Exception p_E) {
        }
    }


    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");

    }


    @Override
    public void showMap() {
        d_Ge.showMap(this);
    }


    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String getPhaseName() {
        return "Issueorder";
    }

}
