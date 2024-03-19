package utility.state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import controller.GameEngine;
import model.GameModel;
import view.CommandPrompt;

/**
 * This class tests the methods in ExecuteOrder phase class.
 */
public class ExecuteOrderTest {

    CommandPrompt d_CpView;
    GameModel d_GameModel;
    GameEngine d_Ge;
    ExecuteOrder d_Eo;
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
        d_Eo = new ExecuteOrder(d_Ge, d_CpView);
    }

    /**
     * This method tests that editmap() is invalid in the Execute Order phase.
     */
    @Test
    public void testEditMap() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.editMap("map5");
            d_P = d_Ge.getPhase();
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }

    }

    /**
     * This method tests that editCountry() is invalid in the execute phase.
     */
    @Test
    public void testEditCountry() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.editCountry("india", "asia");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that editContinent() is invalid in the execute phase.
     */
    @Test
    public void testEditContinent() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.editContinent("asia", "1");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that editNeighbor() is invalid in the execute phase.
     */
    @Test
    public void testEditNeighbor() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.editNeighbor("india", "china");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that savemap() is invalid in the execute phase.
     */
    @Test
    public void testSaveMap() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.saveMap("map");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that loadmap is invalid in the execute phase.
     */
    @Test
    public void testLoadMap() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.loadMap("map");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that addPlayer() is invalid in the execute order phase.
     */
    @Test
    public void testAddPlayers() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.addPlayers("zeal", "raj");
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that assigncountris() is invalid in the execute order phase.
     */
    @Test
    public void testAssignCountries() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.assignCountries();
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }
    }

    /**
     * This method tests that validatemap() is invalid in the execute phase.
     */
    @Test
    public void testValidateMap() {
        try {
            String l_ExpectedMessage = "Invalid command in state ";
            String l_ActualMessage = "";
            d_Eo.validateMap();
            l_ActualMessage = d_Eo.d_Leb.getResult();
            assertEquals(l_ExpectedMessage, l_ActualMessage);
        } catch (Exception p_Exp) {
        }

    }

}
