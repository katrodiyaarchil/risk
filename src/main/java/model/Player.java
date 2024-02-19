package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

/**
 * The Player class represents a player in the game.
 * It contains information about the player's name, ID, color, armies, countries, continents,
 * orders, and game model.
 */

public class Player {
	private String d_PlayerName = "";
	private int d_PlayerId;
	private String d_PlayerColor = "";
	private int d_Armies;
	private int d_TempArmies;
	private int d_ResultInteger;
	private ArrayList<Country> d_Countries = new ArrayList<Country>();
	private Queue<Order> d_Order = new LinkedList<Order>();
	private ArrayList<Continent> d_Continents = new ArrayList<Continent>();
	private String d_Result = "";
	private String d_StringOrder = "";
	private GameModel d_GameModel;

	/**
	 * default constructor of Player class
	 */

	public Player() {
	}

	/**
     * Constructor for Player class with name and game model.
     *
     * @param p_PlayerName The name of the player.
     * @param p_GameModel  The game model.
     */

	public Player(String p_PlayerName, GameModel p_GameModel) {
		this.d_PlayerName = p_PlayerName;
		this.d_GameModel = p_GameModel;
	}

	/**
     * Package-private constructor for Player class with name, ID, and color.
     *
     * @param p_PlayerName  The name of the player.
     * @param p_PlayerId    The ID of the player.
     * @param p_PlayerColor The color of the player.
     */

	Player(String p_PlayerName, int p_PlayerId, String p_PlayerColor) {
		d_PlayerName = p_PlayerName;
		d_PlayerId = p_PlayerId;
		d_PlayerColor = p_PlayerColor;
	}
	/**
     * Adds a country to the player's list of countries.
     *
     * @param p_Country The country to add.
     */
	public void addCountry(Country p_Country) {
		d_Countries.add(p_Country);
	}

    /**
     * Removes a country from the player's list of countries.
     *
     * @param p_Country The country to remove.
     */
	public void removeCountry(Country p_Country) {
		d_Countries.remove(p_Country);
	}

	 // Getters and setters for player attributes...
	/**
	 * get method for player name
	 * 
	 * @return returns player name
	 */
	public String getPlayerName() {
		return this.d_PlayerName;
	}

	/**
	 * get method for player id
	 * 
	 * @return returns player id
	 */
	public int getPlayerId() {
		return this.d_PlayerId;
	}

	/**
	 * get method for player color
	 * 
	 * @return returns player color
	 */
	public String getPlayerColor() {
		return this.d_PlayerColor;
	}

	/**
	 * set method for player id
	 * 
	 * @param p_PlayerId player id of player
	 */
	public void setPlayerId(int p_PlayerId) {
		this.d_PlayerId = p_PlayerId;
	}

	/**
	 * set method for player color
	 * 
	 * @param p_PlayerColor Player Color of the player
	 */
	public void setPlayerColor(String p_PlayerColor) {
		this.d_PlayerColor = p_PlayerColor;
	}

	/**
	 * set method for allocating armies to player
	 * 
	 * @param p_Armies Armies off the player
	 */
	public void setPlayerArmies(int p_Armies) {
		this.d_Armies = p_Armies;
		this.d_TempArmies = p_Armies;
	}

	/**
	 * get method for armies of player
	 * 
	 * @return returns armies of players
	 */
	public int getPlayerArmies() {
		return this.d_Armies;
	}

	/**
	 * set Continent list for the player. It consists of only those continent
	 * objects whose all countries belong to this player.
	 */
	public void setContinentsList() {
		ArrayList<Continent> l_MapContinents = d_GameModel.getSelectedMap().getContinentList();
		for (Continent l_MapContinent : l_MapContinents) {
			int l_Flag = 0;
			outerloop: for (Country l_CountryOfContinent : l_MapContinent.getCountryList()) {
				for (Country l_CountryOfPlayer : d_Countries) {
					if (!(l_CountryOfPlayer == l_CountryOfContinent)) {
						l_Flag = 1;
						break outerloop;
					}
				}
			}
			if (l_Flag == 0) {
				d_Continents.add(l_MapContinent);
			}
		}
	}
	/**
	 * get method for continent list of the player
	 * 
	 * @return returns the list of continents
	 */
	public ArrayList<Continent> getContinentList() {
		return d_Continents;
	}

	/**
	 * The getResult return the result whether the order was added to the order list
	 * or not to the Player controller.
	 * 
	 * @return returns the result of issue order
	 */
	public String getResult() {
		return this.d_Result;
	}

	/**
	 * set method for result of issue order
	 * 
	 * @param p_Result the result after issuing an order
	 */
	public void setResult(String p_Result) {
		this.d_Result = p_Result;
	}

	/**
	 * The setOrder method gets the order in string format for that player.
	 * 
	 * @param p_Order Order entered by the player
	 */
	public void setOrder(String p_Order) {
		this.d_StringOrder = p_Order;
	}

	/**
	 * get method for the size of the country list of the player
	 * 
	 * @return size of the country list
	 */
	public int getCountriesSize() {
		return this.d_Countries.size();
	}

	/**
	 * Get method for the Country list of the player
	 * 
	 * @return returns the country list of the player
	 */
	public ArrayList<Country> getCountryList() {
		return this.d_Countries;
	}

	/**
	 * get method for the Order list size of the player
	 * 
	 * @return size of the order list
	 */
	public int getOrderSize() {
		return this.d_Order.size();
	}

	/**
	 * get method for the result Integer. It is a flag which defines the result of
	 * the issue order method
	 * 
	 * @return integer set for determining the result of issue order method
	 */
	public int getResultInteger() {
		return this.d_ResultInteger;
	}

	/**
	 * The issue order method checks the order issued by the player whether the
	 * country it is asking for is in its country list or not
	 * and whether it has sufficient armies and it sets the result accordingly.
	 * If the country is in the country list and if the player has sufficient armies
	 * than the order is added to its order list.
	 * There are 5 cases
	 * <ul>
	 * <li>when the result integer is 1 - The number of armies asked to deploy on a
	 * country in the list of the player is less than the number of armies with the
	 * players.The order is added into the Order List and the armies are subtracted
	 * from the armies of the player.
	 * <li>when the result integer is 2 - When the number of armies after
	 * successfully adding the order in the list becomes zero. The order is added to
	 * the Order list and the armies are subtracted from the armies of the player.
	 * <li>when the result integer is 3 - When the country asked to deploy armies
	 * doesn't belongs to the player.The order is not added to the order list.
	 * <li>when the result integer is 4 - When the number of armies asked to deploy
	 * is more than the number of armies with the player.The order is not added to
	 * the order list.
	 * <li>when the result integer is 5 - When incorrect command is entered.The
	 * order is not added to the order list.
	 * </ul>
	 */
	public void issue_order() {
		int l_Flag = 0;
		d_ResultInteger = 0;
		String[] l_StringList = d_StringOrder.split(" ");
		if (l_StringList[0].equals("deploy")) {
			if (Integer.parseInt(l_StringList[2]) <= d_Armies) {
				Iterator<Country> l_It = d_Countries.iterator();
				while (l_It.hasNext()) {
					Country l_TempCountry = (Country) l_It.next();
					if (l_StringList[1].equals(l_TempCountry.getCountryName())) {
						l_Flag = 1;
						break;
					}
				}
				if (l_Flag == 1) {
					d_Armies -= Integer.parseInt(l_StringList[2]);
					d_Order.add(new Order(d_StringOrder, d_GameModel));
					d_ResultInteger = 1;
					setResult("\norder " + d_StringOrder + " added to list of " + d_PlayerName);
					if (d_Armies == 0) {
						d_ResultInteger = 2;
						setResult("\n" + d_PlayerName
								+ " : Your armies have become zero now!!. You will not be able to issue an order");
					}
				} else {
					d_ResultInteger = 3;
					setResult("\nThis country " + l_StringList[1] + " doesnot belongs to " + d_PlayerName);
				}
			} else {
				d_ResultInteger = 4;
				setResult("\n" + d_PlayerName + " ; you have only " + d_Armies
						+ " number of armies! Please enter the next order accordingly");
			}
		} else {
			d_ResultInteger = 5;
			setResult("\n" + d_PlayerName + "Please enter Valid Command next time!");
		}
	}

	/**
	 * This method removes the first order in the queue Order list
	 * 
	 * @return returns the first order in the Order List
	 */
	public Order next_order() {
		return d_Order.remove();
	}
}
