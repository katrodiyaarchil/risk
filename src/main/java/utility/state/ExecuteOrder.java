package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

/**
 * The ExecuteOrder Phase extends the phase class and implements all the methods
 * suitable for that particular phase.
 * It returns invalid command for others which are not compatible with this
 * phase
 */
public class ExecuteOrder extends Phase {
    /**
     * object of LogEntryBuffer class to log in the logfile
     */
    LogEntryBuffer d_Leb;

    /**
     * This is the constructor of ExecuteOrder which initializes Game engine object
     * and command prompt object and assigning log entry buffer
     * It then calls the execute method.
     * 
     * @param p_Ge object of game engine
     * @param p_Vw object of view
     */
    public ExecuteOrder(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        try {
            d_Leb = new LogEntryBuffer();
            d_Leb.setResult("This is the Execute Order Phase");
            d_Ge.getPlayerController().playerNextOrder();
            if (d_Ge.getPlayerController().getNumberOfRounds() == 20) {
                d_Ge.setPhase(new GameOver(p_Ge, p_Vw));
                p_Vw.setCommandAcknowledgement(
                        "The game has reached maximum number of rounds - 20. Hence it is a Draw!");
            }
            if (!"GameOver".equals(d_Ge.getPhase().getPhaseName())) {
                d_Ge.setPhase(new Reinforcement(d_Ge, d_Vw));

            }

        } catch (Exception p_E) {
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
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
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String getPhaseName() {
        return "ExecutePhase";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tournament(String p_string, String p_CommandStringFromInput) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

}
