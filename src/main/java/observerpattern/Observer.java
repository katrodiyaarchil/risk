package observerpattern;

/**
 * Interface observer which has one update method which is called when
 * observable notifies it.
 */
public interface Observer {
	/**
	 * This method updates the file on each log entry in LogEntryBuffer.
	 * 
	 * @param p_observable Oject of observable who notifies the Observer.
	 */
    public void update(Observable p_observable);
}
