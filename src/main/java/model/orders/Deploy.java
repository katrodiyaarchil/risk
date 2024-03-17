package model.orders;

import java.util.Iterator;
import model.Country;
import model.Order;
import model.Player;

/**
 * Represents a Deploy order, where a player deploys armies to a specific
 * country.
 */
public class Deploy implements Order {

    private Player d_Player;
    private Country d_Country;
    private int d_NumArmies;

    /**
     * Constructor for Deploy order.
     * 
     * @param p_Player    The player executing the deploy order.
     * @param p_Country   The country to deploy armies to.
     * @param p_NumArmies The number of armies to deploy.
     */
    public Deploy(Player p_Player, Country p_Country, int p_NumArmies) {
        setPlayer(p_Player);
        d_Country = p_Country;
        d_NumArmies = p_NumArmies;
    }

    /**
     * Executes the Deploy order.
     */
    @Override
    public void execute() {
        if (isValid()) {
            getPlayer().setPlayerArmies(getPlayer().getPlayerArmies() - d_NumArmies);
            d_Country.setNoOfArmies(d_Country.getNoOfArmies() + d_NumArmies);
        }
    }

    /**
     * Gets the player executing the deploy order.
     * 
     * @return The player.
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Sets the player executing the deploy order.
     * 
     * @param d_Player The player.
     */
    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }

    /**
     * Checks if the Deploy order is valid.
     * 
     * @return True if valid, false otherwise.
     */
    public boolean isValid() {
        int l_Flag = 0;
        if (d_NumArmies <= getPlayer().getPlayerArmies()) {
            Iterator<Country> l_It = getPlayer().getCountryList().iterator();
            while (l_It.hasNext()) {
                Country l_TempCountry = (Country) l_It.next();
                if (d_Country == l_TempCountry) {
                    l_Flag = 1;
                    break;
                }
            }
            if (l_Flag == 1) {
                getPlayer().setResult("\norder deploy " + d_Country.getCountryName() + " " + d_NumArmies
                        + " added to list of " + getPlayer().getPlayerName());
                return true;
            } else {
                getPlayer().setResult("\nThis country " + d_Country.getCountryName() + " does not belong to "
                        + getPlayer().getPlayerName());
                return false;
            }
        } else {
            getPlayer().setResult("\n" + getPlayer().getPlayerName() + " ; you have only "
                    + getPlayer().getPlayerArmies() + " number of armies! Please enter the next order accordingly");
            return false;
        }
    }
}
