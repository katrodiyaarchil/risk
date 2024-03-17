package model;

/**
 * Parent interface which is implemented by different Orders in the game.
 */
public interface Order {
	/**
	 * This method is overriden by each of the Order class differently. 
	 */
	public void execute();
}