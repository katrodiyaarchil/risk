package model;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * To test the methods of Map.java
 */
public class MapTest {
    Continent d_C0,d_C1;
    Country d_Country1,d_Country2,d_Country3,d_Country4,d_Country5;
    ArrayList<Country> d_Check;
    ArrayList<Continent> d_CheckContinent;
    Map d_Map;

    /**
     * To set the context before each test case
     * @throws Exception relevant for the map creation phase
     */
    @Before
    public void setTestContext() throws Exception {
        d_C0 = new Continent("asia",0);
        d_C1 =  new Continent("africa",0);
        d_Country1 = new Country("india","asia");
        d_Country2 = new Country("china","asia");
        d_Country3 = new Country("japan","asia");
        d_Country4 = new Country("kenya","africa");
        d_Country5 = new Country("egypt","africa");
        d_Check =  new ArrayList<Country>();
        d_CheckContinent = new ArrayList<Continent>();
        d_Map = new Map();
        d_CheckContinent.add(d_C0);
        d_CheckContinent.add(d_C1);
        d_Check.add(d_Country1);
        d_Check.add(d_Country2);
        d_Check.add(d_Country3);
        d_Check.add(d_Country4);
        d_Check.add(d_Country5);
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
    }

    /**
     * This test checks the functionality of addCountry()
     *
     */
    @Test
    public void testAddCountry() {
        assertTrue(d_Map.getCountryList().contains(d_Country5));
    }

    /**
     * This test checks the functionality of addCountry() to see if it adds a country to the continent that does not exists
     */
    @Test
    public void testAddCountryContinentNotExists() {
        String l_ExpectedMessage = "Continent Doesn't Exist to add a Country";
        String l_ActualMessage = "";
        try {
            d_Map.addCountry("brazil", "SA");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This test checks the functionality of addCountry() to see if it catches exception thrown for country already exists
     */
    @Test
    public void testAddCountryCountryExists() {
        String l_ExpectedMessage = "Country Already Exist";
        String l_ActualMessage = "";
        try {
            d_Map.addCountry("egypt", "africa");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This test checks the functionality of removeCountry()
     * @throws Exception If country does not exists
     */
    @Test
    public void testRemoveCountry() throws Exception {
        d_Map.removeCountry("egypt", true);
        assertFalse(d_Map.getCountryList().contains(d_Country5));
    }

    /**
     * This test checks the functionality of removeCountry() to see if the exception is thrown for country does not exists
     */
    @Test
    public void testRemoveCountryThatDoesNotExists() {
        String l_ExpectedMessage = "Country does not exist !!";
        String l_ActualMessage = "";
        try {
            d_Map.removeCountry("congo", true);
        }  catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * This test checks the functionality of addBorder()
     */
    @Test
    public void testAddBorder() {
        try {
            d_Map.addBorder("india", "china");
            d_Map.addBorder("india", "kenya");
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }

        for(Country l_Country: d_Map.getCountryList()) {
            if(l_Country.getCountryName().equals("india")) {
                assertTrue(l_Country.getBorder().contains("china"));
            }
        }
    }

    /**
     * To test addBorder() to check if exception is thrown and caught on adding neighbouring country that does not exists
     */
    @Test
    public void testAddBorderNeighborDoesNotExist() {
        String l_ExpectedMessage = "Neighbour Country does not exists!";
        String l_ActualMessage = "";
        try {
            d_Map.addBorder("india", "congo");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }

        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * To test addBorder() to check if exception is thrown and caught on adding country that does not exists
     */
    @Test
    public void testAddBorderCountryDoesNotExist() {
        String l_ExpectedMessage = "Country does not exists!";
        String l_ActualMessage = "";
        try {
            d_Map.addBorder("congo", "india");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * To test addBorder() to check if exception is thrown and caught on adding border that already exists
     */
    @Test
    public void testAddBorderNeighborExist() {
        String l_ExpectedMessage = "Neighbor Already Exist";
        String l_ActualMessage = "";
        try {
            d_Map.addBorder("japan", "china");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * to test the functionality of removeBorder()
     * @throws Exception If border does not exists, or country does not exists
     */
    @Test
    public void testRemoveBorder() throws Exception {
        d_Map.removeBorder("china", "india");

        for(Country l_Country: d_Map.getCountryList()) {
            if(l_Country.getCountryName().equals("china")) {
                assertFalse(l_Country.getBorder().contains("india"));
            }
        }
    }

    /**
     * To test removeBorder() to check if exception is thrown and caught on adding country that does not exists
     */
    @Test
    public void testRemoveBorderCountryDoesNotExist() {
        String l_ExpectedMessage = "Country does not exists!";
        String l_ActualMessage = "";
        try {
            d_Map.removeBorder("congo", "india");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }

        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * To test removeBorder() to check if exception is thrown and caught on adding neighboring country that does not exists
     */
    @Test
    public void testRemoveBorderNeighborDoesNotExist() {
        String l_ExpectedMessage = "Neighbour Country does not exists!";
        String l_ActualMessage = "";
        try {
            d_Map.removeBorder("india", "congo");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_ActualMessage);
    }

    /**
     * To d_Check the functionality of addContinent
     *
     */
    @Test
    public void testAddContinent() {
        assertTrue(d_Map.getContinentList().contains(d_C0));
    }

    /**
     * To test addContinent() to d_Check  the continent control value
     */
    @Test
    public void testAddContinentContinentControlValue() {
        String l_ExpectedMessage = "Continent control must be a positive integer";
        String l_ActualMessage = "";
        try {
            d_Map.addContinent("europe", "0");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);
    }

    /**
     * To test addContinent() and d_Check if the Continent already Exists or not
     */
    @Test
    public void testAddContinentContinentExists() {
        String l_ExpectedMessage = "Continent Already Exists";
        String l_ActualMessage = "";
        try {
            d_Map.addContinent("asia", "1");
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);
    }

    /**
     * To test removeContinent() for its functionality
     */
    @Test
    public void testRemoveContinent(){
        String l_ExpectedMessage = "Continent does not exist !!";
        String l_ActualMessage = "";
        try {
            d_Map.removeContinent(d_C0.getContinentName());
            d_Map.removeContinent(d_C0.getContinentName());
        } catch (Exception p_Exception) {
            l_ActualMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_ActualMessage);
    }

    /**
     * To test Continent and check if country already exits or not
     */
    @Test
    public void testContinentForCountryExists() {
        for(Country l_Country : d_Map.getCountryList()) {
            int l_Flag=0;
            for(Continent l_Continent : d_Map.getContinentList()) {
                if(l_Continent.getContinentName().equals(l_Country.getContinentName())) {
                    l_Flag=1;break;
                }
            }
            assertEquals(1,l_Flag);
        }
    }

    /**
     * To test functionality removeCountry() from the continent
     */
    @Test
    public void testRemoveCountryFromContinent() {
        d_Map.removeCountryFromContinent(d_Country1.getCountryName(), d_C0.getCountryList());
        assertFalse(d_C0.getCountryList().contains(d_Country1));
    }

    /**
     * To test removeAllCountryFromContinent() method of Map.java
     */
    @Test
    public void testRemoveAllCountryFromContinent() {
        try {
            d_Map.removeAllCountryInContinent(d_C1);
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }
        int l_Flag=0;
        for(Country l_Country : d_C1.getCountryList()) {
            if(d_Map.getCountryList().contains(l_Country)) {
                l_Flag =1;break;
            }
        }
        assertEquals(0,l_Flag);
    }

    /**
     * To test the Validation of Map and Check whether it is a connected graph or not
     * @throws Exception for Add borders and if continent is not a connected subgraph
     */
    @Test
    public void testValidateMap() throws Exception {
        String l_Actual="", l_Expected="Map is Valid";
        d_Map.addBorder("india", "kenya");
        d_Map.addBorder("kenya", "egypt");
        d_Map.addBorder("india", "japan");
        l_Actual = d_Map.validateMap();
        assertEquals(l_Expected,l_Actual);
    }


    /**
     * To test the Validation of Map and Check whether it is not a connected graph (Here continent is a connnected subgraph but the two continents are not)
     * @throws Exception for Add borders and if continent is not a connected subgraph
     */
    @Test
    public void testValidateMapFalse() throws Exception {
        String l_Actual="", l_Expected="Map is not Valid";
        d_Map.addBorder("kenya", "egypt");
        d_Map.addBorder("india", "japan");
        l_Actual = d_Map.validateMap();
        assertEquals(l_Expected,l_Actual);
    }


    /**
     * To test the Validation of Map and Check whether continent is a connected subgraph or not
     * @throws Exception for Add borders and if continent is not a connected subgraph
     */
    @Test
    public void testValidateMapForContinents() throws Exception {
        String l_Result="", l_ResultExpected = "";
        String  l_Actual="", l_Expected="The countries inside asia are not internally Connected";
        d_Map.addBorder("india", "kenya");
        d_Map.addBorder("kenya", "egypt");
        try {
            l_Result = d_Map.validateMap();
        } catch (Exception p_Exception) {
            l_Actual = p_Exception.getMessage();
        }
        assertEquals(l_Expected,l_Actual);
        assertEquals(l_ResultExpected,l_Result);
    }
}