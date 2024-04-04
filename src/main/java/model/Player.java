package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import strategypattern.Strategy;

/**
 * The Player class represents the actual player participating in the game.
 */
public class Player implements Serializable {
    /**
     * This string represents the name of the player
     */
    private String d_PlayerName = "";
    /**
     * This integer represents the id of the player
     */
    private int d_PlayerId;
    /**
     * This integer represents the number of armies
     */
    private int d_Armies;
    /**
     * this integer represents the temporary armies given to the player
     */
    private int d_TempArmies;
    /**
     * This Array list consists of all the countries of the player
     */
    private ArrayList<Country> d_Countries = new ArrayList<Country>();
    /**
     * The Queue represents the queue of players order
     */
    private Queue<Order> d_Order = new LinkedList<Order>();
    /**
     * This Array list consists of all the continents
     */
    private ArrayList<Continent> d_Continents = new ArrayList<Continent>();
    /**
     * This string is used to store the result of issue order
     */
    private String d_Result = "";
    /**
     * This string represents the name of order
     */
    private String d_StringOrder = "";
    /**
     * This is the gamemodelnew objects
     */
    private GameModel d_GameModelNew;
    /**
     * This ArrayList represents all the cards given to players
     */
    private ArrayList<String> d_Cards = new ArrayList<String>();
    /**
     * This list has all the player objects of negotiated players
     */
    private ArrayList<Player> d_NegotiatedPlayers = new ArrayList<Player>();
    /**
     * Boolean to check if a player has won atleast one battle to alot him a card
     */
    private boolean d_AtleastOneBattleWon = false;
    /**
     * It is the object of Strategy Class
     */
    private Strategy d_PlayerStrategy;
    /**
     * Boolean to check if a game should be saved or not
     */
    private boolean d_SaveGame = false;

    /**
     * default constructor of Player class
     */
    public Player() {
    }

    /**
     * constructor of Player class with only player name as the parameters
     *
     * @param p_PlayerName   Name of the player
     * @param p_GameModelNew The GameModelNew reference to access the map
     */
    public Player(String p_PlayerName, GameModel p_GameModelNew) {
        this.d_PlayerName = p_PlayerName;
        this.d_GameModelNew = p_GameModelNew;
    }

    /**
     * addCountry method adds the given country to the player's country list.
     *
     * @param p_Country country object
     */
    public void addCountry(Country p_Country) {
        d_Countries.add(p_Country);
    }

    /**
     * removeCountry removes the given country from the player's country list
     *
     * @param p_Country Name of the country to be removed
     */
    public void removeCountry(Country p_Country) {
        d_Countries.remove(p_Country);
    }

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
        return this.d_GameModelNew;
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
     * This method returns the boolean which is set when game saved
     *
     * @return true if savedgame else false
     */
    public boolean getSaveGame() {
        return this.d_SaveGame;
    }

    /**
     * this method sets the flag for savedgame.
     *
     * @param p_Value value to be set
     */
    public void setSaveGame(boolean p_Value) {
        this.d_SaveGame = p_Value;
    }

    /**
     * set Continent list for the player. It consists of only those continent
     * objects whose all countries belong to this player.
     */
    public void setContinentsList() {
        ArrayList<Continent> l_MapContinents = d_GameModelNew.getMap().getContinentList();
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
        Order order;
        System.out.println("in player class");
        System.out.println(d_PlayerStrategy);
        order = d_PlayerStrategy.createOrder();
        if (order != null) {
            d_Order.add(order);
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

    /**
     * This method sets the strategy for the player when new player is created.
     *
     * @param p_PlayerStrategy strategy type of the player.
     */
    public void setPlayerStrategy(Strategy p_PlayerStrategy) {
        this.d_PlayerStrategy = p_PlayerStrategy;
    }

    /**
     * This method returns the player strategy type.
     *
     * @return type of player strategy
     */
    public Strategy getPlayerStrategy() {
        return this.d_PlayerStrategy;
    }
}