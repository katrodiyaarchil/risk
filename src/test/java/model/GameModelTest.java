package model;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * This class is to test the methods of GameModel class
 */
public class GameModelTest {
    GameModel d_Game = new GameModel();
    ArrayList<Player> d_Check;
    List<String> d_Names;
    ArrayList<Player> d_List;
    List<String> d_CheckNames;
    Player d_C1,d_C2;
    private Map d_Map;
    /**
     * To set up the context for test cases
     * @throws Exception relevant for the map creation phase
     */
    @Before
    public void setTestContext() throws Exception {

        d_Check =  new ArrayList<Player>();
        d_Names = new ArrayList<>();
        d_CheckNames = new ArrayList<>();
        d_Map = new Map();
        d_Map.addContinent("asia","1");
        d_Map.addCountry("india","asia");
        d_Map.addCountry("china","asia");
        d_Map.addCountry("japan","asia");
        d_Game = new GameModel(d_Map);
        d_Game.addPlayer("raj");
        d_Game.addPlayer("kumar");
        d_C1 = new Player("raj",d_Game);
        d_C2 = new Player("kumar",d_Game);
        d_Check.add(d_C1);
        d_Check.add(d_C2);
    }

    /**
     * This test case d_Check the functionality of addplayer()
     *
     */
    @Test
    public void testAddPlayer() {
        for(Player l_Player:d_Check) {
            d_Names.add(l_Player.getPlayerName());
        }
        for(Player l_Player:d_Game.getAllPlayers()) {
            d_CheckNames.add(l_Player.getPlayerName());
        }
        assertEquals(d_CheckNames,d_Names);
    }
    /**
     * To test addPlayer() and check if the player exists
     */
    @Test
    public void testAddPlayerAlreadyExist() {
        String l_ExpectedMessage="Player name already exists.\n" +
                "Please add another name.";
        String l_ActualMessage = "";
        try {
            d_Game.addPlayer("raj");
        } catch (Exception p_E) {
            l_ActualMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);
    }

    /**
     * To test addPlayer() and check if the number reaches the maximum number of players
     */
    @Test
    public void testAddPlayerReachedMax() {
        String l_ExpectedMessage="Reached Max Number of Players.";
        String l_ActualMessage = "";
        try {
            d_Game.addPlayer("zeal");
            d_Game.addPlayer("alpha");

        } catch (Exception p_E) {
            l_ActualMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);
    }


    /**
     * This test case d_Check the functionality of RemovePlayer()
     * @throws Exception Player to be removed does not exist
     */
    @Test
    public void testRemovePlayer() throws Exception {
        d_Game.removePlayer("raj");
        for(Player l_Player:d_Check) {
            d_Names.add(l_Player.getPlayerName());
        }
        for(Player l_Player:d_Game.getAllPlayers()) {
            d_CheckNames.add(l_Player.getPlayerName());
        }
        assertFalse(d_CheckNames.equals(d_Names));
    }

    /**
     * To test removePlayer() and check if the player exists
     */
    @Test
    public void testRemovePlayerNotExists() {
        String l_ExpectedMessage="This Player does not exist";
        String l_ActualMessage = "";
        try {
            d_Game.removePlayer("shilpa");
        }catch (Exception p_E) {
            l_ActualMessage = p_E.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);

    }

    /**
     * This test case d_Check the functionality of AssignReinforcements()
     * @throws Exception To check if the number of players is less than number of countries
     */
    @Test
    public void testAssignReinforcements() throws Exception {
        d_Game.assignReinforcementArmies();
        for(Player l_Player:d_Game.getAllPlayers()) {
            int l_Value=l_Player.getPlayerArmies();
            assertTrue(3<=l_Value);

        }
    }
}