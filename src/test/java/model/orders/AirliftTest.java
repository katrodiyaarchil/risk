package model.orders;

import static org.junit.Assert.*;

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
 * This class tests the methods written in Airlift order class.
 */
public class AirliftTest {
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
     * Objects of Players
     */
    Player d_P1, d_P2;
    /**
     * Objects of Map
     */
    Map d_Map;
    /**
     * object of Airlift
     */
    Airlift d_Air;

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
     * This method tests if the player owns an airlift card
     */
    @Test
    public void testCardCheck() {
        String l_Actual = "", l_Expected = "Player does not have a Airlift card";
        d_Air = new Airlift(d_P1, d_Country1, d_Country5, 2);
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if the source and target countries are not same
     */
    @Test
    public void testSourceTargetTerritory() {
        d_P1.setCard("Airlift");
        String l_Actual = "", l_Expected = "The source country and target country cannot be same!";
        d_Air = new Airlift(d_P1, d_Country1, d_Country1, 2);
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if after airlift source country is left with atleast one
     * army.
     */
    @Test
    public void testPlayerArmies() {
        d_P1.setCard("Airlift");
        String l_Actual = "", l_Expected = "The source country should be left with atleast one army!";
        d_Air = new Airlift(d_P1, d_Country1, d_Country3, 3);
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if the source and target countries belong to the same
     * player.
     */
    @Test
    public void testSourceTargetsame() {
        d_P1.setCard("Airlift");
        String l_Actual = "", l_Expected = "The source country and target country belong to the same player";
        d_Air = new Airlift(d_P1, d_Country1, d_Country3, 2);
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if the airlift is happening between the countries of the
     * same owner.
     */
    @Test
    public void testOtherPlayerCountry() {
        d_P1.setCard("Airlift");
        String l_Actual = "";
        String l_Expected = "You can only airlift armies to your own countries. egypt does not belongs to raj";
        d_Air = new Airlift(d_P1, d_Country1, d_Country5, 2);
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

    /**
     * This method tests if the player doesn't have airlift card after he has
     * already used it.
     */
    @Test
    public void testAirliftAgain() {
        d_P1.setCard("Airlift");
        String l_Actual = "", l_Expected = "Player does not have a Airlift card";
        d_Air = new Airlift(d_P1, d_Country1, d_Country3, 2);
        d_Air.execute();
        d_Air.execute();
        l_Actual = d_Air.getPlayer().getResult();
        assertEquals(l_Expected, l_Actual);

    }

}
