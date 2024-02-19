package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a country or territory in the game map.
 */
public class Country {
    private static int D_Count = 0;
    int d_ID;
    String d_Name;
    String d_ContinentName;
    ArrayList<String> d_Neighbors;
    int d_NoOfArmies;

    /**
     * Constructs a country with the given name and continent name.
     * Initializes an empty list to hold neighboring countries.
     *
     * @param p_Name          The name of the country.
     * @param p_ContinentName The name of the continent.
     */
    public Country(String p_Name, String p_ContinentName) {
        setCountryID(++D_Count);
        this.d_Name = p_Name;
        this.d_ContinentName = p_ContinentName;
        d_Neighbors = new ArrayList<String>();
    }

    /**
     * Sets the static ID of the country.
     *
     * @param p_Count The ID to set.
     */
    public static void setCount(int p_Count) {
        D_Count = p_Count;
    }

    /**
     * Gets the name of the continent to which the country belongs.
     *
     * @return The name of the continent.
     */
    public String getContinentName() {
        return this.d_ContinentName;
    }

    /**
     * Adds a neighboring country to the list of borders.
     *
     * @param p_Border The name of the neighboring country.
     */
    public void setBorder(String p_Border) {
        this.d_Neighbors.add(p_Border);
    }

    /**
     * Gets the list of neighboring countries.
     *
     * @return The list of neighboring countries.
     */
    public ArrayList<String> getBorder() {
        return this.d_Neighbors;
    }

    /**
     * Gets the name of the country.
     *
     * @return The name of the country.
     */
    public String getCountryName() {
        return d_Name;
    }

    /**
     * Gets the ID of the country.
     *
     * @return The ID of the country.
     */
    public int getCountryID() {
        return d_ID;
    }

    /**
     * Sets the ID of the country.
     *
     * @param p_Id The ID to set.
     */
    public void setCountryID(int p_Id) {
        d_ID = p_Id;
    }

    /**
     * {@inheritDoc}
     * Compares the present Country object with another country object.
     *
     * @param p_Country The country object to compare.
     * @return True if the objects are equal, false otherwise.
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
     * Removes a neighboring country from the list of borders.
     *
     * @param p_Border The name of the neighboring country to remove.
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
     * Gets the number of armies deployed in the country.
     *
     * @return The number of armies deployed.
     */
    public int getNoOfArmies() {
        return d_NoOfArmies;
    }

    /**
     * Sets the number of armies deployed in the country.
     *
     * @param p_NoOfArmies The number of armies to set.
     */
    public void setNoOfArmies(int p_NoOfArmies) {
        d_NoOfArmies = p_NoOfArmies;
    }
}
