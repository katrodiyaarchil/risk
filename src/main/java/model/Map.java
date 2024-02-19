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

public class Map implements Serializable {

    private ArrayList<Country> d_CountryObjects;

    private ArrayList<Continent> d_ContinentObjects;

    private HashMap<Integer, Integer> d_PreviousSave;

        public Map() {
        d_CountryObjects = new ArrayList<Country>();
        d_ContinentObjects = new ArrayList<Continent>();
        // d_Neighbors=new HashMap<Integer,ArrayList<Integer>>();
        d_PreviousSave = new HashMap<Integer, Integer>();
    }

        public ArrayList<Country> getCountryList() {
        return this.d_CountryObjects;
    }

        public void reset() {
        this.d_ContinentObjects.clear();
        this.d_CountryObjects.clear();
        // this.d_Neighbors.clear();
        this.d_PreviousSave.clear();
        Country.setCount(0);
        Continent.setCount(0);
    }

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
            // searching for the continent keyword in file and loading all continents into
            // continent object list
            if (l_Line.contains("continent")) {
                l_Line = l_Sc.nextLine();
                while (!"".equals(l_Line) && l_Sc.hasNextLine()) {
                    String[] l_Arr = l_Line.split(" ", 3);
                    l_ControlValue = Integer.parseInt(l_Arr[1]);
                    this.d_ContinentObjects.add(new Continent(l_Arr[0], l_ControlValue));
                    l_Line = l_Sc.nextLine();
                }
            }
            // searching for countries keyword and loading all countries from file to map's
            // country object list
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
            // searching for borders keyword and loading all neighbors from file to map's
            // country object list
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

        public void removeCountryFromContinent(String p_CountryName, ArrayList<Country> p_CountryListOfSpecificContinent) {
        Iterator<Country> l_Iterator = p_CountryListOfSpecificContinent.iterator();
        while (l_Iterator.hasNext()) {
            Country l_TempCountry = l_Iterator.next();
            if (l_TempCountry.getCountryName().equalsIgnoreCase(p_CountryName)) {
                l_Iterator.remove();
            }
        }
    }

        public void removeAllCountryInContinent(Continent p_TempContinent) throws Exception {
        ArrayList<Country> l_TempCountryList = p_TempContinent.getCountryList();
        Iterator<Country> l_CountriesOfContinent = l_TempCountryList.iterator();
        while (l_CountriesOfContinent.hasNext()) {
            Country l_TempCountryToBeRemoved = l_CountriesOfContinent.next();
            removeCountry(l_TempCountryToBeRemoved.getCountryName(), false);
        }
    }

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

        public ArrayList<Continent> getContinentList() {
        return this.d_ContinentObjects;
    }

        public void getContinents() {
        for (Continent l_Continent : this.d_ContinentObjects) {
            System.out.println("ID :  " + l_Continent.getContinentID() + " Name : " + l_Continent.getContinentName());
        }
    }

        public void getCountries() {
        for (Country l_Country : this.d_CountryObjects) {
            System.out.println("ID : " + l_Country.getCountryID() + ", Name : " + l_Country.getCountryName()
                    + ", ContinentName :" + l_Country.getContinentName());
        }
    }

        public String validateMap() throws Exception {
        ValidateMap l_VMap = new ValidateMap(this.d_CountryObjects, this.d_ContinentObjects);
        return l_VMap.isValid();
    }
}