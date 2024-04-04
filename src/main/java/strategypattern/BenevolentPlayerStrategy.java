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
import model.Order;
import model.Player;
import model.orders.Deploy;
/**
 * This class implements the Strategy for the benevolent type of player.
 * This type of player always deloys on it's weak country and never attacks.
 */

public class BenevolentPlayerStrategy extends Strategy implements Serializable {

    /**
     * The Random reference to generate random numbers.
     */
    private Random d_Random;
    /**
     * GameModel new object to get the current map.
     */
    private GameModel d_GameModelNew;
    /**
     * Player reference of this strategy
     */
    private Player d_Player;
    /**
     * Constructor which accepts the Player object and GameModel object to implement strategy.
     * @param p_Player object of the player whose strategy is benevolent.
     * @param p_GameModelNew object of the gamemodel class
     */

    public BenevolentPlayerStrategy(Player p_Player,GameModel p_GameModelNew) {
        this.d_GameModelNew = p_GameModelNew;
        this.d_Player = p_Player;
        d_Random = new Random();
        d_Leb.setResult("Benevolent Player");
    }

    /**
     * This method returns the weakest country of the player where he wants to deploy armies just to save it.
     * @return returns the country at which armies are deploy
     */
    @Override
    public Country toDefend() {
        Country l_TempCountry=null;
        int l_NumberOfArmies = 0;
        HashMap <Country,Integer> l_PlayerCountryMap = new HashMap<>();

        for(Country l_Country : d_Player.getCountryList()){
            System.out.println("in for loop "+l_Country+" - "+l_Country.getNoOfArmies());
            l_PlayerCountryMap.put(l_Country, l_Country.getNoOfArmies());
            System.out.println(l_Country+" - "+l_Country.getNoOfArmies());
        }
        System.out.println("int benevolent treemap is "+l_PlayerCountryMap);
        List<Entry<Country, Integer>> l_List = new LinkedList<Entry<Country, Integer>>(l_PlayerCountryMap.entrySet());
        Collections.sort(l_List, new Comparator<Entry<Country, Integer>>(){
            @Override
            public int compare(Entry<Country, Integer> l_O1, Entry<Country, Integer> l_O2)
            {
                return l_O1.getValue().compareTo(l_O2.getValue());
            }
        });
        l_TempCountry = l_List.get(0).getKey();

        System.out.println("left for loop  "+l_TempCountry);
        d_Player.setResult("the Benevolent Player is defefnding country "+l_TempCountry.getCountryName());
        d_Leb.setResult("the Benevolent Player is defefnding country "+l_TempCountry.getCountryName()+" country with "+l_TempCountry.getNoOfArmies()+" armies");
        return l_TempCountry;
    }

    /**
     * Benevolent player never attacks so it is returning null.
     * @return null
     */
    @Override
    public ArrayList<Country> toAttack()
    {
        return null;
    }
    /**
     * createOrder method for the benevolent player only returns the deploy order for player's weakest country.
     * @return Order object created
     */

    @Override
    public Order createOrder() {
        // TODO Auto-generated method stub
        Order l_ReturnOrder=null;
        Country l_DefendCountry1 = toDefend();
        d_Leb.setResult("in cheater the armies are deployed to -" +l_DefendCountry1.getCountryName());
        l_ReturnOrder =  new Deploy(this.d_Player, l_DefendCountry1, Math.max(d_Random.nextInt(d_Player.getPlayerArmies()),2));
        d_Leb.setResult("in benevolent player the order issued is - "+l_ReturnOrder);
        return l_ReturnOrder;
    }

    /**
     * This method returns the name of the player's strategy
     * @return String which indicates the type of player strategy
     */
    @Override
    public String strategyName() {
        // TODO Auto-generated method stub
        return "Benevolent";
    }
}