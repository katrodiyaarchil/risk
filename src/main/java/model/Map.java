package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import controller.ValidateMap;

/**
 * This is the model class for the Map used in the game.
 * This class consists of all the data members and behavior associated with Map.
 * This class includes methods to add/remove continents, countries and borders
 * as well as loadmap and savemap functionalities.
 */
public class Map implements Serializable {

    /**
     * This is the Arraylist of country objects
     */
    private ArrayList<Country> d_CountryObjects;
    /**
     * This is the ArrayList of continent objects
     */
    private ArrayList<Continent> d_ContinentObjects;
    /**
     * This hashmap is used for proper ordering of countries in save method
     */
    private HashMap<Integer, Integer> d_PreviousSave;

    /**
     * This is the default constructor of the class.
     * When the map object is created, this class initializes Continent, Countries
     * and Neighbour containers
     */
    public Map() {
        d_CountryObjects = new ArrayList<Country>();
        d_ContinentObjects = new ArrayList<Continent>();
        // d_Neighbors=new HashMap<Integer,ArrayList<Integer>>();
        d_PreviousSave = new HashMap<Integer, Integer>();
    }

    /**
     * This method returns the ArrayList of country objects
     *
     * @return ArrayList of CountryObjects
     */
    public ArrayList<Country> getCountryList() {
        return this.d_CountryObjects;
    }

    /**
     * This method initializes the map from scratch by clearing all the containers
     * inside it.
     * This method clears Continent, Country and Neighbor containers.
     * This method also set the static IDs of Country and Continents back to zero.
     */
    public void reset() {
        this.d_ContinentObjects.clear();
        this.d_CountryObjects.clear();
        // this.d_Neighbors.clear();
        this.d_PreviousSave.clear();
        Country.setCount(0);
        Continent.setCount(0);
    }

    /**
     * This method loads a map file given by the user.
     * <ul>
     * <li>This method checks for the input and decides if it is a loadmap or
     * editmap functionality.</li>
     * <li>It gets the data from file and loads the map object in respective
     * continent, countries and neighbors.</li>
     * <li>Before loading or editing any file, it resets the game model so that it
     * doesn't conflict with the existing data in map model.</li>
     * </ul>
     *
     * @param p_FileName Name of the file to Load
     * @return Map is loaded or not
     * @throws FileNotFoundException File not Found to Load
     */
    public String loadMap(String p_FileName) throws FileNotFoundException {
        reset();
        String l_Path = "resource/";
        String l_Result;
        int l_ControlValue;
        int l_ContinentID = 1;
        File l_File = new File(l_Path + p_FileName);
        Scanner l_Sc = new Scanner(l_File);
        while (l_Sc.hasNextLine()) {
            String l_Line = l_Sc.nextLine();
            /*
                searching for the continent keyword in file and loading all continents into
                continent object list
             */
            if (l_Line.contains("continent")) {
                l_Line = l_Sc.nextLine();
                while (!"".equals(l_Line) && l_Sc.hasNextLine()) {
                    String[] l_Arr = l_Line.split(" ", 3);
                    l_ControlValue = Integer.parseInt(l_Arr[1]);
                    this.d_ContinentObjects.add(new Continent(l_Arr[0], l_ControlValue));
                    l_Line = l_Sc.nextLine();
                }
            }
            /*
            searching for countries keyword and loading all countries from file to map's
            country object list
             */
            if (l_Line.contains("countries")) {
                l_Line = l_Sc.nextLine();
                while (!"".equals(l_Line) && l_Sc.hasNextLine()) {
                    String[] l_Arr1 = l_Line.split(" ", 4);
                    l_ContinentID = Integer.parseInt(l_Arr1[2]);
                    String l_NeighborName = "";
                    for (Continent l_Continent : this.d_ContinentObjects) {
                        if (l_Continent.getContinentID() == l_ContinentID) {
                            l_NeighborName = l_Continent.getContinentName();
                        }
                    }
                    Country l_TempCountry = new Country(l_Arr1[1], l_NeighborName);
                    this.d_CountryObjects.add(l_TempCountry);
                    for (Continent l_Continent : this.d_ContinentObjects) {
                        if (l_Continent.getContinentID() == l_ContinentID) {
                            l_Continent.addCountry(l_TempCountry);
                        }
                    }
                    l_Line = l_Sc.nextLine();
                }
            }
            /*
                searching for borders keyword and loading all neighbors from file to map's
                country object list
             */
            if (l_Line.contains("borders")) {
                while (!"".equals(l_Line) && l_Sc.hasNextLine()) {
                    l_Line = l_Sc.nextLine();
                    String[] l_Arr2 = l_Line.split(" ");
                    for (Country l_Tempcountry : this.d_CountryObjects) {
                        if (l_Tempcountry.d_ID == Integer.parseInt(l_Arr2[0])) {
                            ArrayList<Integer> l_Borders = new ArrayList<>();
                            for (int l_K = 1; l_K < l_Arr2.length; l_K++) {
                                for (Country l_TCountry : this.d_CountryObjects) {
                                    if (l_TCountry.d_ID == Integer.parseInt(l_Arr2[l_K])) {
                                        l_Tempcountry.setBorder(l_TCountry.d_Name);
                                    }
                                }
                                l_Borders.add(Integer.parseInt(l_Arr2[l_K]));
                            }
                            break;
                        }
                    }
                }
            }
        }
        l_Sc.close();
        String l_Result1 = validateMap();
        if ("Map is not Valid".equals(l_Result1)) {
            reset();
            return l_Result1;
        }
        l_Result = "The Map is loaded with " + this.d_ContinentObjects.size() + " Continents and "
                + this.d_CountryObjects.size() + " Countries";
        return l_Result;
    }

    /**
     * It saves the user edited map. It checks for the validation of the map object
     * and then only it saves into the file.
     *
     * @param p_FileName File name to save the map
     * @return If Map is saved successfully or not
     * @throws Exception No continent to save
     */
    public String saveMap(String p_FileName) throws Exception {
        String l_Result = validateMap();
        if ("Map is not Valid".equals(l_Result)) {
            return l_Result;
        }
        String l_Path = "resource/";
        ArrayList<String> l_Borders = new ArrayList<>();
        File l_File = new File(l_Path + p_FileName);
        FileWriter l_Fw = new FileWriter(l_File);
        PrintWriter l_Pr = new PrintWriter(l_Fw);
        l_Pr.println("");
        l_Pr.println("continents");
        if (this.d_ContinentObjects.size() <= 0) {
            l_Pr.close();
            throw new Exception("No Continent to Save");
        }
        // adding all the continents in the file
        for (Continent l_Co : this.d_ContinentObjects) {
            String l_Continent_Name = l_Co.getContinentName().replaceAll("\\s", "");

            l_Pr.println(l_Continent_Name + " " + l_Co.getContinentControlValue());
        }
        l_Pr.println("");
        l_Pr.println("countries");
        int l_CountryOrder = 0;
        // adding all countries in the file
        for (Country l_C : this.d_CountryObjects) {
            l_CountryOrder++;
            String l_ContinentName = l_C.getContinentName();
            int l_ContinentId = 0;
            int l_ContinentOrder = 0;
            for (Continent l_Ct : this.d_ContinentObjects) {
                l_ContinentOrder += 1;
                if (l_Ct.getContinentName().equals(l_ContinentName)) {
                    l_ContinentId = l_ContinentOrder;
                }
            }
            this.d_PreviousSave.put(l_C.d_ID, l_CountryOrder);
            l_Pr.println(l_CountryOrder + " " + l_C.d_Name + " " + l_ContinentId);
        }
        l_Pr.println("");
        l_Pr.println("borders");
        // adding all the borders in the file.
        for (Country l_C : d_CountryObjects) {
            int l_New = this.d_PreviousSave.get(l_C.d_ID);
            l_Borders = l_C.getBorder();
            l_Pr.print(l_New + " ");
            for (String l_Str : l_Borders) {
                for (Country l_Country : this.d_CountryObjects) {
                    if (l_Country.getCountryName().equals(l_Str)) {
                        int l_NewNeighbor = this.d_PreviousSave.get(l_Country.d_ID);
                        l_Pr.print(l_NewNeighbor + " ");
                    }
                }

            }
            l_Pr.println("");
        }
        l_Pr.close();
        l_Fw.close();
        return "Map Saved Successfully";
    }

    /**
     * This method receives ContinentName and its Control value from the user
     * through command and saves the continent in map.
     *
     * @param p_ContinentName         Name of the continent to be added
     * @param p_ContinentControlValue Control value of the continent to be added
     * @throws Exception In case of continent already exists, it throws an exception
     */
    public void addContinent(String p_ContinentName, String p_ContinentControlValue) throws Exception {

        if ("0".equals(p_ContinentControlValue)) {
            throw new Exception("Continent control must be a positive integer");
        }
        for (Continent l_Contient : this.d_ContinentObjects) {
            if (l_Contient.getContinentName().equalsIgnoreCase(p_ContinentName)) {
                throw new Exception("Continent Already Exists");
            }
        }
        try {
            this.d_ContinentObjects.add(new Continent(p_ContinentName, Integer.parseInt(p_ContinentControlValue)));
        } catch (Exception l_E) {
            throw new Exception("Please enter a valid Integer for Continent Control Value");
        }
    }

    /**
     * This method removes the continent when user enters remove continent command.
     * It also removes all the countries inside that continent.
     *
     * @param p_ContinentName Name of the continent to be removed
     * @throws Exception In case the continent doesn't exist, it throws an exception
     */
    public void removeContinent(String p_ContinentName) throws Exception {
        Iterator<Continent> l_Iterator = this.d_ContinentObjects.iterator();
        boolean l_RemovedFlag = false;
        while (l_Iterator.hasNext()) {
            Continent l_TempContinent = l_Iterator.next();
            if (l_TempContinent.getContinentName().equalsIgnoreCase(p_ContinentName)) {
                removeAllCountryInContinent(l_TempContinent);
                l_Iterator.remove();
                l_RemovedFlag = true;
            }
        }
        if (!l_RemovedFlag) {
            throw new Exception("Continent does not exist !!");
        }
    }

    /**
     * This method adds a new country in the map.
     *
     * @param p_CountryName   Name of the country to be added
     * @param p_ContinentName Name of the parent continent
     * @throws Exception In case country already exists, it throws an exception
     */
    public void addCountry(String p_CountryName, String p_ContinentName) throws Exception {
        int l_Flag = 0;
        for (Continent l_C : this.getContinentList()) {
            if (l_C.getContinentName().equals(p_ContinentName)) {
                l_Flag = 1;
                break;
            }
        }
        if (l_Flag == 0) {
            throw new Exception("Continent Doesn't Exist to add a Country");
        }
        Country l_TempCountry = new Country(p_CountryName, p_ContinentName);
        for (Country l_Country : d_CountryObjects) {
            if (l_Country.getCountryName().equalsIgnoreCase(p_CountryName)) {
                throw new Exception("Country Already Exist");
            }
        }
        this.d_CountryObjects.add(l_TempCountry);
        for (Continent l_Continent : d_ContinentObjects) {
            if (l_Continent.getContinentName().equals(p_ContinentName)) {
                l_Continent.addCountry(l_TempCountry);
            }
        }
    }

    /**
     * This method removes the country from the map.
     * This method is used at two places. 1) editcountry remove 2) editcontinent
     * remove (to remove all countries of continent)
     *
     * @param p_CountryName         Name of the country to be removed
     * @param p_IsOnlyCountryRemove This flag tells the method if it is a
     *                              editcontinent command or editcountry. True for
     *                              editcountry
     * @throws Exception if country in the list doesn't exist, it throws an
     *                   exception
     */
    public void removeCountry(String p_CountryName, boolean p_IsOnlyCountryRemove) throws Exception {
        Iterator<Country> l_Iterator = this.d_CountryObjects.iterator();
        boolean l_RemovedFlag = false;
        int l_TempCountryIdOfCountryToBeRemoved = 0;
        while (l_Iterator.hasNext()) {
            Country l_TempCountry = l_Iterator.next();
            if (l_TempCountry.getCountryName().equalsIgnoreCase(p_CountryName)) {

                /*
                    Below block is executed only when "editcountry remove" command is called.
                    Not for "editcontinent remove" command.
                */

                l_TempCountryIdOfCountryToBeRemoved = l_TempCountry.getCountryID();
                if (p_IsOnlyCountryRemove) {
                    String l_OwnerContinent = l_TempCountry.getContinentName();
                    for (Continent l_TempContinent : d_ContinentObjects) {
                        if (l_TempContinent.getContinentName().equals(l_OwnerContinent)) {
                            ArrayList<Country> l_CountryListOfOwnerContinent = l_TempContinent.getCountryList();
                            removeCountryFromContinent(p_CountryName, l_CountryListOfOwnerContinent);
                        }
                    }
                }
                for (Country l_Country : d_CountryObjects) {
                    ArrayList<String> l_CountryNeighbors = l_Country.getBorder();
                    Iterator<String> l_NeighborIterator = l_CountryNeighbors.iterator();
                    while (l_NeighborIterator.hasNext()) {
                        String l_NeighborName = l_NeighborIterator.next();
                        if (l_NeighborName.equalsIgnoreCase(p_CountryName)) {
                            l_NeighborIterator.remove();
                        }
                    }
                }
                l_Iterator.remove();
                l_RemovedFlag = true;
            }
        }
        if (!l_RemovedFlag) {
            throw new Exception("Country does not exist !!");
        }
    }


    /**
     * This method is called to remove specific country from the country list of
     * continent
     *
     * @param p_CountryName                    Name of the country to be removed
     * @param p_CountryListOfSpecificContinent List of all countries of the parent
     *                                         continent where this country belongs
     */
    public void removeCountryFromContinent(String p_CountryName, ArrayList<Country> p_CountryListOfSpecificContinent) {
        Iterator<Country> l_Iterator = p_CountryListOfSpecificContinent.iterator();
        while (l_Iterator.hasNext()) {
            Country l_TempCountry = l_Iterator.next();
            if (l_TempCountry.getCountryName().equalsIgnoreCase(p_CountryName)) {
                l_Iterator.remove();
            }
        }
    }

    /**
     * This method removes all the countries in a given continent.
     *
     * @param p_TempContinent Reference of continent object for which all countries
     *                        are to be removed.
     * @throws Exception throws exception in case of error
     */
    public void removeAllCountryInContinent(Continent p_TempContinent) throws Exception {
        ArrayList<Country> l_TempCountryList = p_TempContinent.getCountryList();
        Iterator<Country> l_CountriesOfContinent = l_TempCountryList.iterator();
        while (l_CountriesOfContinent.hasNext()) {
            Country l_TempCountryToBeRemoved = l_CountriesOfContinent.next();
            removeCountry(l_TempCountryToBeRemoved.getCountryName(), false);
        }
    }

    /**
     * This method adds the border between two countries in the map.
     * This is a one directional addition of border from source to target country.
     *
     * @param p_CountryName  Name of the source country
     * @param p_NeighborName Name of the target country
     * @throws Exception Neighbor, Country not exists
     */
    public void addBorder(String p_CountryName, String p_NeighborName) throws Exception {
        int l_Flag = 0;
        for (Country l_C : this.getCountryList()) {
            if (l_C.getCountryName().equals(p_NeighborName)) {
                l_Flag = 1;
                break;
            }
        }
        if (l_Flag == 0) {
            throw new Exception("Neighbour Country does not exists!");
        }
        int l_Flag1 = 0;
        for (Country l_C : this.getCountryList()) {
            if (l_C.getCountryName().equals(p_CountryName)) {
                l_Flag1 = 1;
                break;
            }
        }
        if (l_Flag1 == 0) {
            throw new Exception("Country does not exists!");
        }
        for (Country l_C : this.getCountryList()) {
            if (l_C.getCountryName().equals(p_CountryName) && l_C.getBorder().contains(p_NeighborName)) {
                throw new Exception("Neighbor Already Exist");
            }
        }
        int l_NeighborId = 0;
        int l_CountryId = 0;
        for (Country l_TempCountry : this.getCountryList()) {
            if (l_TempCountry.getCountryName().equals(p_NeighborName)) {
                l_NeighborId = l_TempCountry.getCountryID();
            }
        }
        for (Country l_TempCountry : this.getCountryList()) {
            if (l_TempCountry.getCountryName().equals(p_CountryName)) {
                l_CountryId = l_TempCountry.getCountryID();
                l_TempCountry.setBorder(p_NeighborName);
            }
        }
    }

    /**
     * This method removes the border between source and target. Uni-directional
     * remove only
     *
     * @param p_CountryName   Name of the source country
     * @param p_NeighbourName Name of the target country
     * @throws Exception Country, Neighbor not exists
     */
    public void removeBorder(String p_CountryName, String p_NeighbourName) throws Exception {
        int l_NeighborId = 0;
        int l_CountryId = 0;
        int l_Flag = 0;
        int l_Flag1 = 0;
        for (Country l_C : this.getCountryList()) {
            if (l_C.getCountryName().equals(p_CountryName)) {
                l_Flag = 1;
                break;
            }
        }
        if (l_Flag == 0) {
            throw new Exception("Country does not exists!");
        }
        for (Country l_C : this.getCountryList()) {
            if (l_C.getCountryName().equals(p_NeighbourName)) {
                l_Flag1 = 1;
                break;
            }
        }
        if (l_Flag1 == 0) {
            throw new Exception("Neighbour Country does not exists!");
        }
        for (Country l_TempCountry : this.getCountryList()) {
            if (l_TempCountry.getCountryName().equals(p_NeighbourName)) {
                l_NeighborId = l_TempCountry.getCountryID();
            }
        }
        for (Country l_TempCountry : this.getCountryList()) {
            if (l_TempCountry.getCountryName().equals(p_CountryName)) {
                l_CountryId = l_TempCountry.getCountryID();
                l_TempCountry.removeBorder(p_NeighbourName);
            }
        }
    }

    /**
     * This method is to get list of continents
     *
     * @return ArrayList of continents
     */
    public ArrayList<Continent> getContinentList() {
        return this.d_ContinentObjects;
    }

    /**
     * This method prints the list of continents
     */
    public void getContinents() {
        for (Continent l_Continent : this.d_ContinentObjects) {
            System.out.println("ID :  " + l_Continent.getContinentID() + " Name : " + l_Continent.getContinentName());
        }
    }

    /**
     * This method prints the list of countries
     */
    public void getCountries() {
        for (Country l_Country : this.d_CountryObjects) {
            System.out.println("ID : " + l_Country.getCountryID() + ", Name : " + l_Country.getCountryName()
                    + ", ContinentName :" + l_Country.getContinentName());
        }
    }

    /**
     * This method is used to validate the map.
     * It creates an object of validate map and pass the neighbors hashmap to it.
     *
     * @return It returns the string to inform if map is valid or not.
     * @throws Exception The countries are not internally connected
     */
    public String validateMap() throws Exception {
        ValidateMap l_VMap = new ValidateMap(this.d_CountryObjects, this.d_ContinentObjects);
        return l_VMap.isValid();
    }
}