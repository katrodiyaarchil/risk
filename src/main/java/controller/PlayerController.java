package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import model.Country;
import model.GameModelNew;
import model.Order;
import model.Player;
import model.orders.Advance;
import model.orders.Airlift;
import model.orders.Blockade;
import model.orders.Bomb;
import model.orders.Deploy;
import model.orders.Negotiate;
import observerpattern.LogEntryBuffer;
import strategypattern.HumanPlayerStrategy;
import strategypattern.RandomPlayerStrategy;
import utility.state.GameOver;
import view.CommandPrompt;

/**
 * The Player Controller class controls the activities of all the players at once.
 */
public class PlayerController {
	/**
	 * List of all players
	 */
	private ArrayList <Player> d_Players;
	/**
	 * Winner player for the individual game
	 */
	private Player d_Winner;
	/**
	 * Order acknowlegement string
	 */
	private String d_OrderAcknowledgment="";
	/**
	 * Object of the View
	 */
	private CommandPrompt d_CpView;
	/**
	 * object of the GameModel 
	 */
	private GameModelNew d_GameModel;
	/**
	 * LogEntryBuffer object to log in log file
	 */
	private LogEntryBuffer d_LEB;
	/**
	 * All the cards that can be assigned to player when they win
	 */
	private HashMap<Integer, String> d_AllCards;
	/**
	 * Variable to randomly generate values
	 */
	private Random d_Rand;
	/**
	 * Object of the GameEngine class 
	 */
	private GameEngine d_GameEngine;
	/**
	 * number of rounds used for AI players to end the game
	 */
	private static int d_NumberOfRounds =0;
	/**
	 * Boolean which depicts if the game is saved or not
	 */
	private boolean d_SaveGame=false;
	/**
	 * Constructor of Player controller
	 * @param p_GameModelNew This is the reference of the the game model which is used to access the map.
	 * @param p_CpView object of command prompt for communicating with player
	 * @param p_GameEngine object of Game Engine
	 */
	PlayerController(GameModelNew p_GameModelNew,CommandPrompt p_CpView, GameEngine p_GameEngine) {
		d_GameModel = p_GameModelNew;
		d_Players = d_GameModel.getAllPlayers();
		d_CpView=p_CpView;
		d_LEB=new LogEntryBuffer();
		d_AllCards= new HashMap<>();
		d_GameEngine = p_GameEngine;
		int i=0;
		d_AllCards.put(i++, "Bomb");
		d_AllCards.put(i++, "Blockade");
		d_AllCards.put(i++, "Negotiate");
		d_AllCards.put(i++,"Airlift");
		d_Rand = new Random();
	}
	public int getNumberOfRounds()
	{
		return d_NumberOfRounds;
	}
	
	public boolean getSaved()
	{
		return this.d_SaveGame;
	}

	/**
	 * The player_issue_order method asks each player to issue an order in a round robin fashion and they are added to the order list of that player.
	 * The loop terminates when all the human player enter the keyword "quit".
	 * If the match consists of all AI players then each of them issues only one order.
	 * The acknowledgement are passed on to the view.
	 */
	public void playerIssueOrder() {
		ArrayList <Player> l_Players = d_GameEngine.getGameModel().getAllPlayers();
		HashMap <Player,Boolean> l_CheckArmies = new HashMap<>();
		boolean l_decreasePlayerListSize = false;
		int l_HumanCounter = 0;
		int l_QuitHumanCounter = 0;
		for(Player l_TempPlayer:l_Players) {
			l_CheckArmies.put(l_TempPlayer,false);
		}
		
		for(Player l_TempPlayer : l_Players)
		{
			System.out.println("inside human checking for loop");
			boolean condition = !"Neutral Player".equals(l_TempPlayer.getPlayerName()) && "strategypattern.HumanPlayerStrategy".equals(l_TempPlayer.getPlayerStrategy().toString().split("@")[0]);
			if(condition) {
				System.out.println("the player is a human player");
				l_HumanCounter++;
			}
					System.out.println("outside if condition of human for loop");
		}
		System.out.println("outside human checking for loop");
		int l_PlayerListSize = l_Players.size();
		System.out.println("in player controller player list size - "+l_PlayerListSize);
		
		outerloop:
		while(l_PlayerListSize>1){
			Iterator<Player>l_It = l_Players.iterator();
			
			while(l_It.hasNext()) {
				Player l_Player = (Player)l_It.next(); 
				System.out.println("in player controller player name "+l_Player.getPlayerName());
				System.out.println("in player controller player startegy - "+l_Player.getPlayerStrategy());
				
				
				if(!"Neutral Player".equals(l_Player.getPlayerName())){
					
					System.out.println("in player controller - "+l_Player.getPlayerStrategy().toString().split("@")[0]);
					if("strategypattern.HumanPlayerStrategy".equals(l_Player.getPlayerStrategy().toString().split("@")[0]))
					{
						System.out.println("in player controller before issue order checkarmies-"+l_CheckArmies);
						((HumanPlayerStrategy)l_Player.getPlayerStrategy()).setCheckArmies(l_CheckArmies);
						l_CheckArmies = (HashMap<Player,Boolean>)((HumanPlayerStrategy)l_Player.getPlayerStrategy()).getCheckArmies();

					}
					else if("strategypattern.RandomPlayerStrategy".equals(l_Player.getPlayerStrategy().toString().split("@")[0]))
					{
						System.out.println("in player controller before issue order checkarmies-"+l_CheckArmies);
						((RandomPlayerStrategy)l_Player.getPlayerStrategy()).setCheckArmies(l_CheckArmies);
						l_CheckArmies = (HashMap<Player,Boolean>)((RandomPlayerStrategy)l_Player.getPlayerStrategy()).getCheckArmies();	
					}
				
					if(l_CheckArmies.get(l_Player)==false)
					{

						//d_LEB.setResult(l_StringOrder);
							//l_Player.setOrder(l_StringOrder);
						System.out.println("In playercontroller before issue order");
							l_Player.issue_order();
							if(l_Player.getSaveGame()==true)
							{
								this.d_SaveGame=true;
								break outerloop;
							}
							if("strategypattern.HumanPlayerStrategy".equals(l_Player.getPlayerStrategy().toString().split("@")[0]))
							{
								l_decreasePlayerListSize= ((HumanPlayerStrategy)l_Player.getPlayerStrategy()).getDecreasePlayerListSize();
								l_CheckArmies = (HashMap<Player,Boolean>)((HumanPlayerStrategy)l_Player.getPlayerStrategy()).getCheckArmies();
								System.out.println("in player controller after issue order checkarmies-"+l_CheckArmies);
								if(l_decreasePlayerListSize==true)
								{l_QuitHumanCounter++;}
								
							}
							else if("strategypattern.RandomPlayerStrategy".equals(l_Player.getPlayerStrategy().toString().split("@")[0]))
							{
								l_decreasePlayerListSize= ((RandomPlayerStrategy)l_Player.getPlayerStrategy()).getDecreasePlayerListSize();
								l_CheckArmies = (HashMap<Player,Boolean>)((RandomPlayerStrategy)l_Player.getPlayerStrategy()).getCheckArmies();
								System.out.println("in player controller after issue order checkarmies-"+l_CheckArmies);
							}							
					}
					if(l_decreasePlayerListSize==true)
					{
						System.out.println("inplayercontroller the command is quit");
						l_PlayerListSize--;
					}
					
				}
			}
			if(l_HumanCounter == l_QuitHumanCounter)
			{
				break;
			}
			//check the human player has given quit or  not - use 2 counter 
		}
	}
	/**
	 * <p>
	 * this Method will take inputs from the user and will add or remove player according
	 * to the inputs provided by the user
	 * 
	 * @param p_Command this is command entered by the player
	 * @param p_Str  this is name entered by the player in the command prompt
	 * @return l_ReturnString returns string acknowledgement based on the added or removed players
	 * @throws Exception this is user defined exception based on the add player or remove player method
	 */
	public String editPlayer(String p_Command,String p_Str) throws Exception {
		String[] l_CommandArray = p_Str.split(" ");
		int l_Counter = 1;
		int l_AddCounter = 0;
		int l_RemoveCounter = 0;
		String l_ReturnString = "";
		if(l_CommandArray.length < 3)
			throw new Exception("Please provide valid Parameters to add player");
		while(l_Counter<l_CommandArray.length) {
			if("-add".equals(l_CommandArray[l_Counter])) {
				d_GameEngine.getGameModel().addPlayer(l_CommandArray[l_Counter+1],l_CommandArray[l_Counter+2]);
				l_Counter+=3;
				l_AddCounter+=1;
			} else if("-remove".equals(l_CommandArray[l_Counter])){
				d_GameEngine.getGameModel().removePlayer(l_CommandArray[l_Counter+1]);

				l_Counter+=2;
				l_RemoveCounter+=1;
			} else {
				break;
			}
		}
		if(l_AddCounter>0) {
			l_ReturnString += "Number of Players Added : " + l_AddCounter + "\n";
		}
		if(l_RemoveCounter>0) {
			l_ReturnString += "Number of Players Removed : " + l_RemoveCounter + "\n";
		}
		return l_ReturnString;
	}
	/**
	 * This method iterates till the player list doesn't becomes empty. This means all the orders of all the players are executed.
	 * It works in a round robin fashion. All the players execute there orders one by one.
	 * The player who's all orders are executed is removed from the list.
	 * The player who has won a battle in this round is assigned a card.
	 * All the players' negotiated players list is made empty.
	 * At the end of this round, we check if a player is declared as a winner.
	 */
	public void playerNextOrder() {
		ArrayList <Player> l_Players = d_GameEngine.getGameModel().getAllPlayers();
		ArrayList <Player> l_PlayersClone = (ArrayList<Player>) l_Players.clone();
		while(!l_PlayersClone.isEmpty()) {
			Iterator<Player>l_It = l_Players.iterator();
			int l_Flag =0;
			ArrayList<Player> l_RemovePlayerList = new ArrayList<Player>();
			while(l_It.hasNext()) {
				Player l_Player = (Player)l_It.next(); 
				if(l_Player.getOrderSize()!=0) {
					Order l_Order = l_Player.next_order();
					if("model.orders.Deploy".equals(l_Order.getClass().getName())) {
						Deploy l_DeployOrder = (Deploy) l_Order;
						l_DeployOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}
					else if("model.orders.Advance".equals(l_Order.getClass().getName())) {
						Advance l_AdvanceOrder = (Advance) l_Order;
						l_AdvanceOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}
					else if("model.orders.Blockade".equals(l_Order.getClass().getName())) {
						Blockade l_BlockadeOrder = (Blockade) l_Order;
						l_BlockadeOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}
					else if("model.orders.Bomb".equals(l_Order.getClass().getName())) {
						Bomb l_BombOrder = (Bomb) l_Order;
						l_BombOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}
					else if("model.orders.Airlift".equals(l_Order.getClass().getName())) {
						Airlift l_AirliftOrder = (Airlift) l_Order;
						l_AirliftOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}
					else if("model.orders.Negotiate".equals(l_Order.getClass().getName())) {
						Negotiate l_NegotiateOrder = (Negotiate) l_Order;
						l_NegotiateOrder.execute();
						String l_Result = l_Player.getResult();
						d_OrderAcknowledgment=l_Result;
						d_CpView.setCommandAcknowledgement(d_OrderAcknowledgment);
						d_LEB.setResult(d_OrderAcknowledgment);
					}

				} else {
					l_Flag = 1; l_RemovePlayerList.add(l_Player);
				}
			}
			if(l_Flag == 1) {
				for(Player l_TempRemovePlayer : l_RemovePlayerList) {
					l_PlayersClone.remove(l_TempRemovePlayer);
				}
			}
		}
		// Assigning cards to players that have won a battle in this round.
		for(Player l_TempPlayer : d_GameEngine.getGameModel().getAllPlayers())
		{
			if(l_TempPlayer.getAtleastOneBattleWon())
			{
				int l_cardInteger = d_Rand.nextInt(4);
				l_TempPlayer.setCard(d_AllCards.get(l_cardInteger));
				d_LEB.setResult("The player -"+l_TempPlayer.getPlayerName()+" has been assigned card - "+d_AllCards.get(l_cardInteger));
				l_TempPlayer.setAtleastOneBattleWon(false);
			}
		}
		d_CpView.setCommandAcknowledgement("\nOrders are Succesfully Executed!!");
		d_LEB.setResult("\nOrders are Succesfully Executed!!");
		clearNegotiatedPlayerList();
		removePlayerWithNoCountry();
		checkTheWinner();	
		d_NumberOfRounds++;
	}
	/**
	 * This method is used to remove the player with no countries on its name.
	 */
	public void removePlayerWithNoCountry() {
		Iterator<Player> l_PlayerIterator = d_GameEngine.getGameModel().getAllPlayers().iterator();
		while(l_PlayerIterator.hasNext()) {
			Player l_TempPlayer = (Player)l_PlayerIterator.next();
			if(l_TempPlayer.getCountriesSize()<=0 && !"Neutral Player".equals(l_TempPlayer.getPlayerName())) {
				l_PlayerIterator.remove();
			}
		}
	}
	/**
	 * This method is used to clear all the individual players' negotiated players list after each round of issuing and execution of orders.
	 */
	public void clearNegotiatedPlayerList()
	{

		for(Player l_TempPlayer: d_GameEngine.getGameModel().getAllPlayers())
		{

			l_TempPlayer.removeNegotiatedPlayer();

		}

	}
	/**
	 * This method is used to check if one player owns all the countries of the map and hence can be declared as the winner of the game.
	 */
	public void checkTheWinner()
	{
		ArrayList <Country> l_CountryList = d_GameEngine.getGameModel().getMap().getCountryList();
		Iterator<Country>itr = l_CountryList.iterator();
		Player l_CheckPlayer = (Player)((Country) itr.next()).getCountryOwnerPlayer();
		int l_flag= 0;
		while(itr.hasNext())
		{
			if(!((Player)((Country) itr.next()).getCountryOwnerPlayer()==l_CheckPlayer))
			{
				l_flag=1;
				break;
			}
		}
		if(l_flag==0)
		{
			setWinner(l_CheckPlayer);
			d_GameEngine.setPhase(new GameOver(d_GameEngine,d_CpView));
			d_CpView.setCommandAcknowledgement("\n"+l_CheckPlayer.getPlayerName()+" is the winner of the game!");
		}

	}
	/**
	 * This is the getter method of providing winner player
	 * @return The player who won the game.
	 */
	public Player getWinner()
	{
		return this.d_Winner;
	}
	/**
	 * The setter method to set the player as winner of the game.
	 * @param p_Winner The player who won the game.
	 */
	public void setWinner(Player p_Winner)
	{
		this.d_Winner=p_Winner;
	}
	
}