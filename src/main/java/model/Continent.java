package model;

import java.util.ArrayList;

public class Continent {
    private static int D_Count = 0;
    private int d_ID;
    private String d_Name;
    private int d_ContinentControlValue;
    private ArrayList<Country> d_CountryList;

    public Continent(String p_Name, int p_ContinentControlValue) {
        setContinentID(++D_Count);
        this.d_Name = p_Name;
        this.d_ContinentControlValue = p_ContinentControlValue;
        d_CountryList = new ArrayList<Country>();
    }

    public void setContinentID(int p_ContinentID) {
        d_ID = p_ContinentID;
    }

    public int getContinentID() {
        return d_ID;
    }

    public static void setCount(int p_Count) {
        D_Count = p_Count;
    }

    public String getContinentName() {
        return this.d_Name;
    }

    public void addCountry(Country p_Country) {
        this.d_CountryList.add(p_Country);
    }

    public int getContinentControlValue() {
        return d_ContinentControlValue;
    }

    @Override
    public boolean equals(Object p_Continent) {
        if (this == p_Continent) {
            return true;
        }
        if (p_Continent == null || this.getClass() != p_Continent.getClass()) {
            return false;
        }
        Continent l_P1 = (Continent) p_Continent;
        return this.getContinentName().equals(l_P1.getContinentName());
    }

    public ArrayList<Country> getCountryList() {
        return this.d_CountryList;
    }
}
