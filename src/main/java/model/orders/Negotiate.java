package model.orders;

import model.Order;
import model.Player;

/**
 * Negotiate class implements the Order interface and overrides the execute
 * method.
 * During IssueOrder phase, when player issues negotiate command, it is stored
 * in the orders list.
 * During the ExecuteOrder phase, execute method of this class is called.
 */
public class Negotiate implements Order {
    /**
     * The source player is the negotiate order issuing player, the target player is
     * the player with whom the deal is struck.
     */
    Player d_SourcePlayer;
    /**
     * The source player is the negotiate order issuing player, the target player is
     * the player with whom the deal is struck.
     */
    Player d_TargetPlayer;

    /**
     * Constructor of the Negotiate class which is called when object is created.
     * 
     * @param p_SourcePlayer The player who issues the negotiate command.
     * @param p_TargetPlayer The Player with whom source player wants to have a
     *                       negotiation.
     */
    public Negotiate(Player p_SourcePlayer, Player p_TargetPlayer) {
        setSourcePlayer(p_SourcePlayer);
        d_TargetPlayer = p_TargetPlayer;

    }

    /**
     * This method adds the logic for the diplomacy(negotiation).
     * If the source player owns Negotiate Card, then this method will make sure
     * source and target players can not attack on each other during the turn.
     * This method also removes the Negotiate Card from the source player so that he
     * can not use it again.
     */
    @Override
    public void execute() {
        /*
         * If Player has negotiate card, then it will add target player to
         * negotiatedPlayerList and then remove that card
         */
        if (this.getSourcePlayer().getCardList().contains("Negotiate")) {
            this.getSourcePlayer().getNegotiatedPlayerList().add(d_TargetPlayer);
            this.d_TargetPlayer.getNegotiatedPlayerList().add(getSourcePlayer());
            getSourcePlayer().removeCard("Negotiate");
            d_SourcePlayer.setResult("Negotation with " + d_TargetPlayer.getPlayerName() + " successfull.");
        } else {
            getSourcePlayer().setResult("\n" + getSourcePlayer().getPlayerName()
                    + " does not own Negotiate Card for Diplomacy with " + d_TargetPlayer.getPlayerName());
        }

    }

    /**
     * This method returns the source player who issues the Negotiate order.
     * 
     * @return Player who issued the negotiate command.
     */
    public Player getSourcePlayer() {
        return d_SourcePlayer;
    }

    /**
     * This method sets the player who issues the Negotiate order.
     * 
     * @param d_SourcePlayer Player who issues the negotiate order.
     */
    public void setSourcePlayer(Player d_SourcePlayer) {
        this.d_SourcePlayer = d_SourcePlayer;
    }

}
