package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;
/**
 * The GameOver Phase extends the Phase class and implements methods specific to this phase.
 * It handles the end of the game and provides relevant acknowledgements.
 */

public class GameOver extends Phase {
    LogEntryBuffer d_Leb;

    /**
     * Constructs a GameOver object with a GameEngine and CommandPrompt object, initializing the log entry buffer
     * and ending the game with relevant acknowledgements.
     *
     * @param p_Ge The GameEngine object
     * @param p_Vw The CommandPrompt object
     */
    public GameOver(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        d_Vw.clearTextArea();
        d_Vw.setCommandAcknowledgement("Game Over \n");
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is Game over phase \n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state \n");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String editCountry(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String editContinent(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String editNeighbor(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String saveMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public String validateMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String loadMap(String p_S) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignCountries() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
    }
    /**
     * {@inheritDoc}
     */

    @Override
    public void showMap() {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPhaseName() {
        // TODO Auto-generated method stub
        return "GameOver";
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
