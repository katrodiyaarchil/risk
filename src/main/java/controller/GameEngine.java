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
     * This is a method to show all player details like PlayerNames,armies,Countries
     * owned
     * 
     */
    public void showAllPlayerWithArmies() {
        d_LEB.setResult(
                ":::::::::::::::::::::::::::: Players, Armies, Countries, Cards :::::::::::::::::::::::::::::::::::::::");
        d_PlayerList = d_GameModelNew.getAllPlayers();
        for (Player l_Player : d_PlayerList) {
            d_LEB.setResult("\n" + l_Player.getPlayerName() + "-->" + "armies assigned:" + l_Player.getPlayerArmies());
            d_CpView.setCommandAcknowledgement(
                    "\n" + l_Player.getPlayerName() + "-->" + "armies assigned:" + l_Player.getPlayerArmies());
            d_LEB.setResult("\n" + "Countries Assigned: ");
            d_CpView.setCommandAcknowledgement("\n" + "Countries Assigned: ");

            for (Country l_Country : l_Player.getCountryList()) {
                d_LEB.setResult(l_Country.getCountryName() + ",");
                d_CpView.setCommandAcknowledgement(l_Country.getCountryName() + ",");
            }
            if (l_Player.getCardList().size() > 0) {
                d_LEB.setResult("\n" + "Cards Assigned: ");
                d_CpView.setCommandAcknowledgement("\n" + "Cards Assigned: ");
                ArrayList<String> l_CardList = l_Player.getCardList();
                for (String l_Str : l_CardList) {
                    d_LEB.setResult(l_Str + ",");
                    d_CpView.setCommandAcknowledgement(l_Str + ",");
                }
            }
        }
        d_LEB.setResult(
                ":::::::::::::::::::::::::::: Players, Armies, Countries, Cards :::::::::::::::::::::::::::::::::::::::");
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
     * @param p_GamePhase takes the phase object and shows the map accordingly
     */
    public void showMap(Phase p_GamePhase) {
        if (!p_GamePhase.getClass().getSimpleName().equals("Edit")) {
            d_LEB.setResult(":::::::::::::::::::::::::::: ShowMap :::::::::::::::::::::::::::::::::::::::");
            d_PlayerList = d_GameModelNew.getAllPlayers();
            ArrayList<Continent> l_ContinentList = d_GameModelNew.getMap().getContinentList();
            if (l_ContinentList.size() > 0) {
                d_LEB.setResult("\n");
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_LEB.setResult("Continent: " + l_Continent.getContinentName() + "\n");
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_LEB.setResult("\n");
                    d_CpView.setCommandAcknowledgement("\n");
                    for (Country l_Country : l_CountryList) {
                        d_LEB.setResult("Country: " + l_Country.getCountryName());
                        d_CpView.setCommandAcknowledgement("Country: " + l_Country.getCountryName());

                        if (l_Country.getCountryOwnerPlayer() != null) {
                            d_LEB.setResult("-->Owner: " + l_Country.getCountryOwnerPlayer().getPlayerName());
                            d_CpView.setCommandAcknowledgement(
                                    "-->Owner: " + l_Country.getCountryOwnerPlayer().getPlayerName());
                            d_LEB.setResult("-->Armies deployed: " + l_Country.getNoOfArmies());
                            d_CpView.setCommandAcknowledgement("-->Armies deployed: " + l_Country.getNoOfArmies());
                        }

                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (l_NeighborList.size() > 0) {
                            d_LEB.setResult("\n" + "--> Borders : ");
                            d_CpView.setCommandAcknowledgement("\n" + "--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_LEB.setResult(l_Str + ",");
                                d_CpView.setCommandAcknowledgement(l_Str + ",");
                            }
                        }
                        d_LEB.setResult("\n");
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_LEB.setResult("\n");
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        } else {
            ArrayList<Continent> l_ContinentList = d_GameModelNew.getMap().getContinentList();
            if (l_ContinentList.size() > 0) {
                d_LEB.setResult("\n");
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_LEB.setResult("Continent: " + l_Continent.getContinentName() + "\n");
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_LEB.setResult("Countries:" + "\n");
                    d_CpView.setCommandAcknowledgement("Countries:" + "\n");
                    for (Country l_Country : l_CountryList) {
                        d_LEB.setResult(l_Country.getCountryName());
                        d_CpView.setCommandAcknowledgement(l_Country.getCountryName());
                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (l_NeighborList.size() > 0) {
                            d_LEB.setResult("--> Borders : ");
                            d_CpView.setCommandAcknowledgement("--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_LEB.setResult(l_Str + " ");
                                d_CpView.setCommandAcknowledgement(l_Str + " ");
                            }
                        }
                        d_LEB.setResult("\n");
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_LEB.setResult("\n");
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        }
        d_LEB.setResult(":::::::::::::::::::::::::::: ShowMap :::::::::::::::::::::::::::::::::::::::");
    }
}
