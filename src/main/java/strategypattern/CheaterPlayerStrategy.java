package strategypattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import model.Country;
import model.GameModel;
import model.Order;
import model.Player;
import model.orders.Advance;
import model.orders.Deploy;

/**
 * This Strategy class belongs to the Cheater Player. It encapsulates the
 * behavior of a Cheater Player.
 * This strategic player only issues deploy and advance orders to attacks on
 * neighboring countries,
 * and increases the number of armies on its countries that have enemy
 * neighbors.
 *
 */
public class CheaterPlayerStrategy extends Strategy implements Serializable {

    /**
     * The Random reference to generate random numbers.
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
     * The constructor initializes the cheater player and the game model new object
     * as well as the Random object is created.
     *
     * @param p_Player       The Player whose strategy is Cheater.
     * @param p_GameModel The Reference of GamemodelNew to get the Map on which
     *                       the match is to be played.
     */
    public CheaterPlayerStrategy(Player p_Player, GameModel p_GameModel) {
        this.d_GameModelNew = p_GameModel;
        d_Player = p_Player;
        rand = new Random();
        d_Leb.setResult("Cheater Player");
    }

    /**
     * This is an overridden toAttack method. The random player gets its own
     * strongest country, the source country by calling the toDefend method.
     * Then it randomly chooses one of the neighboring countries of the source
     * country.
     *
     * @return It returns an Arraylist containing the source country and the target
     *         country to attack to.
     */
    @Override
    public ArrayList<Country> toAttack() {
        ArrayList<Country> l_ReturnCountries = new ArrayList<Country>();
        Country l_AttackCountry = null;
        Country l_DefendingCountry = toDefend();
        String l_ReturnCountryName = "";
        l_ReturnCountryName = l_DefendingCountry.getBorder().get(rand.nextInt(l_DefendingCountry.getBorder().size()));
        for (Country l_TempCountry : d_GameModelNew.getMap().getCountryList()) {
            if (l_TempCountry.getCountryName().equals(l_ReturnCountryName)) {
                l_AttackCountry = l_TempCountry;
                break;
            }

        }
        l_ReturnCountries.add(0, l_DefendingCountry);
        l_ReturnCountries.add(1, l_AttackCountry);
        d_Player.setResult("The Cheater player is attacking on " + l_AttackCountry.getCountryName());
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
        try {
            l_ReturnCountry = d_Player.getCountryList().get(rand.nextInt(d_Player.getCountryList().size()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        d_Player.setResult("The Cheater player is defending from " + l_ReturnCountry.getCountryName());
        return l_ReturnCountry;
    }

    /**
     * This is an overridden method to create an order. The cheater player can issue
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
        int l_rndOrder = rand.nextInt(2);
        Order l_returnOrder = null;

        switch (l_rndOrder) {
            case 0:
                Country l_DefendCountry1 = toDefend();
                d_Leb.setResult("in cheater the armies are deployed to -" + l_DefendCountry1.getCountryName());
                l_returnOrder = new Deploy(d_Player, l_DefendCountry1,
                        Math.max(rand.nextInt(d_Player.getPlayerArmies()), 2));
                break;

            case 1:
                ArrayList<Country> l_Countries = toAttack();
                if (l_Countries.get(0).getNoOfArmies() > 1) {
                    d_Leb.setResult("in cheater defending country - " + l_Countries.get(0).getCountryName()
                            + " Attacking country - " + l_Countries.get(1).getCountryName() + " with armies - "
                            + (l_Countries.get(0).getNoOfArmies() - 1));
                    l_returnOrder = new Advance(d_Player, l_Countries.get(0), l_Countries.get(1),
                            l_Countries.get(0).getNoOfArmies() - 1);
                } else {
                    d_Leb.setResult("in cheater the armies are deployed to -" + l_Countries.get(0).getCountryName());
                    l_returnOrder = new Deploy(d_Player, l_Countries.get(0),
                            Math.max(rand.nextInt(d_Player.getPlayerArmies()), 2));
                }

                break;
        }

        d_Leb.setResult("in cheater player order is " + l_returnOrder);
        return l_returnOrder;
    }

    /**
     * This is an overridden method to provide the strategy of the player.
     *
     * @return a String specifying "Cheater" - strategy of this class.
     */
    @Override
    public String strategyName() {
        // TODO Auto-generated method stub
        return "Cheater";
    }

}
