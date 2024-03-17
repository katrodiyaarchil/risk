package model;

import java.util.*;

/**
 * This is a class for all the countries/territories.
 */
public class Country {
    private static int D_Count = 0;
    int d_ID;
    String d_Name;
    String d_ContinentName;
    ArrayList<String> d_Neighbors;
    int d_NoOfArmies;
    private Player d_Owner;

    /**
     * This is the constructor of the class with stores the name of country and its
     * respective continent name
     * The Id is generated Statically, also initializes an arraylist which will
     * later hold all the neighboring countries
     * 
     * @param p_Name          Country Name
     * @param p_ContinentName Continent Name
     */
    public Country(String p_Name, String p_ContinentName) {
        setCountryID(++D_Count);
        this.d_Name = p_Name;
        this.d_ContinentName = p_ContinentName;
        d_Neighbors = new ArrayList<String>();
    }

    /**
     * Method to set the static id of the country
     * 
     * @param p_Count Integer that is set for Id
     */
    public static void setCount(int p_Count) {
        D_Count = p_Count;
    }

    /**
     * Method to return the name of the continent of the present country object
     * 
     * @return d_ContinentName ContinentName
     */
    public String getContinentName() {
        return this.d_ContinentName;
    }

    /**
     * Method to set the borders of neighboring Countries
     * 
     * @param p_Border Name of neighboring country
     */
    public void setBorder(String p_Border) {
        this.d_Neighbors.add(p_Border);
    }

    /**
     * Method to get the list of neighboring Countries
     * 
     * @return d_Neighbors Arraylist of neighboring countries
     */
    public ArrayList<String> getBorder() {
        return this.d_Neighbors;
    }

    /**
     * Method to get Country Name
     * 
     * @return Country Name
     */
    public String getCountryName() {
        return d_Name;
    }

    /**
     * Method to get Country ID
     * 
     * @return COuntry ID
     */
    public int getCountryID() {
        return d_ID;
    }

    /**
     * Method to set the id of the country
     * 
     * @param p_Id Country ID
     */
    public void setCountryID(int p_Id) {
        d_ID = p_Id;
    }

    /**
     * {@inheritDoc}
     * Compares the present Country object with other country object and returns
     * true
     * If the Country object parameter is empty then it returns false
     * 
     * @param p_Country Country Object
     */
    @Override
    public boolean equals(Object p_Country) {
        if (this == p_Country) {
            return true;
        }
        if (p_Country == null || this.getClass() != p_Country.getClass()) {
            return false;
        }
        Country l_P1 = (Country) p_Country;
        return this.getCountryName().equals(l_P1.getCountryName());
    }

    /**
     * Method to remove the Border
     * 
     * @param p_Border Neighbor name
     */
    public void removeBorder(String p_Border) {
        Iterator<String> l_Iterator = this.d_Neighbors.iterator();
        while (l_Iterator.hasNext()) {
            if (l_Iterator.next().toString().equals(p_Border)) {
                l_Iterator.remove();
            }
        }
    }

    /**
     * Method to return the number of armies
     * 
     * @return NoOfArmies
     */
    public int getNoOfArmies() {
        return d_NoOfArmies;
    }

    /**
     * Method to set number of armies
     * 
     * @param p_NoOfArmies pass the number of armies to set
     */
    public void setNoOfArmies(int p_NoOfArmies) {
        d_NoOfArmies = p_NoOfArmies;
    }

    /**
     * Method to get the owner player of the country
     * 
     * @return Player object who owns the country
     */
    public Player getCountryOwnerPlayer() {
        return this.d_Owner;
    }

    /**
     * Method to set the owner player of the country
     * 
     * @param p_OwnerPlayer Player object to be assigned to the owner player of the
     *                      country
     */
    public void setCountryOwnerPlayer(Player p_OwnerPlayer) {
        this.d_Owner = p_OwnerPlayer;
    }
}
