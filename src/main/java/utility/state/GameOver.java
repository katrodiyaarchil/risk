package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;


public class GameOver extends Phase {
    LogEntryBuffer d_Leb;


    public GameOver(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        d_Vw.clearTextArea();
        d_Vw.setCommandAcknowledgement("Game Over");
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is Game over phase");
    }


    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }


    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
    }


    @Override
    public void showMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
    }


    @Override
    public String getPhaseName() {
        // TODO Auto-generated method stub
        return "GameOver";
    }

}
