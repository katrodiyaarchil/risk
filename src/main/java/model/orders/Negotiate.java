package model.orders;

import model.Order;
import model.Player;

/**
 * Represents a Negotiate order, where a player initiates diplomacy with another
 * player.
 */
public class Negotiate implements Order {

    private Player d_SourcePlayer, d_TargetPlayer;

    /**
     * Constructor for Negotiate order.
     * 
     * @param p_SourcePlayer The player initiating the negotiation.
     * @param p_TargetPlayer The player to negotiate with.
     */
    public Negotiate(Player p_SourcePlayer, Player p_TargetPlayer) {
        setSourcePlayer(p_SourcePlayer);
        d_TargetPlayer = p_TargetPlayer;
    }

    /**
     * Executes the Negotiate order.
     */
    @Override
    public void execute() {
        if (this.getSourcePlayer().getCardList().contains("Negotiate")) {
            this.getSourcePlayer().getNegotiatedPlayerList().add(d_TargetPlayer);
            this.d_TargetPlayer.getNegotiatedPlayerList().add(getSourcePlayer());
            getSourcePlayer().removeCard("Negotiate");
            d_SourcePlayer.setResult("Negotiation with " + d_TargetPlayer.getPlayerName() + " successful.");
        } else {
            getSourcePlayer().setResult("\n" + getSourcePlayer().getPlayerName()
                    + " does not own Negotiate Card for Diplomacy with " + d_TargetPlayer.getPlayerName());
        }
    }

    /**
     * Gets the player initiating the negotiation.
     * 
     * @return The source player.
     */
    public Player getSourcePlayer() {
        return d_SourcePlayer;
    }

    /**
     * Sets the player initiating the negotiation.
     * 
     * @param d_SourcePlayer The source player.
     */
    public void setSourcePlayer(Player d_SourcePlayer) {
        this.d_SourcePlayer = d_SourcePlayer;
    }
}
