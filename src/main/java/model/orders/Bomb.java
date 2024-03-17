package model.orders;

import model.Country;
import model.Order;
import model.Player;


public class Bomb implements Order {
    private Player d_Player;
    private Player d_PlayerBombed;
    private Country d_Country;
    private int d_NoOfArmies;


    public Bomb(Player p_Player, Country p_TempCountry) {
        this.setPlayer(p_Player);
        this.d_Country = p_TempCountry;
        setBombedPlayer();
    }


    private void setBombedPlayer() {
        this.d_PlayerBombed = d_Country.getCountryOwnerPlayer();
    }


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

    public Player getPlayer() {
        return d_Player;
    }


    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }
}
