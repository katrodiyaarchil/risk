package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

/**
 * The IssueOrder Phase extends the phase class and implements all the methods
 * suitable for that particular phase.
 * It returns invalid command for others which are not compatible with this
 * phase
 */
public class IssueOrder extends Phase {
    LogEntryBuffer d_Leb;

    /**
     * This is the constructor of IssueOrder class which initializes Game engine
     * object and command prompt object and assigning log entry buffer
     * This then calls the issue order method .
     *
     * @param p_Ge object of game engine
     * @param p_Vw object of view
     */
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

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void showMap() {
        d_Ge.showMap(this);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("/n" + "Invalid command in state " + this.getClass().getSimpleName() + "\n");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String getPhaseName() {
        return "Issueorder";
    }

}
