package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

/**
 * Represents the Edit Phase, which extends the Phase class and implements
 * methods specific to this phase.
 * The Edit Phase handles map editing commands and returns invalid command
 * messages for commands not applicable to this phase.
 */
public class Edit extends Phase {
    LogEntryBuffer d_Leb;

    /**
     * Constructs an Edit object with the specified GameEngine and CommandPrompt
     * objects, and initializes a LogEntryBuffer.
     * 
     * @param p_Ge The GameEngine object.
     * @param p_Vw The CommandPrompt object.
     */
    public Edit(GameEngine p_Ge, CommandPrompt p_Vw) {

        super(p_Ge, p_Vw);
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is the Edit Phase");
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String loadMap(String p_S) {

        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().loadMap(p_S);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);

        d_Ge.setPhase(new Startup(d_Ge, d_Vw));
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editCountry(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);

        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String saveMap(String p_S) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().saveMap(p_S);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
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
    public String editMap(String p_S) {
        System.out.println("entering edit map  ");
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().loadMap(p_S);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editContinent(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String validateMap() {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().validateMap();
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;

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
     */
    @Override
    public String getPhaseName() {
        return "EditPhase";
    }

}
