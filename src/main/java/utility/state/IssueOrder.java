package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;
/**
 * The IssueOrder phase extends the Phase class and implements methods specific to this phase.
 * It handles issuing orders during gameplay.
 */

public class IssueOrder extends Phase {
    LogEntryBuffer d_Leb;
    /**
     * Constructs an IssueOrder object with a GameEngine and CommandPrompt object, initializing the log entry buffer
     * and proceeding to issue orders.
     *
     * @param p_Ge The GameEngine object
     * @param p_Vw The CommandPrompt object
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

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String tournament(String p_string, String p_CommandStringFromInput) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

}
