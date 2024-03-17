package utility.state;

import controller.GameEngine;
import observerpattern.*;
import view.CommandPrompt;

/**
 * The Reinforcement Phase extends the Phase class and implements methods specific to this phase.
 * It handles reinforcing armies during gameplay.
 */
public class Reinforcement extends Phase {
    LogEntryBuffer d_Leb;
    /**
     * Constructs a Reinforcement object with a GameEngine and CommandPrompt object, initializing the log entry buffer
     * and proceeding to assign reinforcement armies and display them.
     *
     * @param p_Ge The GameEngine object
     * @param p_Vw The CommandPrompt object
     */

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
    /**
     * {@inheritDoc}
     */

    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void showMap() {
        d_Ge.showMap(this);
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String getPhaseName() {
        return "ReinforcementPhase";
    }

}