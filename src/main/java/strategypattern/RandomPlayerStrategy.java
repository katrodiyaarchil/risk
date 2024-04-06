package strategypattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.Country;
import model.GameModel;
import model.Order;
import model.Player;
import model.orders.Advance;
import model.orders.Deploy;
/**
 * This Strategy class belongs to the Random Player. It encapsulates the
 * behavior of a Random Player.
 * This strategic player only issues deploy and advance orders to attacks random
 * neighboring countries, and moves armies randomly between its countries.
 *
 */

public class RandomPlayerStrategy extends Strategy implements Serializable {
    /**
     * A random reference to generate random numbers within a bound
     */
    private Random rand;
    /**
     * GameModel new object to get the current map.
     */
    private GameModel d_GameModelNew;
    /**
     * Player reference of this strategy
     */
    private Player d_Player;
    /**
     * CheckArmies Hashmap to get if the player is still interested in issuing
     * orders or wanted to quit.
     */
    HashMap<Player, Boolean> d_CheckArmies = new HashMap<>();
    /**
     * The boolean flag set to true if the player enters "quit" keyword, else false.
     */
    boolean d_decreasePlayerListSize;
    /**
     * The constructor initializes the random player and the game model new object
     * as well as the Random object is created.
     *
     * @param p_Player       The Player whose strategy is Random.
     * @param p_GameModel The Reference of GamemodelNew to get the Map on which
     *                       the match is to be played.
     */

    public RandomPlayerStrategy(Player p_Player, GameModel p_GameModel) {
        this.d_GameModelNew = p_GameModel;
        d_Player = p_Player;
        rand = new Random();
        d_Leb.setResult("Random Player");
    }

    /**
     * This is an overridden toAttack method. The random player gets its own
     * country, the source country by calling the toDefend method.
     * Then it randomly chooses one of the neighboring countries of the source
     * country.
     *
     * @return It returns an Arraylist containing the source country and the target
     *         country to attack to.
     */
    @Override
    public ArrayList<Country> toAttack() {
        ArrayList<Country> l_ReturnCountries = new ArrayList<Country>();
        Country l_ReturnCountry = null;
        Country l_DefendCountry = toDefend();
        l_ReturnCountry = d_GameModelNew.getMap().getCountryList()
                .get(rand.nextInt(d_GameModelNew.getMap().getCountryList().size()));
        l_ReturnCountries.add(0, l_DefendCountry);
        l_ReturnCountries.add(1, l_ReturnCountry);
        return l_ReturnCountries;
    }
    /**
     * This is an overridden toDefend method. The random player randomly selects one
     * of the countries owned by it.
     *
     * @return The country that it wants to deploy army to or move the armies from
     *         in case of an attack.
     */

    @Override
    public Country toDefend() {
        Country l_ReturnCountry = null;
        l_ReturnCountry = d_Player.getCountryList().get(rand.nextInt(d_Player.getCountryList().size()));
        d_Player.setResult("The Random player is defending from " + l_ReturnCountry.getCountryName());
        return l_ReturnCountry;
    }
    /**
     * This is the getter method for the Checkarmies hashmap.
     *
     * @return The Hashmap - Checkarmies
     */
    public HashMap<Player, Boolean> getCheckArmies() {
        return d_CheckArmies;
    }
    /**
     * This is a setter method of the Checkarmies hashmap.
     *
     * @param l_CheckArmies Shows the current status of the players, i.e whether
     *                      they are playing or are out of the game.
     */

    public void setCheckArmies(HashMap<Player, Boolean> l_CheckArmies) {
        d_CheckArmies = l_CheckArmies;
    }
    /**
     * This is the getter method for the decreasePlayerListSize flag.
     *
     * @return true - if the player entered "quit" else false.
     */

    public boolean getDecreasePlayerListSize() {
        return d_decreasePlayerListSize;
    }
    /**
     * This is an overridden method to create an order. The random player can issue
     * a deploy order or an advance order only.
     * In the case of advance order if the source country has armies less than 1,
     * then instead of an advance order the
     * random player chooses to issue a deploy order on that country.
     *
     * @return It returns the order object issued.
     */

    @Override
    public Order createOrder() {
        // TODO Auto-generated method stub
        d_decreasePlayerListSize = false;
        int l_rndOrder = rand.nextInt(2);
        Order l_returnOrder = null;

        switch (l_rndOrder) {
            case 0:
                Country l_DefendCountry1 = toDefend();
                d_Leb.setResult("in random the armies are deployed to -" + l_DefendCountry1.getCountryName());
                l_returnOrder = new Deploy(d_Player, l_DefendCountry1,
                        Math.max(rand.nextInt(d_Player.getPlayerArmies()), 2));
                break;

            case 1:
                ArrayList<Country> l_Countries = toAttack();
                if (l_Countries.get(0).getNoOfArmies() > 1) {
                    d_Leb.setResult("in random defending country - " + l_Countries.get(0).getCountryName()
                            + " Attacking country - " + l_Countries.get(1).getCountryName() + " with armies- "
                            + (l_Countries.get(0).getNoOfArmies() - 1));
                    l_returnOrder = new Advance(d_Player, l_Countries.get(0), l_Countries.get(1),
                            (l_Countries.get(0).getNoOfArmies() - 1));
                } else {
                    d_Leb.setResult("in random the armies are deployed to -" + l_Countries.get(0).getCountryName());
                    l_returnOrder = new Deploy(d_Player, l_Countries.get(0),
                            Math.max(rand.nextInt(d_Player.getPlayerArmies()), 2));
                }
                break;

        }
        d_Leb.setResult("in random player order is " + l_returnOrder);
        return l_returnOrder;
    }

    /**
     * This is an overridden method to provide the strategy of the player.
     *
     * @return a String specifying "Random" - strategy of this class.
     */
    @Override
    public String strategyName() {
        // TODO Auto-generated method stub
        return "Random";
    }
}
