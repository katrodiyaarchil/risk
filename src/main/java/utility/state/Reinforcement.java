package utility.state;

import controller.GameEngine;
import observerpattern.*;
import view.CommandPrompt;


public class Reinforcement extends Phase {
    LogEntryBuffer d_Leb;


    public Reinforcement(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is the Reinforcement Phase");
        try {
            d_Ge.getGameModel().assignReinforcementArmies();
            d_Ge.showAllPlayerWithArmies();
        } catch (Exception e) {
            d_Vw.setCommandAcknowledgement(e.getMessage() + "\n");
            d_Leb.setResult(e.getMessage().toString());
        }

        d_Ge.setPhase(new utility.state.IssueOrder(d_Ge, d_Vw));
    }


    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }


    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
    }


    @Override
    public void showMap() {
        d_Ge.showMap(this);
    }


    @Override
    public String getPhaseName() {
        return "ReinforcementPhase";
    }

}