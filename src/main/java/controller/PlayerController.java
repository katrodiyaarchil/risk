package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;
import java.util.Iterator;
import java.util.Map.Entry;

import model.GameModel;
import model.Order;
import model.Player;
import view.CommandPrompt;

/**
 * The Player Controller class controls the activities of all the players at once.
 */
public class PlayerController {
	private ArrayList<Player> d_Players;
	private String d_OrderAcknowledgment = "";
	private CommandPrompt d_CpView;
	private GameModel d_GameModel;

    /**
	 * Constructor of Player controller
	 * 
	 * @param p_GameModel object of Game model class
	 * @param p_CpView  object of command prompt for communicating with player
	 */
	PlayerController(GameModel p_GameModel, CommandPrompt p_CpView) {
		d_GameModel = p_GameModel;
		d_Players = d_GameModel.getAllPlayers();
		d_CpView = p_CpView;
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
		while (l_PlayerListSize > 0) {
			Iterator<Player> l_It = l_Players.iterator();
			while (l_It.hasNext()) {
				Player l_Player = (Player) l_It.next();
				if (l_Player.getPlayerArmies() > 0) {
					d_OrderAcknowledgment = "\n" + l_Player.getPlayerName() + " Enter deploy order";
					d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
					String l_StringOrder = JOptionPane
							.showInputDialog(l_Player.getPlayerName() + " : Please Enter Your Deploy Order");
					l_Player.setOrder(l_StringOrder);
					l_Player.issue_order();
					String l_Result = l_Player.getResult();
					int l_ResultInteger = l_Player.getResultInteger();
					d_OrderAcknowledgment = l_Result;
					d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
				} else {
					Set<Entry<Player, Boolean>> l_Check = l_CheckArmies.entrySet();
					for (Entry<Player, Boolean> l_E : l_Check) {
						if (l_E.getKey().getPlayerArmies() == 0 && !l_E.getValue()) {
							--l_PlayerListSize;
							l_E.setValue(true);
						}
					}
				}
			}
		}
	}

    /**
	 * This method iterates till the player list doesn't becomes empty. This means
	 * all the orders of all the players are executed.
	 * It works in a round robin fashion. All the players execute there orders one
	 * by one.
	 * The player who's all orders are executed is removed from the list.
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
					System.out.println(l_Order.getOrder());
					l_Order.execute();
                    d_OrderAcknowledgment = l_Order.getExecuteResult();
					d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
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
		d_CpView.setCommandAcknowledgement("\nOrders are Succesfully Executed.....!!!");
	}
}
