package controller;

import java.util.ArrayList;
import view.CommandPrompt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import model.Continent;
import model.Country;
import model.GameModelNew;
import model.Player;
import observerpattern.LogEntryBuffer;
import utility.state.Edit;
import utility.state.Phase;

/**
 * This class serves as the main controller in the MVC model.
 * It manages the interactions between the view, models, and various child
 * controllers.
 * Acting as an intermediary, it facilitates communication between
 * models/controllers and the view.
 */

public class GameEngine {
    private GameModel d_GameModel;
    private CommandPrompt d_CpView;
    private MapController d_MapController;
    private ArrayList<Player> d_PlayerList;
    private PlayerController d_PlayerController;
    private Phase d_GamePhase;
    private LogEntryBuffer d_LEB;

    /**
     * Constructs a new GameEngine with the specified CommandPrompt view and
     * GameModel.
     * 
     * @param p_CpView    The CommandPrompt view.
     * @param p_GameModel The GameModel.
     */
    public GameEngine(CommandPrompt p_CpView, GameModel p_GameModel) {
        d_GameModel = p_GameModel;
        d_CpView = p_CpView;
        d_MapController = new MapController(this.d_GameModel.getMap());
        d_PlayerController = new PlayerController(d_GameModel, d_CpView, this);
        d_CpView.commandSendButtonListener(new CommandListener());
        setPhase(new Edit(this, getViewObject()));
        d_LEB = new LogEntryBuffer();
    }

    /**
     * This method returns the reference of the MapController
     * 
     * @return Mapcontroller reference
     */
    public MapController getMapController() {
        return this.d_MapController;
    }

    /**
     * This method returns the reference of the command prompt view
     * 
     * @return Command prompt reference
     */
    public CommandPrompt getViewObject() {
        return this.d_CpView;
    }

    /**
     * This method returns the reference of player controller
     * 
     * @return the player controller reference
     */
    public PlayerController getPlayerController() {
        return this.d_PlayerController;
    }

    /**
     * This method returns the reference of the game model new.
     * 
     * @return the GameModelNew reference
     */
    public GameModelNew getGameModel() {
        return this.d_GameModelNew;
    }

    /**
     * This method sets the phase in game engine
     * 
     * @param p_phase object of phase to set
     */
    public void setPhase(Phase p_phase) {
        d_GamePhase = p_phase;

    }

    /**
     * This method returns the current phase
     * 
     * @return object of current phase
     */
    public Phase getPhase() {
        return this.d_GamePhase;

    }

    /**
     * This class extends the GameEngine class and listens to actions triggered by
     * buttons in the view.
     * It implements the ActionListener interface and overrides the actionPerformed
     * method.
     * Responsible for transferring data from the view to models/child controllers.
     */
    public class CommandListener implements ActionListener {
        /**
         * On click of the button in view, this method gets the string which user
         * entered.
         * Based on the type of the command, it will call the method of specific
         * controllers.
         * <ul>
         * <li>editcontinent, editcountry, editneighbor commands are handled by the map
         * controller's editmap method.</li>
         * <li>savemap, loadmap, editmap, validatemap commands are also handled by the
         * map controller's respective methods.</li>
         * <li>gameplayer, showmap commands are handled by the GameEngine's respective
         * methods.</li>
         * <li>For all the methods called from the various cases here, feedback is shown
         * on the view.</li>
         * </ul>
         */
        @Override
        public void actionPerformed(ActionEvent l_E) {
            try {
                String l_CommandStringFromInput = d_CpView.getCommandInput().trim();
                switch (l_CommandStringFromInput.split(" ")[0]) {
                    case "editcontinent":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(
                                d_GamePhase.editContinent("editcontinent", l_CommandStringFromInput));

                        break;

                    case "editcountry":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(
                                d_GamePhase.editCountry("editcountry", l_CommandStringFromInput));

                        break;

                    case "editneighbor":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(
                                d_GamePhase.editCountry("editneighbor", l_CommandStringFromInput));

                        break;

                    case "showmap":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_GamePhase.showMap();

                        break;

                    case "savemap":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(d_GamePhase.saveMap(l_CommandStringFromInput));

                        break;

                    case "editmap":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(d_GamePhase.editMap(l_CommandStringFromInput));

                        break;

                    case "validatemap":

                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(d_GamePhase.validateMap());

                        break;

                    case "loadmap":
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(d_GamePhase.loadMap(l_CommandStringFromInput));

                        break;

                    case "gameplayer":
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(
                                d_GamePhase.addPlayers("GamePlayer", l_CommandStringFromInput));

                        break;

                    case "assigncountries":
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_GamePhase.assignCountries();

                        break;

                    case "reset":
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_MapController.reset();
                        d_CpView.setCommandAcknowledgement("The Map is Reset" + "\n");
                        break;

                    default:
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement("Invalid Command. Please try again.\n");
                        d_LEB.setResult("Invalid Command. Please try again.\n");
                        break;
                }
                d_CpView.setCommandInput("");
            } catch (Exception p_Exception) {
                System.out
                        .println("Exception in ActionPerformed Method in ActionListener : " + p_Exception.getMessage());
            }
        }
    }

    /**
     * this Method will take inputs from the user and will add or remove player
     * according to the inputs provided by the user
     * 
     * @param p_Command The command.
     * @param p_Str     The string.
     * @return A string indicating the number of players added and removed.
     * @throws Exception if an error occurs.
     */
    public String editPlayer(String p_Command, String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        int l_Counter = 1;
        int l_AddCounter = 0;
        int l_RemoveCounter = 0;
        String l_ReturnString = "";
        if (l_CommandArray.length < 3)
            throw new Exception("Please provide valid Parameters to add player");
        while (l_Counter < l_CommandArray.length) {
            if (l_CommandArray[l_Counter].equals("-add")) {
                d_GameModel.addPlayer(l_CommandArray[l_Counter + 1]);
                l_Counter += 2;
                l_AddCounter += 1;
            } else if (l_CommandArray[l_Counter].equals("-remove")) {
                d_GameModel.removePlayer(l_CommandArray[l_Counter + 1]);
                l_Counter += 2;
                l_RemoveCounter += 1;
            } else {
                break;
            }
        }
        if (l_AddCounter > 0) {
            l_ReturnString += "Number of Players Added : " + l_AddCounter + "\n";
        }
        if (l_RemoveCounter > 0) {
            l_ReturnString += "Number of Players Removed : " + l_RemoveCounter + "\n";
        }
        return l_ReturnString;
    }

    /**
     * This Method will take assign countries from command prompt and will do
     * startup Phase as well as assigning reinforcements to the player.
     * 
     * @throws Exception this is user defined exception based on AssignCountries if
     *                   there are no players assigned
     *                   then it will throw an exception
     */
    public void assignCountries() throws Exception {
        d_GameModel.startUpPhase();
    }

    /**
     * Shows all players with their assigned armies.
     */
    public void showAllPlayerWithArmies() {
        d_PlayerList = d_GameModel.getAllPlayers();
        for (Player l_Player : d_PlayerList) {
            d_CpView.setCommandAcknowledgement(
                    "\n" + l_Player.getPlayerName() + "--> " + "armies assigned: " + l_Player.getPlayerArmies());
            d_CpView.setCommandAcknowledgement("\n" + "Countries Assigned: ");
            for (Country l_Country : l_Player.getCountryList()) {
                d_CpView.setCommandAcknowledgement(l_Country.getCountryName() + ",");
            }
        }
    }

    /**
     * This is a method to show all countries and continents, armies on each
     * country, ownership, and connectivity
     * <ul>
     * <li>Map Phase : For Each Continent in Continent List, For each country in
     * that continent, For each neighbor in that country</li>
     * <li>Game Phase : Apart from continent, country and neighbors, it also shows
     * player, thier ownership and their number of armies.</li>
     * </ul>
     * 
     * @param p_BooleanForGamePhaseStarted takes boolean value to show map for map
     *                                     phase or game phase
     */
    public void showMap(Boolean p_BooleanForGamePhaseStarted) {
        if (p_BooleanForGamePhaseStarted) {
            d_PlayerList = d_GameModel.getAllPlayers();
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
            if (!l_ContinentList.isEmpty()) {
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_CpView.setCommandAcknowledgement("\n");
                    for (Country l_Country : l_CountryList) {
                        d_CpView.setCommandAcknowledgement("Country: " + l_Country.getCountryName());
                        if (this.d_PlayerList != null) {
                            for (Player l_Player : d_PlayerList) {
                                if (l_Player.getCountryList().contains(l_Country)) {
                                    d_CpView.setCommandAcknowledgement("\n" + "-->Owner: " + l_Player.getPlayerName());
                                    d_CpView.setCommandAcknowledgement(
                                            "\n" + "-->Armies deployed: " + l_Country.getNoOfArmies());
                                }
                            }
                        }
                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (!l_NeighborList.isEmpty()) {
                            d_CpView.setCommandAcknowledgement("\n" + "--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_CpView.setCommandAcknowledgement(l_Str + ",");
                            }
                        }
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        } else {
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
            if (!l_ContinentList.isEmpty()) {
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_CpView.setCommandAcknowledgement("Countries:" + "\n");
                    for (Country l_Country : l_CountryList) {
                        d_CpView.setCommandAcknowledgement(l_Country.getCountryName());
                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (!l_NeighborList.isEmpty()) {
                            d_CpView.setCommandAcknowledgement("--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_CpView.setCommandAcknowledgement(l_Str + " ");
                            }
                        }
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        }
    }
}
