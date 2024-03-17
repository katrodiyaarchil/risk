package utility.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import controller.GameEngine;
import model.GameModelNew;
import view.CommandPrompt;

/**
 * This class tests the methods in Edit phase class.
 */
public class EditTest {
    CommandPrompt d_CpView;
    GameModelNew d_GameModel;
    GameEngine d_Ge;
    Edit d_Ed;
    Phase d_P;

    /**
     * This method sets the context before each method is executed.
     *
     * @throws Exception any exception that is thrown while setting up the context.
     */
    @Before
    public void setTestContext() throws Exception {
        d_CpView = new CommandPrompt();
        d_GameModel = new GameModelNew();
        d_Ge = new GameEngine(d_CpView, d_GameModel);
        d_Ed = new Edit(d_Ge, d_CpView);

    }

    /**
     * This method tests that after loadmap, the game move on to the startup phase.
     */
    @Test
    public void testLoadMap() {
        String l_ExpectedMessage = "Startup";
        String l_ActualMessage = "";
        d_Ed.loadMap("map5");
        d_P = d_Ge.getPhase();
        l_ActualMessage = d_P.getPhaseName();
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This method tests if the map about to load is incorrect/ invalid.
     */
    @Test
    public void testIncorrectLoadMap() {
        String l_ExpectedMessage = "resource/incorrectmap (No such file or directory)";
        String l_ActualMessage = "";
        l_ActualMessage = d_Ed.loadMap("loadmap incorrectmap");

        assertEquals(l_ExpectedMessage, l_ActualMessage.split("\n")[0]);
    }

    /**
     * This method tests that in editmap phase assigncountries() can not be executed.
     */
    @Test
    public void testAssignCountries() {
        String l_ExpectedMessage = "Invalid command in state ";
        String l_ActualMessage = "";
        d_Ed.assignCountries();
        d_P = d_Ge.getPhase();
        l_ActualMessage = d_Ed.d_Leb.getResult();
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This method tests that in Edit phase, gameplayer command can not be executed.
     */
    @Test
    public void testAddPlayers() {
        String l_ExpectedMessage = "Invalid command in state ";
        String l_ActualMessage = "";
        d_Ed.addPlayers("zeal", "raj");
        l_ActualMessage = d_Ed.d_Leb.getResult();
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }
}
