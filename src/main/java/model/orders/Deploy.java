package model.orders;

import java.util.Iterator;
import model.Country;
import model.Order;
import model.Player;

public class Deploy implements Order {

    private Player d_Player;
    private Country d_Country;
    private int d_NumArmies;

    public Deploy(Player p_Player, Country p_Country, int p_NumArmies) {
        setPlayer(p_Player);
        d_Country = p_Country;
        d_NumArmies = p_NumArmies;
    }

    @Override
    public void execute() {
        if (isValid()) {
            getPlayer().setPlayerArmies(getPlayer().getPlayerArmies() - d_NumArmies);
            d_Country.setNoOfArmies(d_Country.getNoOfArmies() + d_NumArmies);
        }
    }

    public Player getPlayer() {
        return d_Player;
    }

    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }

    public boolean isValid() {
        return false;
    }
}
