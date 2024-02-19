package warzone;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

import model.Order;
import model.Country;
import model.GameModel;
import model.Map;

/**
 * This is a class to test the methods of Order class
 */
public class OrderTest {
    String d_OrderString = "deploy india 3";
    Order d_Order;
    Map d_Map;
    private GameModel d_GameModel;

    /**
     * To set up the context for test cases
     * @throws Exception relevant for the map creation phase
     */
    @Before
    public void setContextOfOrder() throws Exception {
        d_Map = new Map();
        d_Map.addContinent("asia", "1");
        d_Map.addCountry("india","asia");
        d_GameModel = new GameModel(d_Map);
        d_Order =  new Order(d_OrderString,d_GameModel);
    }

    /**
     * Test the execute() method in Order.java
     */
    @Test
    public void testOrderExecute() {
        d_Order.execute();
        for(Country l_Country : d_Map.getCountryList()) {
            assertEquals(3,l_Country.getNoOfArmies());
        }
    }
}