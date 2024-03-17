
package model.orders;

import java.util.Iterator;
import model.Country;
import model.GameModel;
import model.Order;
import model.Player;

public class Blockade implements Order {
	private Player d_Player;
	private Country d_Country;
	private GameModel d_GameObj;

	public Blockade(Player p_Player, Country p_TempCountry) {
		setPlayer(p_Player);
		d_Country = p_TempCountry;
		this.d_GameObj = getPlayer().getGameModel();
	}


	@Override
	public void execute() {
		if (isValid()) {
			d_Country.setNoOfArmies(d_Country.getNoOfArmies() * 3);
			getPlayer().getCountryList().remove(d_Country);
			for (Player l_Player : d_GameObj.getAllPlayers()) {
				if (l_Player.getPlayerName().equals("Neutral Player")) {
					d_Country.setCountryOwnerPlayer(l_Player);
					l_Player.setPlayerArmies(l_Player.getPlayerArmies() + d_Country.getNoOfArmies());
					l_Player.addCountry(d_Country);
				}
			}
		}
		getPlayer().removeCard("Blockade");
	}


	public boolean isValid() {
		int l_Flag = 0;
		if (!getPlayer().getCardList().contains("Blockade")) {
			getPlayer().setResult("Player doesn't have a blockade card");
			return false;
		}
		Iterator<Country> l_It = getPlayer().getCountryList().iterator();
		while (l_It.hasNext()) {
			Country l_TempCountry = (Country) l_It.next();
			if (d_Country == l_TempCountry) {
				l_Flag = 1;
				break;
			}
		}
		if (l_Flag == 1) {
			getPlayer().setResult("The blockade was successfull");
			return true;
		} else {
			getPlayer().setResult("\nThis country " + d_Country.getCountryName() + " doesn't belongs to "
					+ getPlayer().getPlayerName());
			return false;
		}
	}

	public Player getPlayer() {
		return d_Player;
	}

	public void setPlayer(Player d_Player) {
		this.d_Player = d_Player;
	}
}

