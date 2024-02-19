package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameModel {
    private Map d_Map;
    private Player d_PlayerID;
    private ArrayList<Player> d_PlayerList;
    private Queue<Player> d_PlayerQueue = new LinkedList<Player>();

    public GameModel() {
        this.d_Map = new Map();
        this.d_PlayerList = new ArrayList<Player>();
    }

    public GameModel(Map p_Map) {
        this.d_Map = p_Map;
        this.d_PlayerList = new ArrayList<Player>();
    }

    public Map getMap() {
        return this.d_Map;
    }

    public void setMap(Map p_Map) {
        this.d_Map = p_Map;
    }

    public Player getPlayerId() {
        return this.d_PlayerID;
    }

    public void setPlayerId(Player d_PlayerID) {
        this.d_PlayerID = d_PlayerID;
    }

    public ArrayList<Player> getAllPlayers() {
        return this.d_PlayerList;
    }

    public Map getSelectedMap() {
        return this.d_Map;
    }

    public void addPlayer(String p_PlayerName) throws Exception {
        if ((d_PlayerList.size() >= getSelectedMap().getCountryList().size())) {
            throw new Exception("Reached Max Number of Players can be added to the game");
        }
        if (existDuplicatePlayer(p_PlayerName)) {
            throw new Exception("Please enter a differnt Player name as this name already exists");
        } else {
            Player l_PlayerObject = new Player(p_PlayerName, this);
            d_PlayerList.add(l_PlayerObject);
        }
    }

    public boolean existDuplicatePlayer(String p_PlayerName) {
        for (Player l_Player : d_PlayerList)
            if (l_Player.getPlayerName().equalsIgnoreCase(p_PlayerName))
                return true;
        return false;
    }

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
        if (l_PlayerFound == false) {
            throw new Exception("This Player does not exists");
        }
    }

    public void setplayerQueue(Queue<Player> d_PlayerQueue) {
        this.d_PlayerQueue = d_PlayerQueue;

    }

    public void setPlayerArmies(int p_Count) {
        for (Player l_Player : getAllPlayers()) {
            l_Player.setPlayerArmies(p_Count);
        }
    }

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
            throw new Exception("\"Please enter players using gameplayer add command");
        }
    }
}
