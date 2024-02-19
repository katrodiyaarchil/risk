package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

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


	public Player() {
	}

	public Player(String p_PlayerName, GameModel p_GameModel) {
		this.d_PlayerName = p_PlayerName;
		this.d_GameModel = p_GameModel;
	}

	Player(String p_PlayerName, int p_PlayerId, String p_PlayerColor) {
		d_PlayerName = p_PlayerName;
		d_PlayerId = p_PlayerId;
		d_PlayerColor = p_PlayerColor;
	}

	public void addCountry(Country p_Country) {
		d_Countries.add(p_Country);
	}

	public void removeCountry(Country p_Country) {
		d_Countries.remove(p_Country);
	}

	public String getPlayerName() {
		return this.d_PlayerName;
	}

	public int getPlayerId() {
		return this.d_PlayerId;
	}

	public String getPlayerColor() {
		return this.d_PlayerColor;
	}

	public void setPlayerId(int p_PlayerId) {
		this.d_PlayerId = p_PlayerId;
	}

	public void setPlayerColor(String p_PlayerColor) {
		this.d_PlayerColor = p_PlayerColor;
	}

	public void setPlayerArmies(int p_Armies) {
		this.d_Armies = p_Armies;
		this.d_TempArmies = p_Armies;
	}

	public int getPlayerArmies() {
		return this.d_Armies;
	}

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

	public ArrayList<Continent> getContinentList() {
		return d_Continents;
	}

	public String getResult() {
		return this.d_Result;
	}

	public void setResult(String p_Result) {
		this.d_Result = p_Result;
	}

	public void setOrder(String p_Order) {
		this.d_StringOrder = p_Order;
	}

	public int getCountriesSize() {
		return this.d_Countries.size();
	}

	public ArrayList<Country> getCountryList() {
		return this.d_Countries;
	}

	public int getOrderSize() {
		return this.d_Order.size();
	}

	public int getResultInteger() {
		return this.d_ResultInteger;
	}

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

	public Order next_order() {
		return d_Order.remove();
	}
}
