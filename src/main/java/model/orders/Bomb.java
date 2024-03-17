package model.orders;

import model.Country;
import model.Order;
import model.Player;

/**
 * The Bomb class represents a bomb order in the game.
 * When executed, it reduces the number of armies in a target country by half and deducts the appropriate armies from the owner's total.
 */
public class Bomb implements Order {
    private Player d_Player;
    private Player d_PlayerBombed;
    private Country d_Country;
    private int d_NoOfArmies;

    /**
     * Constructor which is called when a new object of Bomb class is created.
     * 
     * @param p_Player      The player who issues the bomb order.
     * @param p_TempCountry The country on which the bomb to be dropped, if valid.
     */
    public Bomb(Player p_Player, Country p_TempCountry) {
        this.setPlayer(p_Player);
        this.d_Country = p_TempCountry;
        setBombedPlayer();
    }

    /**
     * This method sets the player whose country is going to be bombed.
     */
    private void setBombedPlayer() {
        this.d_PlayerBombed = d_Country.getCountryOwnerPlayer();
    }

    /**
     * This method checks the validity of the Bomb order and execute if it passes.
     * If the order is valid, it will half the number of armies on the country which
     * is bombed.
     * This method removes the bomb card from the player's card list so that can't
     * be used again.
     */
    @Override
    public void execute() {
        if (isValid()) {
            d_NoOfArmies = d_Country.getNoOfArmies() / 2;
            int l_NoOfArmiesReduced = d_Country.getNoOfArmies() - d_NoOfArmies;
            d_Country.setNoOfArmies(d_Country.getNoOfArmies() - d_NoOfArmies);
            d_PlayerBombed.setPlayerArmies(d_PlayerBombed.getPlayerArmies() - l_NoOfArmiesReduced);
            getPlayer().removeCard("Bomb");
        }
    }

    /**
     * This method checks the validity of the Bomb order.
     * It returns false if coutry is owned by negotiated player, player does not
     * have bomb card.
     * It also returns false if country is owned by the same player who issues the
     * bomb order.
     * it returns false if country to be bombed is not a neighbour country.
     * 
     * @return true if Bomb order is valid, else false.
     */
    public boolean isValid() {
        if (getPlayer().getNegotiatedPlayerList().size() > 0) {
            getPlayer().setResult("Player is a negotiated player");
            return false;
        }
        if (!getPlayer().getCardList().contains("Bomb")) {
            getPlayer().setResult("Player does not have a bomb card");
            return false;
        }

        if (getPlayer().getCountryList().contains(d_Country)) {
            getPlayer().setResult("Player cannot bomb its own country");
            return false;
        }
        int l_Flag = 0;
        for (Country l_Country : getPlayer().getCountryList()) {
            if (l_Country.getBorder().contains(d_Country.getCountryName())) {
                l_Flag = 1;
            }

        }

        if (l_Flag == 0) {
            getPlayer().setResult("The bombing country is not a neighbour of player");
            return false;
        } else {
            getPlayer().setResult("The country is bombed");
            return true;
        }

    }
    
    /**
     * This method returns the player who issues the Bomb order
     * 
     * @return The player who issues the bomb order
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * Sets the player who issues the bomb order.
     * 
     * @param d_Player Player who issues the bomb order.
     */
    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }
}
