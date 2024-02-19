package warzone;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.GameModelNew;
import model.Player;
import model.Map;

/**
 * This class aims to test the methods in GameModel
 */
public class GameModelTest {
    GameModelNew d_Game = new GameModelNew();
    ArrayList<Player> d_Check;
    List<String> d_Names;
    ArrayList<Player> d_List;
    List<String> d_CheckNames;
    public Player d_C1, d_C2;
    private Map d_Map;

    /**
     * To set up the context for test cases
     *
     * @throws Exception relevant for the map creation phase
     */
    @Before
    public void setTestContext() throws Exception {

        d_Check = new ArrayList<Player>();
        d_Names = new ArrayList<>();
        d_CheckNames = new ArrayList<>();
        d_Map = new Map();
        d_Map.addContinent("asia", "1");
        d_Map.addCountry("india", "asia");
        d_Map.addCountry("china", "asia");
        d_Map.addCountry("japan", "asia");
        d_Game = new GameModelNew(d_Map);
        d_Game.addPlayer("Lee");
        d_Game.addPlayer("Sam");
        d_C1 = new Player("Lee", d_Game);
        d_C2 = new Player("Sam", d_Game);
        d_Check.add(d_C1);
        d_Check.add(d_C2);
    }

    /**
     * This case d_Check the functionality of addplayer()
     */
    @Test
    public void testAddPlayer() {
        for (Player l_Player : d_Check) {
            d_Names.add(l_Player.getPlayerName());
        }
        for (Player l_Player : d_Game.getAllPlayers()) {
            d_CheckNames.add(l_Player.getPlayerName());
        }
        assertEquals(d_CheckNames, d_Names);
    }

    /**
     * Test addPlayer method then check the player is overlapped
     */
    @Test
    public void testAddPlayerAlreadyExist() {
        String l_ExpectedMessage = "Please enter a differnt Player name as this name already exists";
        String l_OutputMessage = "";
        try {
            d_Game.addPlayer("Lee");
        } catch (Exception p_E) {
            l_OutputMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * Test addPlayer() to see if the number has beyond maximum players
     */
    @Test
    public void testAddPlayerReachedMax() {
        String l_ExpectedMessage = "Reached Max Number of Players can be added to the game";
        String l_OutputMessage = "";
        try {
            d_Game.addPlayer("Eliot");
            d_Game.addPlayer("Nick");

        } catch (Exception p_E) {
            l_OutputMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * This case d_Check the functionality of RemovePlayer()
     *
     * @throws Exception Player to be removed does not exist
     */
    @Test
    public void testRemovePlayer() throws Exception {
        d_Game.removePlayer("Lee");
        for (Player l_Player : d_Check) {
            d_Names.add(l_Player.getPlayerName());
        }
        for (Player l_Player : d_Game.getAllPlayers()) {
            d_CheckNames.add(l_Player.getPlayerName());
        }
        assertFalse(d_CheckNames.equals(d_Names));
    }

    /**
     * To test removePlayer() and check if Player exists or not
     */
    @Test
    public void testRemovePlayerNotExists() {
        String l_ExpectedMessage = "This Player does not exists";
        String l_OutputMessage = "";
        try {
            d_Game.removePlayer("jane");
        } catch (Exception p_E) {
            l_OutputMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);

    }

    /**
     * This case d_Check the functionality of AssignReinforcements()
     *
     * @throws Exception To check if the number of players is less than number of
     *                   countries or not
     */
    @Test
    public void testAssignReinforcements() throws Exception {
        d_Game.startUpPhase();
        for (Player l_Player : d_Game.getAllPlayers()) {
            int l_Value = l_Player.getPlayerArmies();
            assertTrue(3 <= l_Value);

        }
    }

    /**
     * This case d_Check the functionality of whether player has deployed
     * all of the armies that assigned to player
     *
     */
    @Test
    public void testIssueOrder() {
        String l_Command = "deploy india 3";
        String l_Expected = "\nLee : Your armies have become zero now!!. You will not be able to issue an order";
        d_C1.setOrder(l_Command);
        d_C1.setPlayerArmies(3);
        d_C1.addCountry(d_Map.getCountryList().get(0));
        d_C1.issue_order();
        String l_Result = d_C1.getResult();
        assertEquals(l_Expected, l_Result);
    }

    /**
     * This case is to check whether player owns the country that he is deploying
     *
     */
    @Test
    public void testIssueOrder1() {

        String l_Command1 = "deploy kenya 3";
        String l_Expected1 = "\nThis country kenya doesnot belongs to Lee";
        d_C1.setPlayerArmies(3);
        d_C1.addCountry(d_Map.getCountryList().get(0));
        d_C1.setOrder(l_Command1);
        d_C1.issue_order();
        String l_Result1 = d_C1.getResult();
        assertEquals(l_Expected1, l_Result1);
    }

    /**
     * This case is to check whether no armies deployed is less than the player's
     *
     */
    @Test
    public void testIssueOrder2() {

        String l_Command2 = "deploy india 2";
        String l_Expected2 = "\norder deploy india 2 added to list of Lee";
        d_C1.setPlayerArmies(3);
        d_C1.addCountry(d_Map.getCountryList().get(0));
        d_C1.setOrder(l_Command2);
        d_C1.issue_order();
        String l_Result2 = d_C1.getResult();
        assertEquals(l_Expected2, l_Result2);
    }

    /**
     * This case is to check whether no armies deployed is more than the player's
     *
     */
    @Test
    public void testIssueOrderArmySize() {

        String l_Command3 = "deploy india 4";
        String l_Expected3 = "\nLee ; you have only 3 number of armies! Please enter the next order accordingly";
        d_C1.setPlayerArmies(3);
        d_C1.addCountry(d_Map.getCountryList().get(0));
        d_C1.setOrder(l_Command3);
        d_C1.issue_order();
        String l_Result3 = d_C1.getResult();
        assertEquals(l_Expected3, l_Result3);
    }
}