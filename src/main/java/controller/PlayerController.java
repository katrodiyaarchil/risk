package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

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
import observerpattern.LogEntryBuffer;
import utility.state.GameOver;
import view.CommandPrompt;

/**
 * The Player Controller class controls the activities of all the players at once.
 */
public class PlayerController {
	private ArrayList<Player> d_Players;
	private String d_OrderAcknowledgment = "";
	private CommandPrompt d_CpView;
	private GameModel d_GameModel;
	private LogEntryBuffer d_LEB;
	private HashMap<Integer, String> d_AllCards;
	private Random d_Rand;
	private GameEngine d_GameEngine;

    /**
	 * Constructor of Player controller
	 * 
	 * @param p_GameModel object of Game model class
	 * @param p_Players      list of players
	 * @param p_CpView  object of command prompt for communicating with player
	 */
	PlayerController(GameModel p_GameModel, CommandPrompt p_CpView) {
		d_GameModel = p_GameModel;
		d_Players = d_GameModel.getAllPlayers();
		d_CpView = p_CpView;
		d_LEB = new LogEntryBuffer();
		d_AllCards = new HashMap<>();
		d_GameEngine = p_GameEngine;
		int i = 0;
		d_AllCards.put(i++, "Bomb");
		d_AllCards.put(i++, "Blockade");
		d_AllCards.put(i++, "Negotiate");
		d_AllCards.put(i++, "Airlift");
		d_Rand = new Random();
	}

    /**
	 * The player_issue_order method asks each player to issue an order in a round
	 * robin fashion.
	 * The loop terminates when the armies of all the players are exhausted.
	 * The acknowledgement are passed on to the view.
	 */
	public void playerIssueOrder() {
		ArrayList<Player> l_Players = d_Players;
		HashMap<Player, Boolean> l_CheckArmies = new HashMap<>();
		for (Player l_TempPlayer : l_Players) {
			l_CheckArmies.put(l_TempPlayer, false);
		}
		int l_PlayerListSize = l_Players.size();
		while (l_PlayerListSize > 1) {
			Iterator<Player> l_It = l_Players.iterator();
			while (l_It.hasNext()) {
				Player l_Player = (Player) l_It.next();
				if (!l_Player.getPlayerName().equals("Neutral Player")) {
					if (l_CheckArmies.get(l_Player) == false) {

						String l_StringOrder = JOptionPane
								.showInputDialog(l_Player.getPlayerName() + " : Please Enter Your Order");
						d_LEB.setResult(l_StringOrder);
						if (l_StringOrder.equalsIgnoreCase("quit")) {
							l_CheckArmies.put(l_Player, true);
							--l_PlayerListSize;
						} else {
							l_Player.setOrder(l_StringOrder);
							l_Player.issue_order();

						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * this Method will take inputs from the user and will add or remove player
	 * according
	 * to the inputs provided by the user
	 * 
	 * @param p_Command this is command entered by the player
	 * @param p_Str     this is name entered by the player in the command prompt
	 * @return l_ReturnString returns string acknowledgement based on the added or
	 *         removed players
	 * @throws Exception this is user defined exception based on the add player or
	 *                   remove player method
	 */
	public String editPlayer(String p_Command, String p_Str) throws Exception {
		String[] l_CommandArray = p_Str.split(" ");
		int l_Counter = 1;
		int l_AddCounter = 0;
		int l_RemoveCounter = 0;
		String l_ReturnString = "";
		if (l_CommandArray.length < 3)
			throw new Exception("Please provide valid Parameters to add player");
		while (l_Counter < l_CommandArray.length) {
			if (l_CommandArray[l_Counter].equals("-add")) {
				d_GameModel.addPlayer(l_CommandArray[l_Counter + 1]);
				l_Counter += 2;
				l_AddCounter += 1;
			} else if (l_CommandArray[l_Counter].equals("-remove")) {
				d_GameModel.removePlayer(l_CommandArray[l_Counter + 1]);

				l_Counter += 2;
				l_RemoveCounter += 1;
			} else {
				break;
			}
		}
		if (l_AddCounter > 0) {
			l_ReturnString += "Number of Players Added : " + l_AddCounter + "\n";
		}
		if (l_RemoveCounter > 0) {
			l_ReturnString += "Number of Players Removed : " + l_RemoveCounter + "\n";
		}
		return l_ReturnString;
	}


	/**
	 * This method iterates till the player list doesn't becomes empty. This means
	 * all the orders of all the players are executed.
	 * It works in a round robin fashion. All the players execute there orders one
	 * by one.
	 * The player who's all orders are executed is removed from the list.
	 * The player who has won a battle in this round is assigned a card.
	 * All the players' negotiated players list is made empty.
	 * At the end of this round, we check if a player is declared as a winner.
	 */
	public void playerNextOrder() {
		ArrayList<Player> l_Players = d_Players;
		ArrayList<Player> l_PlayersClone = (ArrayList<Player>) d_Players.clone();
		while (!l_PlayersClone.isEmpty()) {
			Iterator<Player> l_It = l_Players.iterator();
			int l_Flag = 0;
			ArrayList<Player> l_RemovePlayerList = new ArrayList<Player>();
			while (l_It.hasNext()) {
				Player l_Player = (Player) l_It.next();
				if (l_Player.getOrderSize() != 0) {
					Order l_Order = l_Player.next_order();
					if (l_Order.getClass().getName().equals("model.orders.Deploy")) {
						Deploy l_DeployOrder = (Deploy) l_Order;
						l_DeployOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					} else if (l_Order.getClass().getName().equals("model.orders.Advance")) {
						Advance l_AdvanceOrder = (Advance) l_Order;
						l_AdvanceOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					} else if (l_Order.getClass().getName().equals("model.orders.Blockade")) {
						Blockade l_BlockadeOrder = (Blockade) l_Order;
						l_BlockadeOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					} else if (l_Order.getClass().getName().equals("model.orders.Bomb")) {
						Bomb l_BombOrder = (Bomb) l_Order;
						l_BombOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					} else if (l_Order.getClass().getName().equals("model.orders.Airlift")) {
						Airlift l_AirliftOrder = (Airlift) l_Order;
						l_AirliftOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					} else if (l_Order.getClass().getName().equals("model.orders.Negotiate")) {
						Negotiate l_NegotiateOrder = (Negotiate) l_Order;
						l_NegotiateOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment = l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}

				} else {
					l_Flag = 1;
					l_RemovePlayerList.add(l_Player);
				}
			}
			if (l_Flag == 1) {
				for (Player l_TempRemovePlayer : l_RemovePlayerList) {
					l_PlayersClone.remove(l_TempRemovePlayer);
				}
			}
		}
		// Assigning cards to players that have won a battle in this round.
		for (Player l_TempPlayer : d_Players) {
			if (l_TempPlayer.getAtleastOneBattleWon()) {
				int l_cardInteger = d_Rand.nextInt(4);
				l_TempPlayer.setCard(d_AllCards.get(l_cardInteger));
				l_TempPlayer.setAtleastOneBattleWon(false);
			}
		}
		d_CpView.setCommandAcknowledgement("\nOrders are Succesfully Executed!!");
		d_LEB.setResult("\nOrders are Succesfully Executed!!");
		clearNegotiatedPlayerList();
		removePlayerWithNoCountry();
		checkTheWinner();

	}
/**
 * This method is used to remove the player with no countries on its name.
*/
public void removePlayerWithNoCountry() {
	Iterator<Player> l_PlayerIterator = d_Players.iterator();
	while (l_PlayerIterator.hasNext()) {
		Player l_TempPlayer = (Player) l_PlayerIterator.next();
		if (l_TempPlayer.getCountriesSize() <= 0 && !l_TempPlayer.getPlayerName().equals("Neutral Player")) {
			l_PlayerIterator.remove();
		}
	}
}
/**
* This method is used to clear all the individual players' negotiated players
* list after each round of issuing and execution of orders.
*/
public void clearNegotiatedPlayerList() {

	for (Player l_TempPlayer : d_Players) {

		l_TempPlayer.removeNegotiatedPlayer();

	}

}

	/**
	 * This method is used to check if one player owns all the countries of the map
	 * and hence can be declared as the winner of the game.
	 */
	public void checkTheWinner() {
		ArrayList<Country> l_CountryList = d_GameModel.getMap().getCountryList();
		Iterator<Country> itr = l_CountryList.iterator();
		Player l_CheckPlayer = (Player) ((Country) itr.next()).getCountryOwnerPlayer();
		int l_flag = 0;
		while (itr.hasNext()) {
			if (!((Player) ((Country) itr.next()).getCountryOwnerPlayer() == l_CheckPlayer)) {
				l_flag = 1;
				break;
			}
		}
		if (l_flag == 0) {

			d_GameEngine.setPhase(new GameOver(d_GameEngine, d_CpView));
			d_CpView.setCommandAcknowledgement("\n" + l_CheckPlayer.getPlayerName() + " is the winner of the game!");
		}

	}

}