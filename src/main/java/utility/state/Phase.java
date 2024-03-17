package utility.state;

import controller.GameEngine;
import view.CommandPrompt;

/**
 * Represents an abstract class for game phases, each phase extending this class
 * represents different game states.
 * Specific game phases implement methods defined in this class for their
 * functionalities.
 */
public abstract class Phase {
    public GameEngine d_Ge;
    public CommandPrompt d_Vw;

    /**
     * Constructs a Phase object with specified GameEngine and CommandPrompt
     * objects.
     * 
     * @param p_Ge The GameEngine object.
     * @param p_Vw The CommandPrompt object.
     */
    public Phase(GameEngine p_Ge, CommandPrompt p_Vw) {
        d_Ge = p_Ge;
        d_Vw = p_Vw;
    }

    /**
     * Executes the editMap command based on the current phase.
     * 
     * @param p_S The command entered by the player.
     * @return Acknowledgement message.
     */
    abstract public String editMap(String p_S);

    /**
     * Executes the editCountry command based on the current phase.
     * 
     * @param p_S  The first parameter for the editCountry command.
     * @param p_S1 The second parameter for the editCountry command.
     * @return Acknowledgement message.
     */
    abstract public String editCountry(String p_S, String p_S1);

    /**
     * Executes the editContinent command based on the current phase.
     * 
     * @param p_S  The first parameter for the editContinent command.
     * @param p_S1 The second parameter for the editContinent command.
     * @return Acknowledgement message.
     */
    abstract public String editContinent(String p_S, String p_S1);

    /**
     * Executes the editNeighbor command based on the current phase.
     * 
     * @param p_S  The first parameter for the editNeighbor command.
     * @param p_S1 The second parameter for the editNeighbor command.
     * @return Acknowledgement message.
     */
    abstract public String editNeighbor(String p_S, String p_S1);

    /**
     * Executes the saveMap command based on the current phase.
     * 
     * @param p_S The command entered by the player.
     * @return Acknowledgement message.
     */
    abstract public String saveMap(String p_S);

    /**
     * Executes the validateMap command based on the current phase.
     * 
     * @return Acknowledgement message.
     */
    abstract public String validateMap();

    /**
     * Executes the loadMap command based on the current phase.
     * 
     * @param p_S The command entered by the player.
     * @return Acknowledgement message.
     */
    abstract public String loadMap(String p_S);

    /**
     * Executes the addPlayers command based on the current phase.
     * 
     * @param p_S  The first parameter for the addPlayers command.
     * @param p_S1 The second parameter for the addPlayers command.
     * @return Acknowledgement message.
     */
    abstract public String addPlayers(String p_S, String p_S1);

    /**
     * Assigns countries to players based on the current phase.
     */
    abstract public void assignCountries();

    /**
     * Displays the map based on the current phase.
     */
    abstract public void showMap();

    /**
     * Gets the name of the current phase.
     * 
     * @return The name of the current phase.
     */
    abstract public String getPhaseName();
}
