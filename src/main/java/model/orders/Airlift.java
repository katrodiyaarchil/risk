package model.orders;

import model.Country;
import model.Order;
import model.Player;

/**
 * Airlift class implements the Order interface and overrides the execute
 * method.
 * The object of this method is created when the player issues airlift order.
 */
public class Airlift implements Order {
    public Player d_Player;
    Country d_SourceCountry, d_TargetCountry;
    int d_NumArmies;

    /**
     * Constructor of the class which is called when the new object of Airlift class
     * is created.
     * 
     * @param p_Player        Player who issues the airlift order.
     * @param p_SourceCountry Country from which the airlift of armies should
     *                        happen.
     * @param p_TargetCountry Country to which armies to be sent.
     * @param p_NumArmies     Number of armies to be airlift from one country to
     *                        another.
     */
    public Airlift(Player p_Player, Country p_SourceCountry, Country p_TargetCountry, int p_NumArmies) {
        d_Player = p_Player;
        d_SourceCountry = p_SourceCountry;
        d_TargetCountry = p_TargetCountry;
        d_NumArmies = p_NumArmies;
    }


    /**
     * This method airlifts armies from one country to another country of the same
     * player if order is valid.
     * This method also removes the card airlift.
     */
    @Override
    public void execute() {
        if (isValid()) {
            d_SourceCountry.setNoOfArmies(d_SourceCountry.getNoOfArmies() - d_NumArmies);
            d_TargetCountry.setNoOfArmies(d_TargetCountry.getNoOfArmies() + d_NumArmies);
        }
        d_Player.removeCard("Airlift");
    }

    /**
     * This method checks for the validity of the Airlift order.
     * Returns false if the source and target countries are same, player doesn't
     * have airlift card.
     * Return false if source country is not left with atleast one army.
     * 
     * @return true if valid, else false.
     */

    public boolean isValid() {
        if (!d_Player.getCardList().contains("Airlift")) {
            d_Player.setResult("Player does not have a Airlift card");
            return false;
        }
        if (d_SourceCountry == d_TargetCountry) {
            d_Player.setResult("The source country and target country cannot be same!");
            return false;
        } else if (d_SourceCountry.getNoOfArmies() - d_NumArmies < 1) {
            d_Player.setResult("The source country should be left with atleast one army!");
            return false;
        } else {
            if (d_Player.getCountryList().contains(d_SourceCountry)
                    && d_Player.getCountryList().contains(d_TargetCountry)) {
                d_Player.setResult("The source country and target country belong to the same player");
                return true;
            } else {
                d_Player.setResult("You can only airlift armies to your own countries. "
                        + d_TargetCountry.getCountryName() + " does not belongs to " + d_Player.getPlayerName());
                return false;
            }
        }
    }
}