package model.orders;

import java.util.Iterator;

import model.Country;
import model.Order;
import model.Player;

/**
 * Deploy class implements the Order interface and overrides the execute method.
 * The object of this class is created when the player issues Deploy order.
 */
public class Deploy implements Order {

	/**
	 * The deploy order issuing player.
	 */
	Player d_Player;
	/**
	 * The country on which the armies are to be deployed.
	 */
	Country d_Country;
	/**
	 * The number of armies to be deployed.
	 */
	int d_NumArmies;

	/**
	 * Constructor which is created on creation of the object
	 * 
	 * @param p_Player    Player who issues the deploy order.
	 * @param p_Country   Country on which the player issues deploy order.
	 * @param p_NumArmies Number of armies to be deployed on the given country.
	 */
	public Deploy(Player p_Player, Country p_Country, int p_NumArmies) {
		setPlayer(p_Player);
		d_Country = p_Country;
		d_NumArmies = p_NumArmies;
	}

	/**
	 * This method checks for the validity of Deploy order and then assigns number
	 * of armies to the country.
	 */
	@Override
	public void execute() {
		if (isValid()) {
			getPlayer().setPlayerArmies(getPlayer().getPlayerArmies() - d_NumArmies);
			d_Country.setNoOfArmies(d_Country.getNoOfArmies() + d_NumArmies);
		}
	}

	/**
	 * This method checks if the issued deploy order is valid or not.
	 * If the country does not belong to the player, it returns false.
	 * If the player does not have enough number of armies, it returns false.
	 * 
	 * @return return true if valid else false.
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
				getPlayer().setResult("\nThis country " + d_Country.getCountryName() + " doesnot belongs to "
						+ getPlayer().getPlayerName());
				return false;
			}
		} else {
			getPlayer().setResult("\n" + getPlayer().getPlayerName() + " ; you have only "
					+ getPlayer().getPlayerArmies() + " number of armies! Please enter the next order accordingly");
			return false;
		}

	}

	/**
	 * This method returns the player who issues Deploy order.
	 * 
	 * @return Player who issued the deploy order
	 */
	public Player getPlayer() {
		return d_Player;
	}

	/**
	 * This method sets the value of the player.
	 * 
	 * @param d_Player Player who issued the Deploy order.
	 */
	public void setPlayer(Player d_Player) {
		this.d_Player = d_Player;
	}

}
