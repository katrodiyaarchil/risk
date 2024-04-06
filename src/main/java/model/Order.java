package model;

import java.io.Serializable;

/**
 * Parent interface which is implemented by different Orders in the game.
 */
public interface Order extends Serializable {
	/**
	 * This method is overriden by each of the Order class differently.
	 */
	public void execute();
}
