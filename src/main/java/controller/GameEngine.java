package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Continent;
import model.Country;
import model.GameModel;
import model.Player;
import observerpattern.LogEntryBuffer;
import utility.state.Edit;
import utility.state.GameSaved;
import utility.state.IssueOrder;
import utility.state.Phase;
import utility.state.Startup;
import view.CommandPrompt;
import dnl.utils.text.table.TextTable;

/**
 * This is the main controller class of MVC model.
 * This class has a references of View, Models and various child controllers.
 * This class acts as an intermediary between models/controllers and view.
 */
public class GameEngine {
    /**
     * Object of the GameModel which internally holds Map object
     */
    private GameModel d_GameModel;
    /**
     * Object of view
     */
    private CommandPrompt d_CpView;
    /**
     * Object of MapController to access Map model from GameEngine
     */
    private MapController d_MapController;
    /**
     * ArrayList of players playing the game
     */
    private ArrayList<Player> d_PlayerList;
    /**
     * Object of PlayerController to access Player Model methods
     */
    private PlayerController d_PlayerController;
    /**
     * Object of the phase class to implement phases in game
     */
    private Phase d_GamePhase;
    /**
     * Object to edit logfile
     */
    private LogEntryBuffer d_LEB;
    /**
     * static variable used in tournament mode
     */
    static int NUM = 0;
    /**
     * hashmap to store the result of tournament and display at last
     */
    private HashMap<String, ArrayList<String>> d_TournamentResult;

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
     * @return the GameModel reference
     */
    public GameModel getGameModel() {
        return this.d_GameModel;
    }

    /**
     * This controller takes view and model as arguments and use throughout the
     * game.
     * 
     * @param p_CpView    main view of the game.
     * @param p_GameModel main model of the game.
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
     * This is a child class of the GameEngine which listens to the actions
     * performed by button in view.
     * This class implements the ActionListener and override the actionPerformed
     * method.
     * This class is responsible for passing data from view to models/child
     * controllers.
     */
    public class CommandListener implements ActionListener {
        /**
         * {@inheritDoc}
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
                    case "tournament":
                        d_LEB.setResult(l_CommandStringFromInput);
                        d_CpView.setCommandAcknowledgement(
                                d_GamePhase.tournament("tournament", l_CommandStringFromInput));

                        break;

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

                    case "loadgame":
                        d_LEB.setResult(l_CommandStringFromInput);
                        loadGame(l_CommandStringFromInput);
                        break;

                    case "savegame":
                        d_LEB.setResult(l_CommandStringFromInput);
                        saveGame(l_CommandStringFromInput);
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
     * startup Phase as well as assigning reinforcements to the player
     * 
     * @throws Exception this is user defined exception based on AssignCountries if
     *                   there are no players assigned
     *                   then it will throw an exception
     * 
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
        d_PlayerList = d_GameModel.getAllPlayers();
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
        if (!"Edit".equals(p_GamePhase.getClass().getSimpleName())) {
            d_LEB.setResult(":::::::::::::::::::::::::::: ShowMap :::::::::::::::::::::::::::::::::::::::");
            d_PlayerList = d_GameModel.getAllPlayers();
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
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
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
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

    /**
     * This method calls the loadGame method of the GameModel to load previously
     * saved game.
     * After loading all the objects to their respective places, it changes the
     * state of the game accordingly.
     * 
     * @param p_Command command entered by the user.
     */
    public void loadGame(String p_Command) {
        boolean l_Flag = false;
        this.d_GameModel = GameModel.loadGame(p_Command.split(" ")[1]);
        if (this.d_GameModel == null) {
            d_CpView.setCommandAcknowledgement("\nGame not found");
            d_LEB.setResult("Game not found");
            return;
        }
        d_LEB.setResult("Game Loaded");
        d_CpView.setCommandAcknowledgement("\nGame Loaded");

        if (this.d_GameModel.getAllPlayers().size() <= 1) {
            this.setPhase(new Startup(this, d_CpView));
        } else {
            ArrayList<Player> l_Players = this.d_GameModel.getAllPlayers();
            for (Player l_P : l_Players) {
                if (l_P.getCountriesSize() > 0) {
                    l_Flag = true;
                    break;
                }
            }
            if (l_Flag == true) {
                this.setPhase(new IssueOrder(this, d_CpView));
            } else {
                this.setPhase(new Startup(this, d_CpView));
            }
        }
    }

    /**
     * This method calls the savegame method of GameModel to save the game during
     * gameplay.
     * Once the game is saved, this method also sets it phase to GameSaved phase.
     * 
     * @param p_Command command entered by the user
     */
    public void saveGame(String p_Command) {
        this.d_GameModel.saveGame(p_Command.split(" ")[1]);
        this.setPhase(new GameSaved(this, d_CpView));
    }

    /**
     * This method implements tournament mode.
     * <ul>
     * <li>It takes the input string from the user and initializes variables.</li>
     * <li>Based on number of maps and number of games we will iterate the main
     * tournament loop.</li>
     * <li>Based on number of player strategies we will create the players with
     * respective strategies.</li>
     * <li>Now we will call the tournament startup phase to assign the countries to
     * the players.
     * <li>
     * <li>Iterate all the players starting with assign reinforcements, player issue
     * order and player next order.</li>
     * <li>Now we will terminate the above loop in two cases : (i) if we have a
     * winner (ii) If the number of turns are exceeded.</li>
     * <li>So once a game is completed we will clear the game model object and map
     * object.</li>
     * <li>As the game is completed, we store the result in the hashmap.</li>
     * </ul>
     * 
     * @param p_InputString tournament command with list of maps, player strategies,
     *                      number of games, number of turns
     * @throws Exception for addPlayer
     */
    public void tournament(String p_InputString) throws Exception {

        int l_M = 0;
        int l_P = 0;
        int l_G = 0;
        int l_D = 0;
        String[] l_MapList = null;
        String[] l_PlayerStrategyList = null;
        ArrayList<Map> l_Maps = new ArrayList<Map>();
        ArrayList<File> l_Files = new ArrayList<File>();
        String l_Path = "resource/";
        String[] l_CommandArray = p_InputString.split(" ");
        for (String s : l_CommandArray) {
            System.out.println("---> " + s);
        }
        if ("-M".equals(l_CommandArray[1])) {
            l_MapList = l_CommandArray[2].split(",");
            if (l_MapList.length < 1) {
                d_LEB.setResult("Atleast 1 map file required");
                throw new Exception("Atleast 1 map file required");// throw exception

            } else
                l_M = l_MapList.length;
            for (int i = 0; i < l_M; i++) {
                System.out.println(i + "---> " + l_MapList[i]);
                System.out.println("Path--> " + l_Path + l_MapList[i]);
                File l_F = new File(l_Path + l_MapList[i]);
                if (!l_F.exists()) {
                    d_LEB.setResult("File does not Exists");
                    throw new Exception("File does not Exists");// throw exception

                } else
                    l_Files.add(l_F);
            }
        }
        if ("-P".equals(l_CommandArray[3])) {
            l_PlayerStrategyList = l_CommandArray[4].split(",");
            if (l_PlayerStrategyList.length > 4 || l_PlayerStrategyList.length < 2) {
                d_LEB.setResult("Number of Player strategies should be in between 2 to 4 both inclusive");
                throw new Exception("Number of Player strategies should be in between 2 to 4 both inclusive");// throw
                                                                                                              // exception

            } else {
                l_P = l_PlayerStrategyList.length;
            }
        }
        if ("-G".equals(l_CommandArray[5])) {
            int l_NumGames = Integer.parseInt(l_CommandArray[6]);
            if (l_NumGames < 1) {
                d_LEB.setResult("Number of Games should be more than 1");
                throw new Exception("Number of Games should be more than 1");// throw exception

            } else
                l_G = l_NumGames;
        }
        if ("-D".equals(l_CommandArray[7])) {
            int l_MaxTurns = Integer.parseInt(l_CommandArray[8]);
            if (l_MaxTurns < 15) {
                d_LEB.setResult("Number of turns should be at least 15");
                throw new Exception("Number of turns should be at least 15");// throw exception

            } else
                l_D = l_MaxTurns;
        }
        d_TournamentResult = new HashMap<>();

        for (int i = 0; i < l_M; i++) {
            d_CpView.setCommandAcknowledgement("\n=============================================\n");
            d_CpView.setCommandAcknowledgement("\nMap number:" + (i + 1) + "\n");
            d_LEB.setResult("\n=============================================\n");
            d_LEB.setResult("\nMap number:" + (i + 1) + "\n");
            ArrayList<String> l_Result = new ArrayList<>();

            for (int j = 0; j < l_G; j++) {
                d_GameModel.getMap().loadMap(l_MapList[i]);
                d_CpView.setCommandAcknowledgement("\n=============================================\n");
                d_CpView.setCommandAcknowledgement("\nGame number:" + (j + 1) + "\n");
                d_LEB.setResult("\n=============================================\n");
                d_LEB.setResult("\nGame number:" + (j + 1) + "\n");

                d_GameModel.getAllPlayers().clear();

                for (int k = 0; k < l_P; k++) {
                    d_GameModel.addPlayer("Player" + (NUM++), l_PlayerStrategyList[k]);
                }

                d_GameModel.tournamentstartUpPhase();
                int l_Noofturns = 0;
                while (true) {
                    d_GameModel.assignReinforcementArmies();
                    this.getPlayerController().playerIssueOrder();
                    this.getPlayerController().playerNextOrder();
                    if (this.getPlayerController().getWinner() != null) {
                        l_Result.add(this.getPlayerController().getWinner().getPlayerStrategy().strategyName());
                        d_CpView.setCommandAcknowledgement(
                                this.getPlayerController().getWinner().getPlayerName() + "is the winner");
                        d_LEB.setResult(this.getPlayerController().getWinner().getPlayerName() + "is the winner");
                        d_GameModel.getMap().reset();
                        break;
                    }

                    if (l_Noofturns == l_D) {
                        d_CpView.setCommandAcknowledgement("\nMatch is draw");
                        d_LEB.setResult("\nMatch is draw");
                        l_Result.add("Draw");
                        d_GameModel.getAllPlayers().clear();
                        d_GameModel.getMap().reset();
                        break;
                    }
                    l_Noofturns++;
                }
                d_TournamentResult.put(l_MapList[i], l_Result);
            }
        }
        printTournamentResult(l_M, l_G, l_D, d_TournamentResult, l_PlayerStrategyList);
    }

    /**
     * This method prints the tournament result for each individual map and each
     * individual game.
     * 
     * @param p_M                  Number of maps
     * @param p_G                  Number of games
     * @param p_D                  Number of turns
     * @param p_tournamentResult   Result of the tournament
     * @param p_PlayerStrategyList Player strategy list
     */
    private void printTournamentResult(int p_M, int p_G, int p_D, HashMap<String, ArrayList<String>> p_tournamentResult,
            String[] p_PlayerStrategyList) {

        // Define table data
        int numMaps = p_tournamentResult.size();
        String[] headers = new String[p_G + 1];
        headers[0] = "";
        for (int i = 0; i < p_G; i++) {
            headers[i + 1] = "Game " + (i + 1);
        }
        String[][] data = new String[numMaps][p_G + 1];
        int mapIndex = 0;
        for (String map : p_tournamentResult.keySet()) {
            ArrayList<String> gameResults = p_tournamentResult.get(map);
            data[mapIndex][0] = map;
            for (int i = 0; i < p_G; i++) {
                data[mapIndex][i + 1] = gameResults.get(i);
            }
            mapIndex++;
        }

        // Create text table
        TextTable tt = new TextTable(headers, data);
        tt.setAddRowNumbering(true);
        tt.setSort(0);
        tt.printTable();

        // Create a custom PrintStream using the custom OutputStream
        String table = "";
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            tt.printTable(ps, 0);
            table = baos.toString(utf8);
        } catch (Exception e) {}

        String result = "\n=============================================\n";
        result += "============== TOURNAMENT RESULT ==============\n";
        result += "=============================================\n";
        result += "M: " + String.join(", ", p_tournamentResult.keySet()) + "\n";
        result += "P: " + String.join(", ", p_PlayerStrategyList) + "\n";
        result += "G: " + p_G + "\n";
        result += "D: " + p_D + "\n";
        result += table;

        d_LEB.setResult(result);
        d_CpView.setCommandAcknowledgement(result);
    }

    public HashMap<String, ArrayList<String>> getTournamentResult() {
        return this.d_TournamentResult;
    }
}