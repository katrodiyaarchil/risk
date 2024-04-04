package strategypattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import model.Country;
import model.GameModel;
import model.Order;
import model.Player;
import model.orders.Advance;
import model.orders.Airlift;
import model.orders.Blockade;
import model.orders.Bomb;
import model.orders.Deploy;
import model.orders.Negotiate;

/**
 * This Strategy class belongs to the Human Player. It encapsulates the behavior
 * of a Human Player.
 * This strategic players issues all types of orders on its own.
 *
 */
public class HumanPlayerStrategy extends Strategy implements Serializable {
    /**
     * GameModel new object to get the current map.
     */
    private GameModel d_GameModel;
    /**
     * Player reference of this strategy
     */
    Player d_Player;
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
     * The constructor initializes the human player and the game model new object.
     *
     * @param p_Player       The Player whose strategy is Human.
     * @param p_GameModel The Reference of GamemodelNew to get the Map on which
     *                       the match is to be played.
     */
    public HumanPlayerStrategy(Player p_Player, GameModel p_GameModel) {
        this.d_GameModel = p_GameModel;
        d_Player = p_Player;
        System.out.println("Human");
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
     * This is the getter method for the Checkarmies hashmap.
     *
     * @return The Hashmap - Checkarmies
     */
    public HashMap<Player, Boolean> getCheckArmies() {
        return d_CheckArmies;
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
     * This is an overridden attack method. The human player enters the country of
     * its own choice.
     */
    @Override
    public ArrayList<Country> toAttack() {
        return null;
    }

    /**
     * This is an overridden defend method. The human player enters the country it
     * wants to deploy its armies to or attack from.
     */
    @Override
    public Country toDefend() {
        return null;
    }

    /**
     * This in an overridden method to create an order. Based in the input order
     * string entered by the human player. It creates a corresponding order.
     * <ol>
     * <li>A deploy keyword would create an object of a Deploy order.</li>
     * <li>An advance keyword would create an object of a Advance order.</li>
     * <li>A bomb keyword would create an object of a Bomb order.</li>
     * <li>A blockade keyword would create an object of a Blockade order.</li>
     * <li>An airlift keyword would create an object of a Airlift order.</li>
     * <li>A negotiate keyword would create an object of a Negotiate order.</li>
     * </ol>
     * Other than these 5 orders, if a player enters the command "quit", then the
     * value of this player in checkArmies hashmap is changed to true
     * and the decreasePlayerListSize boolean is also set to true.
     * Or if a player enters the command "savegame", then the current game is saved
     * in a txt filename mentioned by the player.
     *
     * @return It returns the order object issued.
     */
    @Override
    public Order createOrder() {
        // TODO Auto-generated method stub
        String l_StringOrder = JOptionPane.showInputDialog(d_Player.getPlayerName() + " : Please Enter Your Order");
        d_decreasePlayerListSize = false;
        System.out.println("in human player createorder");
        if ("quit".equalsIgnoreCase(l_StringOrder)) {
            System.out.println("in human player the command is quit");
            d_CheckArmies.put(d_Player, true);
            d_decreasePlayerListSize = true;
            // --d_PlayerListSize;
        }

        else {
            int l_Flag = 0;
            String[] l_StringList = l_StringOrder.split(" ");
            String l_OrderType = l_StringList[0];

            d_Leb.setResult("in human player the command is not quit " + l_StringOrder);
            switch (l_OrderType) {

                case "deploy":
                    if (l_StringList.length != 3) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    int l_NumArmies = Integer.parseInt(l_StringList[2]);
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
                            return new Deploy(d_Player, l_TempCountry, l_NumArmies);
                        }
                    }
                    break;
                case "advance":
                    if (l_StringList.length != 4) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    int l_NumArmies1 = Integer.parseInt(l_StringList[3]);
                    Country l_SourceCountry = null;
                    Country l_TargetCountry = null;
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
                            l_SourceCountry = l_TempCountry;
                            break;
                        }
                    }
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[2])) {
                            l_TargetCountry = l_TempCountry;
                            break;
                        }
                    }
                    return new Advance(d_Player, l_SourceCountry, l_TargetCountry, l_NumArmies1);
                case "bomb":
                    if (l_StringList.length != 2) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
                            return new Bomb(d_Player, l_TempCountry);
                        }
                    }
                    break;
                case "blockade":
                    if (l_StringList.length != 2) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
                            return new Blockade(d_Player, l_TempCountry);

                        }
                    }
                    break;
                case "airlift":
                    if (l_StringList.length != 4) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    int l_NumArmies2 = Integer.parseInt(l_StringList[3]);
                    Country l_SourceCountry1 = null;
                    Country l_TargetCountry1 = null;

                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[1])) {
                            l_SourceCountry1 = l_TempCountry;
                            break;
                        }
                    }
                    for (Country l_TempCountry : d_GameModel.getMap().getCountryList()) {
                        if (l_TempCountry.getCountryName().equals(l_StringList[2])) {
                            l_TargetCountry1 = l_TempCountry;
                            break;
                        }
                    }
                    return new Airlift(d_Player, l_SourceCountry1, l_TargetCountry1, l_NumArmies2);
                case "negotiate":
                    if (l_StringList.length != 2) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    for (Player l_TempPlayer : d_GameModel.getAllPlayers()) {
                        if (l_TempPlayer.getPlayerName().equals(l_StringList[1])) {
                            return new Negotiate(d_Player, l_TempPlayer);
                        }
                    }

                    break;

                case "savegame":
                    if (l_StringList.length != 2) {
                        d_Leb.setResult("Please enter valid number of parameters");
                        break;
                    }
                    d_GameModel.saveGame(l_StringList[1]);
                    d_Leb.setResult("Saving the current Game");
                    d_Player.setSaveGame(true);
                    return null;
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * This is an overridden method to provide the strategy of the player.
     *
     * @return a String specifying "Human" - strategy of this class.
     */
    @Override
    public String strategyName() {
        // TODO Auto-generated method stub
        return "Human";
    }

}
