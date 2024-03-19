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
 * This class tests the methods in Deploy order class.
 */
public class DeployTest {

    CommandPrompt d_CpView;
    GameModel d_GameModel;
    GameEngine d_Ge;
    Continent d_C0,d_C1;
    Country d_Country1,d_Country2,d_Country3,d_Country4,d_Country5;

    Player d_P1,d_P2;
    Map d_Map;
    Deploy d_Deploy;

    /**
     * This method sets the context before each method is executed.
     * @throws Exception any exception that is thrown while setting up the context.
     */
    @Before
    public void setTestContext() throws Exception {
        d_CpView= new CommandPrompt();

        d_C0 = new Continent("asia",0);
        d_C1 =  new Continent("africa",0);

        d_Country1 = new Country("india","asia");
        d_Country2 = new Country("china","asia");
        d_Country3 = new Country("japan","asia");
        d_Country4 = new Country("kenya","africa");
        d_Country5 = new Country("egypt","africa");

        d_Map = new Map();
        d_Map.addContinent(d_C0.getContinentName(), "1");
        d_Map.addContinent(d_C1.getContinentName(), "1");

        d_Map.addCountry("india","asia");
        d_Map.addCountry("china","asia");
        d_Map.addCountry("japan","asia");
        d_Map.addCountry("kenya","africa");
        d_Map.addCountry("egypt","africa");

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
        d_Ge= new GameEngine(d_CpView,d_GameModel);
        d_GameModel.addPlayer("raj");
        d_GameModel.addPlayer("kumar");
        d_P1 = new Player("raj",d_GameModel);
        d_P2 = new Player("kumar",d_GameModel);

        d_P1.addCountry(d_Country1);
        d_P2.addCountry(d_Country4);
        d_P1.addCountry(d_Country3);

        d_P2.addCountry(d_Country2);
        d_P2.addCountry(d_Country5);

        d_Country1.setCountryOwnerPlayer(d_P1);
        d_Country2.setCountryOwnerPlayer(d_P2);
        d_Country3.setCountryOwnerPlayer(d_P1);

        d_Country4.setCountryOwnerPlayer(d_P2);
        d_Country5.setCountryOwnerPlayer(d_P2);


        d_P1.setPlayerArmies(3);
        d_P2.setPlayerArmies(3);
        d_Country1.setNoOfArmies(3);
        d_Country5.setNoOfArmies(3);

    }

    /**
     * This method tests if the player is deploying armies lesser than owned ones.
     */
    @Test
    public void testArmies() {
        String l_Actual="";
        String l_Expected="\nraj ; you have only 3 number of armies! Please enter the next order accordingly";
        d_Deploy = new Deploy(d_P1,  d_Country1, 4);
        d_Deploy.execute();
        l_Actual = d_Deploy.getPlayer().getResult();
        assertEquals(l_Expected,l_Actual);
    }

    /**
     * This method tests if the country is not owned by the player
     * when the player trying to deploy its armies.
     * .
     */
    @Test
    public void testCountry() {
        String l_Actual="";
        String l_Expected="\n" +
                "This country india does not belong to kumar";
        d_Deploy = new Deploy(d_P2,  d_Country1, 3);
        d_Deploy.execute();
        l_Actual = d_Deploy.getPlayer().getResult();
        assertEquals(l_Expected,l_Actual);
    }

    /**
     * This method tests that number of armies are deployed on the player successfully.
     */
    @Test
    public void testDeploy() {
        String l_Actual="";
        String l_Expected="\norder deploy india 2 added to list of raj";
        d_Deploy = new Deploy(d_P1,  d_Country1, 2);
        d_Deploy.execute();
        l_Actual = d_Deploy.getPlayer().getResult();
        assertEquals(l_Expected,l_Actual);
    }
}
