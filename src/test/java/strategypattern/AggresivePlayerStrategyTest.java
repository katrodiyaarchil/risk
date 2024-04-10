package strategypattern;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import model.Continent;
import model.Country;
import model.GameModel;
import model.Map;
import model.Player;

/**
 *
 * This is the Aggresive Player Strategy Class
 *
 */
public class AggresivePlayerStrategyTest {
    /**
     * Player objects
     */
    Player d_Player1, d_Player2;
    /**
     * Map object
     */
    Map d_Map;
    /**
     * GameModel Object
     */
    GameModel d_GameModel;
    /**
     * Strategy Objects
     */
    Strategy d_Strategy1, d_Strategy2;
    /**
     * Continent object
     */
    Continent d_Continent;
    /**
     * Country objects
     */
    Country d_Country1, d_Country2, d_Country3;

    @Before
    public void setTestContext() throws Exception {
        d_Map = new Map();
        d_GameModel = new GameModel(d_Map);
        d_Player1 = new Player("raj", d_GameModel);
        d_Player2 = new Player("zeal", d_GameModel);
        d_Continent = new Continent("asia", 3);
        d_Map.addContinent(d_Continent.getContinentName(), "3");
        d_Map.addCountry("india", "asia");
        d_Map.addCountry("china", "asia");
        d_Map.addCountry("japan", "asia");
        d_Map.addBorder("india", "china");
        d_Map.addBorder("china", "india");
        d_Map.addBorder("japan", "china");
        d_Map.addBorder("china", "japan");
        d_Map.addBorder("india", "japan");
        d_Country1 = new Country("india", "asia");
        d_Country2 = new Country("china", "asia");
        d_Country3 = new Country("japan", "asia");
        d_Player1.addCountry(d_Country1);
        d_Player1.addCountry(d_Country2);
        d_Player2.addCountry(d_Country3);
        d_Player1.setPlayerArmies(3);
        d_Player2.setPlayerArmies(3);
        d_Country1.setNoOfArmies(2);
        d_Country2.setNoOfArmies(3);
        d_Country1.setBorder("china");
        d_Country1.setBorder("japan");
        d_Country2.setBorder("japan");
        d_Country3.setBorder("china");
        d_Country3.setBorder("india");

    }

    /**
     * This Method is to check whether the Player is defending on the Players
     * Strongest Country
     */
    @Test
    public void testDefendCheck() {
        String l_Actual = "";
        String l_Expected = "The aggressive player is defending from china";
        AggresivePlayerStrategy l_Agress = new AggresivePlayerStrategy(d_Player1, d_GameModel);
        l_Agress.toDefend();
        l_Actual = d_Player1.getResult();
        assertEquals(l_Expected, l_Actual);
    }

    /**
     * This Method is to check whether the Player is attacking on the Neighbor
     * Country
     */
    @Test
    public void testAttackFromCheck() {
        String l_Actual = "";
        String l_Expected = "The aggressive player is attacking on japan";
        AggresivePlayerStrategy l_Agress = new AggresivePlayerStrategy(d_Player1, d_GameModel);
        l_Agress.toAttack();
        l_Actual = d_Player1.getResult();
        assertEquals(l_Expected, l_Actual);
    }
}
