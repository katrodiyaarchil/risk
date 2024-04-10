package utility.state;

import java.io.File;
import java.util.Scanner;

import javax.swing.JOptionPane;

import file.ConquestLoader;
import file.Adapter;
import file.MapLoader;
import controller.GameEngine;
import observerpattern.LogEntryBuffer;
import view.CommandPrompt;

/**
 * The Edit Phase extends the phase class and implements all the methods
 * suitable for that particular phase.
 * It returns invalid command for others which are not compatible with this
 * phase
 */
public class Edit extends Phase {
    /**
     * object of LogEntryBuffer class to log in the logfile
     */
    LogEntryBuffer d_Leb;

    /**
     * This is the constructor of Edit class which initializes Game engine object
     * and command prompt object and assigning log entry buffer
     * 
     * @param p_Ge object of game engine
     * @param p_Vw object of view
     */
    public Edit(GameEngine p_Ge, CommandPrompt p_Vw) {

        super(p_Ge, p_Vw);
        d_Leb = new LogEntryBuffer();
        d_Leb.setResult("This is the Edit Phase");
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String loadMap(String p_S) {
        boolean l_Flag = false;
        String l_AckMsg;
        try {
            String l_Path = "resource/";
            File l_File = new File(l_Path + p_S.split(" ")[1]);
            Scanner l_Sc = new Scanner(l_File);
            while (l_Sc.hasNextLine()) {

                String l_Line = l_Sc.nextLine();
                if (l_Line.contains("Territories")) {
                    l_Flag = true;
                    break;
                }
            }
            l_Sc.close();
            if (l_Flag) {
                MapLoader l_MapLoaderObject = new Adapter(new ConquestLoader(), d_Ge);
                l_AckMsg = l_MapLoaderObject.loadMap(p_S.split(" ")[1]);
            } else {
                MapLoader l_MapLoaderObject = new MapLoader(d_Ge);
                l_AckMsg = l_MapLoaderObject.loadMap(p_S);
            }

        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
            d_Leb.setResult(l_AckMsg);
            d_Ge.setPhase(new Edit(d_Ge, d_Vw));
            return l_AckMsg;
        }
        d_Leb.setResult(l_AckMsg);

        d_Ge.setPhase(new Startup(d_Ge, d_Vw));
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editCountry(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);

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
    public String saveMap(String p_S) {
        String l_AckMsg;
        try {
            while (true) {
                String l_StringOrder = JOptionPane
                        .showInputDialog(" : Please Enter 1 to save as ConquestMap OR 2 for DominationMap");

                if (l_StringOrder.equals("1")) {

                    MapLoader l_MapLoaderObject = new Adapter(new ConquestLoader(), d_Ge);
                    l_AckMsg = l_MapLoaderObject.saveMap(p_S.split(" ")[1]);
                    break;
                } else if (l_StringOrder.equals("2")) {
                    MapLoader l_MapLoaderObject = new MapLoader(d_Ge);
                    l_AckMsg = l_MapLoaderObject.saveMap(p_S);
                    break;
                }

            }
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
    public String addPlayers(String p_S, String p_S1) {
        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");
        return null;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public String editMap(String p_S) {

        String l_AckMsg;
        boolean l_Flag = false;
        try {
            String l_Path = "resource/";
            File l_File = new File(l_Path + p_S.split(" ")[1]);
            Scanner l_Sc = new Scanner(l_File);
            while (l_Sc.hasNextLine()) {

                String l_Line = l_Sc.nextLine();
                if (l_Line.contains("Territories")) {
                    l_Flag = true;
                    break;
                }
            }
            l_Sc.close();
            if (l_Flag) {
                MapLoader l_MapLoaderObject = new Adapter(new ConquestLoader(), d_Ge);
                l_AckMsg = l_MapLoaderObject.loadMap(p_S.split(" ")[1]);
            } else {
                MapLoader l_MapLoaderObject = new MapLoader(d_Ge);
                l_AckMsg = l_MapLoaderObject.loadMap(p_S);
            }
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
    public String editContinent(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);
        } catch (Exception p_Exception) {
            l_AckMsg = p_Exception.getMessage();
        }
        d_Leb.setResult(l_AckMsg);
        return l_AckMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String editNeighbor(String p_S, String p_S1) {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().editMap(p_S, p_S1);
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
    public String validateMap() {
        String l_AckMsg;
        try {
            l_AckMsg = d_Ge.getMapController().validateMap();
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
    public void assignCountries() {

        d_Vw.setCommandAcknowledgement("Invalid command in state " + this.getClass().getSimpleName() + "\n");
        d_Leb.setResult("Invalid command in state ");

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
     */
    @Override
    public String getPhaseName() {
        return "EditPhase";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String tournament(String p_string, String p_CommandStringFromInput) {
        try {
            d_Ge.tournament(p_CommandStringFromInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " ";
    }

}
