package utility.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import controller.GameEngine;
import model.GameModel;
import view.CommandPrompt;

/**
 * This class tests the methods written in Edit phase class.
 */
public class EditTest {
    /**
     * This is the command prompt object
     */
    CommandPrompt d_CpView;
    /**
     * This is the game model new object
     */
    GameModel d_GameModel;
    /**
     * This is the game engine object
     */
    GameEngine d_Ge;
    /**
     * This is the Edit class object
     */
    Edit d_Ed;
    /**
     * This is the Phase class object
     */
    Phase d_P;

    /**
     * This method sets the context before each method is executed.
     *
     * @throws Exception any exception that is thrown while setting up the context.
     */
    @Before
    public void setTestContext() throws Exception {
        d_CpView = new CommandPrompt();
        d_GameModel = new GameModel();
        d_Ge = new GameEngine(d_CpView, d_GameModel);
        d_Ed = new Edit(d_Ge, d_CpView);

    }

    /**
     * This method tests that after the loadmap, the game goes in the startup phase.
     */
    @Test
    public void testLoadMap() {
        String l_ExpectedMessage = "EditPhase";
        String l_ActualMessage = "";
        d_Ed.loadMap("map5");
        d_P = d_Ge.getPhase();
        l_ActualMessage = d_P.getPhaseName();
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This method tests the loading of incorrect/ invalid map.
     */
    @Test
    public void testIncorrectLoadMap() {
        String l_ExpectedMessage = "Map is not Valid";
        String l_ActualMessage = "";
        l_ActualMessage = d_Ed.loadMap("loadmap incorrectmap");

        assertEquals(l_ExpectedMessage, l_ActualMessage.split("\n")[0]);
    }

    /**
     * This method tests that during editmap phase assigncountries method can not be
     * executed.
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
     * This method tests that during Edit phase, gameplayer command can not be
     * executed.
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
