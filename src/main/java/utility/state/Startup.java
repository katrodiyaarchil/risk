package utility.state;

import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

public class Startup extends Phase {
    public Startup(GameEngine p_Ge, CommandPrompt p_Vw) {
        super(p_Ge, p_Vw);
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is the Startup Phase");
    }
}
