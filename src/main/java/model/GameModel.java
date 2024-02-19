package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

/**
 * Represents the GameModel class which manages the game data and logic.
 */
public class GameModel {
    private Map d_Map;
    private Player d_PlayerID;
    private ArrayList<Player> d_PlayerList;
    private Queue<Player> d_PlayerQueue = new LinkedList<Player>();

    /**
     * Default constructor which initializes the map and player list.
     */
    public GameModel() {
        this.d_Map = new Map();
        this.d_PlayerList = new ArrayList<Player>();
    }

    /**
     * Constructor with a parameter to set the map.
     * 
     * @param p_Map The map to be set for the game model.
     */
    public GameModel(Map p_Map) {
        this.d_Map = p_Map;
        this.d_PlayerList = new ArrayList<Player>();
    }

    /**
     * Get method for retrieving the map.
     * 
     * @return The map associated with the game model.
     */
    public Map getMap() {
        return this.d_Map;
    }

    /**
     * Set method for setting the map.
     * 
     * @param p_Map The map to be set for the game model.
     */
    public void setMap(Map p_Map) {
        this.d_Map = p_Map;
    }

    /**
     * Get method for retrieving the player ID.
     * 
     * @return The player ID associated with the game model.
     */
    public Player getPlayerId1() {
        return this.d_PlayerID;
    }

    /**
     * Set method for setting the player ID.
     * 
     * @param d_PlayerID The player ID to be set.
     */
    public void setPlayerId(Player d_PlayerID) {
        this.d_PlayerID = d_PlayerID;
    }

    /**
     * Get method for retrieving all players.
     * 
     * @return The list of all players.
     */
    public ArrayList<Player> getAllPlayers() {
        return this.d_PlayerList;
    }

    /**
     * Get method for retrieving the selected map.
     * 
     * @return The selected map associated with the game model.
     */
    public Map getSelectedMap() {
        return this.d_Map;
    }

    /**
     * Method to add a new player to the game.
     * 
     * @param p_PlayerName The name of the player to be added.
     * @throws Exception If the umber of players are greater than country length or
     *                   if the player name already exists.
     */
    public void addPlayer(String p_PlayerName) throws Exception {
        if ((d_PlayerList.size() >= getSelectedMap().getCountryList().size())) {
            throw new Exception("Reached Max Number of Players.");
        }
        if (existDuplicatePlayer(p_PlayerName)) {
            throw new Exception("Player name already exists.\nPlease add another name.");
        } else {
            Player l_PlayerObject = new Player(p_PlayerName, this);
            d_PlayerList.add(l_PlayerObject);
        }
    }

    /**
     * Method to check if a player with the given name already exists.
     * 
     * @param p_PlayerName The name of the player to be checked.
     * @return True if a player with the given name exists, false otherwise.
     */
    public boolean existDuplicatePlayer(String p_PlayerName) {
        for (Player l_Player : d_PlayerList)
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName))
                return true;
        return false;
    }

    /**
     * Method to remove a player from the game.
     * 
     * @param p_PlayerName The name of the player to be removed.
     * @throws Exception If the player is not found.
     */
    public void removePlayer(String p_PlayerName) throws Exception {
        Player l_CurrentPlayer;
        boolean l_PlayerFound = false;
        for (Player l_Player : d_PlayerList) {
            l_CurrentPlayer = l_Player;
            if (l_CurrentPlayer.getPlayerName().equalsIgnoreCase(p_PlayerName)) {
                l_PlayerFound = true;
                d_PlayerList.remove(d_PlayerList.indexOf(l_Player));
            }
        }
        if (!l_PlayerFound) {
            throw new Exception("This Player does not exist");
        }
    }

    /**
     * Method to set the player queue.
     * 
     * @param d_PlayerQueue The player queue to be set.
     */
    public void setplayerQueue(Queue<Player> d_PlayerQueue) {
        this.d_PlayerQueue = d_PlayerQueue;
    }

    /**
     * Method to set the number of armies for all players.
     * 
     * @param p_Count The number of armies to be set.
     */
    public void setPlayerArmies(int p_Count) {
        for (Player l_Player : getAllPlayers()) {
            l_Player.setPlayerArmies(p_Count);
        }
    }

    /**
     * <p>
     * This method initializes the startup phase of the game.
     * </p>
     * <ul>
     * <li>This method enqueues all players.
     * <li>It then iterates through all available countries on the map.
     * <li>A loop is executed until all countries are assigned to players.
     * <li>Countries are randomly assigned to players and removed from the available
     * list.
     * <li>After assigning countries to players, it calls the
     * AssignReinforcementArmies method.
     * </ul>
     * 
     * @throws Exception if there are no players in the list.
     */

    public void startUpPhase() throws Exception {
        if (getAllPlayers().size() > 1) {
            d_PlayerQueue.addAll(getAllPlayers());
            List<Country> l_CountryList = new ArrayList<>();
            l_CountryList = (List<Country>) getSelectedMap().getCountryList().clone();
            while (l_CountryList.size() > 0) {
                Random l_Random = new Random();
                int l_Index = l_Random.nextInt(l_CountryList.size());
                setPlayerId(d_PlayerQueue.remove());
                getPlayerId1().addCountry(l_CountryList.get(l_Index));
                d_PlayerQueue.add(d_PlayerID);
                l_CountryList.remove(l_Index);
            }
            for (Player l_Player : getAllPlayers()) {
                l_Player.setContinentsList();
            }

            assignReinforcementArmies();
        } else {
            if (getAllPlayers().size() == 0) {
                throw new Exception("Please enter players using gameplayer add command");
            } else {
                throw new Exception("One Player Found. Please enter more players using gameplayer add command");
            }
        }
    }

    /**
     * /**
     * This method assigns armies to the players.
     * <ul>
     * <li>It first checks if the size of the player list is greater than 0.
     * <li>If the size is greater than 0, it calculates the number of armies to
     * assign based on the number of countries assigned to the player divided by 3.
     * <li>If a player controls a complete continent, the continent's value is added
     * to the player's army count.
     * <li>The method then uses the maximum function to ensure that players receive
     * a minimum of 3 armies if their calculated army count is less than 3,
     * otherwise, it assigns the maximum possible armies.
     * <li>Once the respective armies have been determined for each player, the
     * method sets the player's army count accordingly.
     * </ul>
     * 
     * @throws Exception If there are no players.
     */

    public void assignReinforcementArmies() throws Exception {
        int l_ContinentValue = 0;
        if (getAllPlayers().size() > 0) {
            for (Player l_Player : getAllPlayers()) {
                int l_ArmyCount = ((l_Player.getCountriesSize()) / 3);
                for (Continent l_Continent : l_Player.getContinentList()) {
                    l_ContinentValue = l_Continent.getContinentControlValue();
                }
                l_ArmyCount = Math.max(l_ArmyCount, 3);
                l_Player.setPlayerArmies(l_ArmyCount + l_ContinentValue);
            }
        } else {
            throw new Exception("Please enter players using gameplayer add command");
        }
    }
}
