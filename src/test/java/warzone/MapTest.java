package warzone;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Continent;
import model.Country;
import model.Map;

/**
 * To test the methods of Map.java
 */
public class MapTest {
    Continent d_C1, d_C2;
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
        d_C1 = new Continent("asia",0);
        d_C2 =  new Continent("africa",0);
        d_Country1 = new Country("india","asia");
        d_Country2 = new Country("china","asia");
        d_Country3 = new Country("japan","asia");
        d_Country4 = new Country("kenya","africa");
        d_Country5 = new Country("egypt","africa");
        d_Check =  new ArrayList<Country>();
        d_CheckContinent = new ArrayList<Continent>();
        d_Map = new Map();
        d_CheckContinent.add(d_C1);
        d_CheckContinent.add(d_C2);
        d_Check.add(d_Country1);
        d_Check.add(d_Country2);
        d_Check.add(d_Country3);
        d_Check.add(d_Country4);
        d_Check.add(d_Country5);
        d_Map.addContinent(d_C1.getContinentName(), "1");
        d_Map.addContinent(d_C2.getContinentName(), "1");
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
     * This case checks the functionality of addCountry()
     */
    @Test
    public void testAddCountry() {
        assertTrue(d_Map.getCountryList().contains(d_Country5));
    }

    /**
     * This case checks the functionality of addCountry() to see if it adds a country to the continent that does not exists
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
     * This case checks the functionality of addCountry() to see if it catches exception thrown
     * if the country already exists
     */
    @Test
    public void testAddCountryCountryExists() {
        String l_ExpectedMessage = "Country Already Exist";
        String l_OutputMessage = "";
        try {
            d_Map.addCountry("egypt", "africa");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * This case checks the functionality of removeCountry()
     * @throws Exception If country does not exists
     */
    @Test
    public void testRemoveCountry() throws Exception {
        d_Map.removeCountry("egypt", true);
        assertFalse(d_Map.getCountryList().contains(d_Country5));
    }

    /**
     * This test checks the functionality of removeCountry() to see if the exception is thrown
     * for country does not exists
     */
    @Test
    public void testRemoveCountryThatDoesNotExists() {
        String l_ExpectedMessage = "Country does not exist !!";
        String l_OutputMessage = "";
        try {
            d_Map.removeCountry("congo", true);
        }  catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * This case checks the functionality of addBorder()
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
     * Test addBorder() to check if exception is thrown and caught
     * for adding a neighbouring country that does not exist
     */
    @Test
    public void testAddBorderNeighborDoesNotExist() {
        String l_ExpectedMessage = "Neighbour Country does not exists!";
        String l_OutputMessage = "";
        try {
            d_Map.addBorder("india", "congo");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }

        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * Test addBorder() to check if exception is thrown and caught
     * for adding a country that does not exist
     */
    @Test
    public void testAddBorderCountryDoesNotExist() {
        String l_ExpectedMessage = "Country does not exists!";
        String l_OutputMessage = "";
        try {
            d_Map.addBorder("congo", "india");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * Test addBorder() to check if exception is thrown and caught
     * for adding existed border
     */
    @Test
    public void testAddBorderNeighborExist() {
        String l_ExpectedMessage = "Neighbor Already Exist";
        String l_OutputMessage = "";
        try {
            d_Map.addBorder("japan", "china");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * Test the functionality of removeBorder()
     * @throws Exception If border or Country does not exist
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
     * Test removeBorder() to check if exception is thrown and caught
     * for adding country that does not exist
     */
    @Test
    public void testRemoveBorderCountryDoesNotExist() {
        String l_ExpectedMessage = "Country does not exists!";
        String l_OutputMessage = "";
        try {
            d_Map.removeBorder("congo", "india");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }

        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * Test removeBorder() to check if exception is thrown and caught
     * for adding neighboring country that does not exist
     */
    @Test
    public void testRemoveBorderNeighborDoesNotExist() {
        String l_ExpectedMessage = "Neighbour Country does not exists!";
        String l_OutputMessage = "";
        try {
            d_Map.removeBorder("india", "congo");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage, l_OutputMessage);
    }

    /**
     * To d_Check the functionality of addContinent()
     *
     */
    @Test
    public void testAddContinent() {
        assertTrue(d_Map.getContinentList().contains(d_C1));
    }

    /**
     * To test addContinent() to d_Check  the continent control value
     */
    @Test
    public void testAddContinentContinentControlValue() {
        String l_ExpectedMessage = "Continent control must be a positive integer";
        String l_OutputMessage = "";
        try {
            d_Map.addContinent("europe", "0");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_OutputMessage);
    }

    /**
     * To test addContinent() and d_Check if the Continent already exists or not
     */
    @Test
    public void testAddContinentContinentExists() {
        String l_ExpectedMessage = "Continent Already Exists";
        String l_OutputMessage = "";
        try {
            d_Map.addContinent("asia", "1");
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_OutputMessage);
    }

    /**
     * Test removeContinent()
     */
    @Test
    public void testRemoveContinent(){
        String l_ExpectedMessage = "Continent does not exist !!";
        String l_OutputMessage = "";
        try {
            d_Map.removeContinent(d_C1.getContinentName());
            d_Map.removeContinent(d_C1.getContinentName());
        } catch (Exception p_Exception) {
            l_OutputMessage = p_Exception.getMessage();
        }
        assertEquals(l_ExpectedMessage,l_OutputMessage);
    }

    /**
     * Test Continent and check if country already exits or not
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
     * Test functionality removeCountry() from the continent
     */
    @Test
    public void testRemoveCountryFromContinent() {
        d_Map.removeCountryFromContinent(d_Country1.getCountryName(), d_C1.getCountryList());
        assertFalse(d_C1.getCountryList().contains(d_Country1));
    }

    /**
     * Test removeAllCountryFromContinent() in Map.java
     */
    @Test
    public void testRemoveAllCountryFromContinent() {
        try {
            d_Map.removeAllCountryInContinent(d_C2);
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }
        int l_Flag=0;
        for(Country l_Country : d_C2.getCountryList()) {
            if(d_Map.getCountryList().contains(l_Country)) {
                l_Flag =1;break;
            }
        }
        assertEquals(0,l_Flag);
    }



}