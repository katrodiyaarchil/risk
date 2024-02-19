package controller;

import model.Map;


public class MapController {
    
    private Map d_MapModel;

    
    public MapController(Map p_Map) {
        d_MapModel = p_Map;
    }

    
    public String saveMap(String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        if (l_CommandArray.length < 2) {
            throw new Exception("\nPlease Enter valid Filename to save the map");
        }
        String l_Result = d_MapModel.saveMap(l_CommandArray[1]);
        return l_Result;
    }

    
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

    
    public void reset() {
        d_MapModel.reset();
    }

    
    public String validateMap() throws Exception {
        if (d_MapModel.getContinentList().size() > 0) {
            return d_MapModel.validateMap();
        } else {
            throw new Exception("\nNo map to Validate");
        }
    }

    
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