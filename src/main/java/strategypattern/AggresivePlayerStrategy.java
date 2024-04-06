package strategypattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import model.Country;
import model.GameModel;
import model.GameModel;
import model.Order;
import model.Player;
import model.orders.Advance;
import model.orders.Deploy;
/**
 * This class creates orders for the AggressivePlayer according to its strategy.
 * It extends the parent Strategy class and implements the necessary methods.
 */
public class AggresivePlayerStrategy extends Strategy implements Serializable {
    /**
     * Reference to generate random numbers.
     */
    private Random d_Random;
    /**
     * GameModel object to access the current map.
     */
    private GameModel d_GameModelNew;
    /**
     * Reference to the player using this strategy.
     */
    private Player d_Player;
    /**
     * Constructor accepting the aggressive player and the game model containing game-related data.
     *
     * @param p_Player       The aggressive type player
     * @param p_GameModelNew GameModel object
     */
    public AggresivePlayerStrategy(Player p_Player, GameModel p_GameModelNew) {
        this.d_Player = p_Player;
        this.d_GameModelNew = p_GameModelNew;
        d_Random = new Random();
        d_Leb.setResult("Aggressive Player");
    }
    /**
     * This method returns the strongest country for the aggressive player.
     * It sorts the player's countries based on the number of armies and returns the strongest one.
     *
     * @return The strongest country of the player
     */
    @Override
    public Country toDefend() {
        Country l_TempCountry = null;
        HashMap<Country, Integer> l_PlayerCountryMap = new HashMap<>();
        for (Country l_Country : d_Player.getCountryList()) {
            l_PlayerCountryMap.put(l_Country, l_Country.getNoOfArmies());
        }
        List<Entry<Country, Integer>> l_List = new LinkedList<Entry<Country, Integer>>(l_PlayerCountryMap.entrySet());
        Collections.sort(l_List, new Comparator<Entry<Country, Integer>>() {
            @Override
            public int compare(Entry<Country, Integer> l_O1, Entry<Country, Integer> l_O2) {
                return l_O2.getValue().compareTo(l_O1.getValue());
            }
        });
        l_TempCountry = l_List.get(0).getKey();
        d_Player.setResult("The aggressive player is defending from " + l_TempCountry.getCountryName());
        return l_TempCountry;
    }

    /**
     * This method selects a random neighbor of the strongest country to attack.
     * If the country to be attacked belongs to the same player, only transfer of armies occurs, else an advance order is executed.
     *
     * @return The list of source and target countries for the attack
     */
    @Override
    public ArrayList<Country> toAttack() {
        ArrayList<Country> l_ReturnCountries = new ArrayList<Country>();
        Country l_Country = toDefend();
        Country l_ReturnCountry = null;
        ArrayList<Country> l_BorderCountriesList = new ArrayList<>();
        for (Country l_C : this.d_GameModelNew.getMap().getCountryList()) {
            if (l_Country.getBorder().contains(l_C.getCountryName())) {
                l_BorderCountriesList.add(l_C);
            }
        }
        l_ReturnCountry = l_BorderCountriesList.get(d_Random.nextInt(l_BorderCountriesList.size()));
        l_ReturnCountries.add(0, l_Country);
        l_ReturnCountries.add(0, l_ReturnCountry);
        d_Player.setResult("The aggressive player is attacking on " + l_ReturnCountry.getCountryName());
        return l_ReturnCountries;
    }

    /**
     * This method centralizes its armies on the strongest country.
     * It initially deploys on the strongest country and then attacks from it when having enough armies.
     * Deploy and Advance orders are created randomly.
     *
     * @return The order which was created
     */
    @Override
    public Order createOrder() {
        int l_RandomInt = d_Random.nextInt(2);
        Order l_OrderToBeReturned = null;
        switch (l_RandomInt) {
            case 0:
                Country l_DefendCountry1 = toDefend();
                d_Leb.setResult("in agressive the armies are deployed to -" + l_DefendCountry1.getCountryName());
                l_OrderToBeReturned = new Deploy(this.d_Player, l_DefendCountry1,
                        Math.max(d_Random.nextInt(d_Player.getPlayerArmies()), 2));
                break;
            case 1:
                ArrayList<Country> l_Countries = toAttack();
                /*
                 * If player does not have enough armies, it will not advance. it will still
                 * deploy
                 */
                if (l_Countries.get(0).getNoOfArmies() > 1) {
                    d_Leb.setResult("in aggressive defending country - " + l_Countries.get(0).getCountryName()
                            + " Attacking country - " + l_Countries.get(1).getCountryName() + " with armies- "
                            + (l_Countries.get(0).getNoOfArmies() - 1));
                    l_OrderToBeReturned = new Advance(this.d_Player, l_Countries.get(0), l_Countries.get(1),
                            l_Countries.get(0).getNoOfArmies() - 1);
                } else {
                    d_Leb.setResult("in agrressive the armies are deployed to -" + l_Countries.get(0).getCountryName());
                    l_OrderToBeReturned = new Deploy(this.d_Player, toDefend(),
                            Math.max(d_Random.nextInt(d_Player.getPlayerArmies()), 2));
                }
                break;
        }
        d_Leb.setResult("in aggressive the order is - " + l_OrderToBeReturned);
        return l_OrderToBeReturned;
    }
    /**
     * This method returns the strategy type of the player.
     *
     * @return The type of player
     */
    @Override
    public String strategyName() {
        return "Aggressive";
    }

}
