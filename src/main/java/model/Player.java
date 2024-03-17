package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

import model.orders.Advance;
import model.orders.Airlift;
import model.orders.Bomb;
import model.orders.Blockade;
import model.orders.Deploy;
import model.orders.Negotiate;
/**
 * The Player class represents a player in the game.
 * It contains information about the player's name, ID, color, armies, countries, continents,
 * orders, and game model.
 */

public class Player {
	private String d_PlayerName = "";
	private int d_PlayerId;
	private int d_Armies;
	private ArrayList<Country> d_Countries = new ArrayList<Country>();
	private Queue<Order> d_Order = new LinkedList<Order>();
	private ArrayList<Continent> d_Continents = new ArrayList<Continent>();
	private String d_Result = "";
	private String d_StringOrder = "";
	private GameModel d_GameModel;
	private ArrayList<String> d_Cards = new ArrayList<String>();
	private ArrayList<Player> d_NegotiatedPlayers = new ArrayList<Player>();
	private boolean d_AtleastOneBattleWon = false;

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
	 * set method for player id
	 * 
	 * @param p_PlayerId player id of player
	 */
	public void setPlayerId(int p_PlayerId) {
		this.d_PlayerId = p_PlayerId;
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

	public GameModel getGameModel() {
		return this.d_GameModel;
	}

	/**
	 * This method sets the flag value to true if the player won a battle and false
	 * otherwise.
	 * 
	 * @param p_B a boolean value to determine if player has won a battle or not
	 */
	public void setAtleastOneBattleWon(boolean p_B) {
		this.d_AtleastOneBattleWon = p_B;
	}

	/**
	 * This method returns the battle won boolean
	 * 
	 * @return the boolean value indicating if the player had won a battle or not
	 */
	public boolean getAtleastOneBattleWon() {
		return this.d_AtleastOneBattleWon;
	}

	/**
	 * set Continent list for the player. It consists of only those continent
	 * objects whose all countries belong to this player.
	 */
	public void setContinentsList() {
		ArrayList<Continent> l_MapContinents = d_GameModel.getSelectedMap().getContinentList();
		for (Continent l_MapContinent : l_MapContinents) {
			int l_Flag = 0;
			for (Country l_Country : l_MapContinent.getCountryList()) {
				if (!d_Countries.contains(l_Country)) {
					l_Flag = 1;
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
	 * get method for the card if the player owns it or not.
	 * 
	 * @param p_TypeOfCard the string that indicates the type of card
	 * @return true if the card type exists in the list of player.
	 */
	public boolean getCard(String p_TypeOfCard) {
		return d_Cards.contains(p_TypeOfCard);
	}

	/**
	 * set method for adding the card to the card list belonging to the player
	 * 
	 * @param p_Card the Card object that belongs to the player
	 */
	public void setCard(String p_Card) {
		d_Cards.add(p_Card);
	}

	/**
	 * This method removes given card name from player's card list
	 * 
	 * @param p_Card The card to be removed from player card list
	 */
	public void removeCard(String p_Card) {
		d_Cards.remove(p_Card);
	}

	/**
	 * This method returns the Player's class list
	 * 
	 * @return The player's card list
	 */
	public ArrayList<String> getCardList() {
		return d_Cards;
	}

	/**
	 * This method adds the player that has strike a deal with this player
	 * 
	 * @param p_NegotiatedPlayer Player to be added in Negotiated player list.
	 */
	public void addNegotiatedPlayer(Player p_NegotiatedPlayer) {
		d_NegotiatedPlayers.add(p_NegotiatedPlayer);
	}

	/**
	 * This method returns the list of negotiated players of this player
	 * 
	 * @return The list of negotiated players of this player
	 */
	public ArrayList<Player> getNegotiatedPlayerList() {
		return d_NegotiatedPlayers;
	}

	/**
	 * This method clears the negotiated players list
	 */
	public void removeNegotiatedPlayer() {
		if (d_NegotiatedPlayers.size() > 0)
			d_NegotiatedPlayers.clear();
	}

	/**
	 * The issue order method checks the order issued by the player.
	 * There are 5 types of orders
	 * <ul>
	 * <li>The deploy order - deploys the number of armies asked by the issuing
	 * player to the asked country.</li>
	 * <li>The Advance order - The issuing players intends to attack a targetCountry
	 * with a certain number of armies from the sourceCountry.</li>
	 * <li>The Bomb order - The issuing player bombs a given country.</li>
	 * <li>The Blockade order - The issuing player makes its own country a neutral
	 * territory by increasing it 3 times.</li>
	 * <li>The AirLift order - The issuing player moves the armies from the source
	 * country to target country which are not neighbors.</li>
	 * <li>The Negotiate order - The issuing player strikes a deal with another
	 * player. so it won't be able to attack each others countries.</li>
	 * </ul>
	 */
	public void issue_order() {
		int l_Flag = 0;
		String[] l_StringList = d_StringOrder.split(" ");
		String l_OrderType = l_StringList[0];
		switch (l_OrderType) {

			case "deploy":
				if (l_StringList.length != 3) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				int l_NumArmies = Integer.parseInt(l_StringList[2]);
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
						d_Order.add(new Deploy(this, l_TempCountry, l_NumArmies));
						break;
					}
				}
				break;
			case "advance":
				if (l_StringList.length != 4) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				int l_NumArmies1 = Integer.parseInt(l_StringList[3]);
				Country l_SourceCountry = null, l_TargetCountry = null;
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
						l_SourceCountry = l_TempCountry;
						break;
					}
				}
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[2])) {
						l_TargetCountry = l_TempCountry;
						break;
					}
				}
				d_Order.add(new Advance(this, l_SourceCountry, l_TargetCountry, l_NumArmies1));
				break;
			case "bomb":
				if (l_StringList.length != 2) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
						d_Order.add(new Bomb(this, l_TempCountry));
						break;
					}
				}
				break;
			case "blockade":
				if (l_StringList.length != 2) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
						d_Order.add(new Blockade(this, l_TempCountry));
						break;
					}
				}
				break;
			case "airlift":
				if (l_StringList.length != 4) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				int l_NumArmies2 = Integer.parseInt(l_StringList[3]);
				Country l_SourceCountry1 = null, l_TargetCountry1 = null;

				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
						l_SourceCountry1 = l_TempCountry;
						break;
					}
				}
				for (Country l_TempCountry : d_GameModel.getSelectedMap().getCountryList()) {
					if (l_TempCountry.getCountryName().equals(l_StringList[2])) {
						l_TargetCountry1 = l_TempCountry;
						break;
					}
				}
				d_Order.add(new Airlift(this, l_SourceCountry1, l_TargetCountry1, l_NumArmies2));
				break;
			case "negotiate":
				if (l_StringList.length != 2) {
					System.out.println("Please enter valid number of parameters");
					break;
				}
				for (Player l_TempPlayer : d_GameModel.getAllPlayers()) {
					if (l_TempPlayer.getPlayerName().equals(l_StringList[1])) {
						d_Order.add(new Negotiate(this, l_TempPlayer));
						break;
					}
				}

				break;

			default:
				break;

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
