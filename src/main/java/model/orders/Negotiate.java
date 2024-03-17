package model.orders;

import model.Order;
import model.Player;

public class Negotiate implements Order {

    private Player d_SourcePlayer, d_TargetPlayer;

    public Negotiate(Player p_SourcePlayer, Player p_TargetPlayer) {
        setSourcePlayer(p_SourcePlayer);
        d_TargetPlayer = p_TargetPlayer;
    }

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

    public Player getSourcePlayer() {
        return d_SourcePlayer;
    }

    public void setSourcePlayer(Player d_SourcePlayer) {
        this.d_SourcePlayer = d_SourcePlayer;
    }
}
