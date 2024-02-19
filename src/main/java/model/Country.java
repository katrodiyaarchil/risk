package model;

import java.util.*;

public class Country {
    private static int D_Count = 0;
    int d_ID;
    String d_Name;
    String d_ContinentName;
    ArrayList<String> d_Neighbors;
    int d_NoOfArmies;

    public Country(String p_Name, String p_ContinentName) {
        setCountryID(++D_Count);
        this.d_Name = p_Name;
        this.d_ContinentName = p_ContinentName;
        d_Neighbors = new ArrayList<String>();
    }

    public static void setCount(int p_Count) {
        D_Count = p_Count;
    }

    public String getContinentName() {
        return this.d_ContinentName;
    }

    public void setBorder(String p_Border) {
        this.d_Neighbors.add(p_Border);
    }

    public ArrayList<String> getBorder() {
        return this.d_Neighbors;
    }

    public String getCountryName() {
        return d_Name;
    }

    public int getCountryID() {
        return d_ID;
    }

    public void setCountryID(int p_Id) {
        d_ID = p_Id;
    }

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

    public void removeBorder(String p_Border) {
        Iterator<String> l_Iterator = this.d_Neighbors.iterator();
        while (l_Iterator.hasNext()) {
            if (l_Iterator.next().toString().equals(p_Border)) {
                l_Iterator.remove();
            }
        }
    }

    public int getNoOfArmies() {
        return d_NoOfArmies;
    }

    public void setNoOfArmies(int p_NoOfArmies) {
        d_NoOfArmies = p_NoOfArmies;
    }
}