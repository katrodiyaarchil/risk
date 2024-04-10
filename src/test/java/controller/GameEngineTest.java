package controller;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.GameModel;
import view.CommandPrompt;

/**
 *
 * Test the tournament mode of GameEngine Class
 *
 */
public class GameEngineTest {
    /**
     * Object of CommandPrompt
     */
    CommandPrompt d_CpView;
    /**
     * Object of GameModel
     */
    GameModel d_GameModel;
    /**
     * Object of GameEngine
     */
    GameEngine d_GameEngine;
    /**
     * String variable which holds the Command of tournament phase
     */
    String d_InputString;

    /**
     * Set the context before each test case
     *
     * @throws Exception for the methods implemented
     */
    @Before
    public void setTestContext() throws Exception {
        d_CpView = new CommandPrompt();
        d_GameModel = new GameModel();
        d_GameEngine = new GameEngine(d_CpView, d_GameModel);
    }

    /**
     * Test if the specified map file mentioned in the tournament mode exists or
     * not
     */
    @Test
    public void testTournamentFileNotExists() {
        String l_ActualMessage = "";
        String l_ExpectedMessage = "File does not Exists";

        d_InputString = "tournament -M map9,map99 -P benevolent,aggressive -G 2 -D 10";
        try {
            d_GameEngine.tournament(d_InputString);
        } catch (Exception e) {
            l_ActualMessage = e.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * Test if the number of maps entered are in range or not
     */
    @Test
    public void testTournamentMapRange() {
        String l_ActualMessage = "";
        String l_ExpectedMessage = "Number of Maps should be in between 1 to 5 both inclusive";

        d_InputString = "tournament -M map9,map99,map1,map2,map5,map8 -P benevolent,aggressive -G 2 -D 10";
        try {
            d_GameEngine.tournament(d_InputString);
        } catch (Exception e) {
            l_ActualMessage = e.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * Test if the number of Players mentioned are in range or not
     */
    @Test
    public void testTournamentPlayerRange() {
        String l_ActualMessage = "";
        String l_ExpectedMessage = "Number of Player strategies should be in between 2 to 4 both inclusive";

        d_InputString = "tournament -M map99,map5 -P benevolent -G 2 -D 10";
        try {
            d_GameEngine.tournament(d_InputString);
        } catch (Exception e) {
            l_ActualMessage = e.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * Test if the number of games mentioned are in range or not
     */
    @Test
    public void testTournamentGamesRange() {
        String l_ActualMessage = "";
        String l_ExpectedMessage = "Number of Games should be in between 1 to 5 both inclusive";

        d_InputString = "tournament -M map99,map5 -P benevolent,aggressive -G 7 -D 10";
        try {
            d_GameEngine.tournament(d_InputString);
        } catch (Exception e) {
            l_ActualMessage = e.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * Test if the number of turns mentioned are in range or not
     */
    public void testTournamentTurnsRange() {
        String l_ActualMessage = "";
        String l_ExpectedMessage = "Number of Games are not in range";

        d_InputString = "tournament -M map99,map5 -P benevolent,aggressive -G 5 -D 9";
        try {
            d_GameEngine.tournament(d_InputString);
        } catch (Exception e) {
            l_ActualMessage = e.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * Test if the tournament mode gives the result or not
     *
     * @throws Exception for file not found, add player, and ranges for different
     *                   input parameter
     */
    public void testTournamentMode() throws Exception {
        d_InputString = "tournament -M map99,map5 -P benevolent,aggressive -G 5 -D 9";
        d_GameEngine.tournament(d_InputString);
        Assert.assertNotNull(d_GameEngine.getTournamentResult());
    }

}
