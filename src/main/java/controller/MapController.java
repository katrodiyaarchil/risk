package controller;

import model.Map;


/**
 * This class update the MapModel. Also receives the acknowledgement for the
 * update and pass it back to the parent GameEngine.
 */
public class MapController {

    /**
     * Object of the Map class which is to be access from this controller
     */
    private Map d_MapModel;


    /**
     * This is a constructor which receives Map object as parameter which is used
     * throughout the class
     *
     * @param p_Map This is a reference of Map object (within GameModel) passed from
     *              parent GameEngine.
     */
    public MapController(Map p_Map) {
        d_MapModel = p_Map;
    }


    /**
     * This method calls the savemap method of the mapmodel to save the map in file.
     *
     * @param p_Str the command given by user to save map
     * @return l_Result a feedback message on success or error of save function.
     * @throws Exception throws exception when there is any error during savemap
     */
    public String saveMap(String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        if (l_CommandArray.length < 2) {
            throw new Exception("\nPlease Enter valid Filename to save the map");
        }
        String l_Result = d_MapModel.saveMap(l_CommandArray[1]);
        return l_Result;
    }


    /**
     * This method calls the loadmap method of mapmodel and passes the filename
     * parameter to load a map which is in file
     *
     * @param p_Str It is the command from the CommandPrompt
     * @return l_result a feedback message on success or error
     * @throws Exception throws exception if there is any exception occured during
     *                   loading the map
     */
    public String loadMap(String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        if ("editmap".equals(l_CommandArray[0])) {
            String l_Result = d_MapModel.loadMap(l_CommandArray[1]);
            l_Result = l_Result + "\n" + " You Can Now Edit IT";
            return l_Result;
        } else {
            if (l_CommandArray.length < 2) {
                throw new Exception("\nPlease Enter valid Filename");
            }
            String l_Result = d_MapModel.loadMap(l_CommandArray[1]);
            l_Result = l_Result + "\nYou Can Now Proceed To Add Players";
            return l_Result;
        }
    }


    /**
     * This method will call the reset method of mapmodel class
     */
    public void reset() {
        d_MapModel.reset();
    }


    /**
     * This method calls the Validate map method from the mapmodel class
     *
     * @return String which says if map is valid or not.
     * @throws Exception if there is no map created
     *
     */
    public String validateMap() throws Exception {
        if (d_MapModel.getContinentList().size() > 0) {
            return d_MapModel.validateMap();
        } else {
            throw new Exception("\nNo map to Validate");
        }
    }


    /**
     * This method takes the keyword and entire command from the CommandPrompt and
     * then checks it with all the cases.
     * After checking it performs the specific functionality and returns the
     * feedback accordingly
     * <ul>
     * <li>If the command is to add continent, country or neighbor, it goes into the
     * first block and calls respective method of map model.</li>
     * <li>If the command is to remove continent, country or neighbor, it goes into
     * the second block and calls respective method of map model.</li>
     * <li>Counters are used in order to send back feedback on number of
     * country/continent/neighbor added or removed.</li>
     * <li>In case of exception thrown by the model during add/remove functionality,
     * this method receives it and delegate to the Game Engine to display on
     * view.</li>
     * </ul>
     *
     * @param p_Command The Keyword to access the specific case in the switchcase
     * @param p_Str     The entire command from the CommandPrompt
     * @return l_ReturnString returns feedback on every functionality performed
     * @throws Exception throws exception if any error in performing a certain
     *                   functionality
     */
    public String editMap(String p_Command, String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        int l_Counter = 1;
        int l_AddContinentCounter = 0;
        int l_RemoveContinentCounter = 0;
        int l_AddCountryCounter = 0;
        int l_RemoveCountryCounter = 0;
        int l_AddBorderCounter = 0;
        int l_RemoveBorderCounter = 0;
        String l_ReturnString = "";
        if (l_CommandArray.length <= 1)
            throw new Exception("Please enter valid Parameters!");
        while (l_Counter < l_CommandArray.length) {
            if ("-add".equals(l_CommandArray[l_Counter])) {
                switch (p_Command) {
                    case "editcontinent":
                        if (l_CommandArray.length < 3) {
                            throw new Exception("Please add the name of continent and a control value");
                        }
                        if (l_CommandArray.length < 4) {
                            throw new Exception("Please add control value for the continent");
                        }
                        d_MapModel.addContinent(l_CommandArray[l_Counter + 1], l_CommandArray[l_Counter + 2]);
                        l_Counter += 3;
                        l_AddContinentCounter += 1;
                        break;
                    case "editcountry":
                        if (l_CommandArray.length < 4) {
                            throw new Exception("Please add continent for the country");
                        }
                        d_MapModel.addCountry(l_CommandArray[l_Counter + 1], l_CommandArray[l_Counter + 2]);
                        l_Counter += 3;
                        l_AddCountryCounter += 1;
                        break;
                    case "editneighbor":
                        if (l_CommandArray.length < 4) {
                            throw new Exception("Please add neighbor for the country ");
                        }
                        d_MapModel.addBorder(l_CommandArray[l_Counter + 1], l_CommandArray[l_Counter + 2]);
                        l_Counter += 3;
                        l_AddBorderCounter += 1;
                        break;

                }

            } else if ("-remove".equals(l_CommandArray[l_Counter])) {
                switch (p_Command) {
                    case "editcontinent":
                        if (l_CommandArray.length < 3) {
                            throw new Exception("Please add continent to remove");
                        }
                        d_MapModel.removeContinent(l_CommandArray[l_Counter + 1]);
                        l_Counter += 2;
                        l_RemoveContinentCounter += 1;
                        break;
                    case "editcountry":
                        if (l_CommandArray.length < 3) {
                            throw new Exception("Please add country to remove");
                        }
                        d_MapModel.removeCountry(l_CommandArray[l_Counter + 1], true);
                        l_Counter += 2;
                        l_RemoveCountryCounter += 1;
                        break;
                    case "editneighbor":
                        if (l_CommandArray.length < 4) {
                            throw new Exception("Please add neighbor to remove");
                        }
                        d_MapModel.removeBorder(l_CommandArray[l_Counter + 1], l_CommandArray[l_Counter + 2]);
                        l_Counter += 3;
                        l_RemoveBorderCounter += 1;
                        break;
                }
            } else {
                throw new Exception(
                        "Please Enter a Valid Command. \n If you have added multiple add/remove commands, use showmap command to check the map state.");
            }
        }
        if (l_AddContinentCounter > 0) {
            l_ReturnString += "Number of Continents Added : " + l_AddContinentCounter + "\n";
        }
        if (l_RemoveContinentCounter > 0) {
            l_ReturnString += "Number of Continents Removed : " + l_RemoveContinentCounter + "\n";
        }
        if (l_AddCountryCounter > 0) {
            l_ReturnString += "Number of Countries Added : " + l_AddCountryCounter + "\n";
        }
        if (l_RemoveCountryCounter > 0) {
            l_ReturnString += "Number of Countries Removed : " + l_RemoveCountryCounter + "\n";
        }
        if (l_AddBorderCounter > 0) {
            l_ReturnString += "Number of Borders Added : " + l_AddBorderCounter + "\n";
        }
        if (l_RemoveBorderCounter > 0) {
            l_ReturnString += "Number of Borders Removed : " + l_RemoveBorderCounter + "\n";
        }
        d_MapModel.getContinents();
        if (d_MapModel.getCountryList().size() > 0) {
            d_MapModel.getCountries();
        }
        return l_ReturnString;
    }
}