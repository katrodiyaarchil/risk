package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

/**
 * The Startup Phase extends the phase class and implements all the methods
 * suitable for that particular phase.
 * It returns invalid command for others which are not compatible with this
 * phase
 */
public class Startup extends Phase {
	/**
	 * object of LogEntryBuffer class to log in the logfile
	 */
	LogEntryBuffer d_Leb;

	/**
	 * This is the constructor of Startup which initializes Game engine object and
	 * command prompt object and assigning log entry buffer
	 * 
	 * @param p_Ge object of game engine
	 * @param p_Vw object of view
	 */
	public Startup(GameEngine p_Ge, CommandPrompt p_Vw) {
		super(p_Ge, p_Vw);
		d_Leb = new LogEntryBuffer();
		d_Leb.setResult("This is the Startup Phase");
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void showMap() {
		d_Ge.showMap(this);
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String loadMap(String p_S) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String editCountry(String p_S, String p_S1) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String saveMap(String p_S) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String addPlayers(String p_S, String p_S1) {
		String l_AckMsg;
		try {
			l_AckMsg = d_Ge.getPlayerController().editPlayer(p_S, p_S1);

		} catch (Exception p_Exception) {
			l_AckMsg = p_Exception.getMessage();
		}
		d_Leb.setResult(l_AckMsg);
		return l_AckMsg;

	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String editMap(String p_S) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;

	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String editContinent(String p_S, String p_S1) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String editNeighbor(String p_S, String p_S1) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void assignCountries() {
		try {
			d_Ge.assignCountries();
		} catch (Exception p_Exception) {
			d_Vw.setCommandAcknowledgement(p_Exception.getMessage());
			d_Leb.setResult(p_Exception.getMessage());
			d_Ge.setPhase(new Startup(d_Ge, d_Vw));
			return;
		}
		d_Ge.setPhase(new Reinforcement(d_Ge, d_Vw));
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String validateMap() {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String getPhaseName() {
		return "Startup";
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public String tournament(String p_string, String p_CommandStringFromInput) {
		d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
		d_Leb.setResult("Invalid command in state ");
		return null;
	}

}
