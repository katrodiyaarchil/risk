package model.orders;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import controller.GameEngine;
import model.Continent;
import model.Country;
import model.GameModel;
import model.Map;
import model.Player;
import view.CommandPrompt;

/**
 * This class tests the methods written in Advance order class.
 */
public class AdvanceTest {
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
    GameEngine d_Ge;
    /**
     * Objects of Continent
     */
    Continent d_C0, d_C1;
    /**
     * Objects of country
     */
    Country d_Country1, d_Country2, d_Country3, d_Country4, d_Country5;
    /**
     * Objects of Player
     */
    Player d_P1, d_P2;
    /**
     * Objects of Map
     */
    Map d_Map;
    /**
     * Objects of advance
     */
    Advance d_Adv;

    /**
     * This method sets the context before each method is executed.
     *
     * @throws Exception any exception that is thrown while setting up the context.
     */
    @Before
    public void setTestContext() throws Exception {
        d_CpView = new CommandPrompt();

        d_C0 = new Continent("asia", 0);
        d_C1 = new Continent("africa", 0);

        d_Country1 = new Country("india", "asia");
        d_Country2 = new Country("china", "asia");
        d_Country3 = new Country("japan", "asia");
        d_Country4 = new Country("kenya", "africa");
        d_Country5 = new Country("egypt", "africa");

        d_Map = new Map();
        d_Map.addContinent(d_C0.getContinentName(), "1");
        d_Map.addContinent(d_C1.getContinentName(), "1");

        d_Map.addCountry("india", "asia");
        d_Map.addCountry("china", "asia");
        d_Map.addCountry("japan", "asia");
        d_Map.addCountry("kenya", "africa");
        d_Map.addCountry("egypt", "africa");

        d_Map.addBorder("egypt", "kenya");
        d_Map.addBorder("kenya", "japan");
        d_Map.addBorder("japan", "china");
        d_Map.addBorder("china", "india");
        d_Map.addBorder("india", "kenya");
        d_Map.addBorder("kenya", "egypt");
        d_Map.addBorder("india", "japan");
        d_Map.addBorder("kenya", "india");
        d_Map.addBorder("japan", "india");
        d_GameModel = new GameModel(d_Map);
        d_Ge = new GameEngine(d_CpView, d_GameModel);
        d_GameModel.addPlayer("raj", "human");
        d_GameModel.addPlayer("kumar", "human");
        d_P1 = new Player("raj", d_GameModel);
        d_P2 = new Player("kumar", d_GameModel);

        d_P1.addCountry(d_Country1);
        d_P1.addCountry(d_Country4);
        d_P1.addCountry(d_Country3);

        d_P2.addCountry(d_Country2);
        d_P2.addCountry(d_Country5);

        d_Country1.setCountryOwnerPlayer(d_P1);
        d_Country2.setCountryOwnerPlayer(d_P2);
        d_Country3.setCountryOwnerPlayer(d_P1);

        d_Country4.setCountryOwnerPlayer(d_P1);
        d_Country5.setCountryOwnerPlayer(d_P2);

        d_P1.setPlayerArmies(3);
        d_P2.setPlayerArmies(3);
        d_Country1.setNoOfArmies(3);
        d_Country5.setNoOfArmies(3);

    }

    /**
     * This method tests the validatemap method.
     *
     * @throws Exception any exception thrown during validateMap method check.
     */
    @Test
    public void testValidateMap() throws Exception {
        String l_Actual = "", l_Expected = "Map is Valid";
        l_Actual = d_Map.validateMap();
        assertEquals(l_Expected, l_Actual);
    }

    /**
     * This method tests if the source and target countries during the advance order
     * are non adjacent.
     */
    @Test
    public void testNonAdjacentTerritory() {
        String l_Actual = "", l_Expected = "\nThe source country and target country are not neighbours!";
        d_Adv = new Advance(d_P1, d_Country1, d_Country5, 2);
        d_Adv.execute();
        l_Actual = d_Adv.d_Player.getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if the source and target countries are same during the
     * advance order.
     */
    @Test
    public void testSourceTargetTerritory() {
        String l_Actual = "", l_Expected = "\nThe source country and target country cannot be same!";
        d_Adv = new Advance(d_P1, d_Country1, d_Country1, 2);
        d_Adv.execute();
        l_Actual = d_Adv.d_Player.getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method test if source and target countries are not neighbors during
     * advance order.
     */
    @Test
    public void testSourceTargetNeighbours() {
        String l_Actual = "", l_Expected = "\nThe source country and target country are not neighbours!";
        d_Adv = new Advance(d_P1, d_Country1, d_Country3, 2);
        d_Adv.execute();
        l_Actual = d_Adv.d_Player.getResult();
        assertEquals(l_Expected, l_Actual);

    }

}
