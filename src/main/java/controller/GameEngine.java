package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Continent;
import model.Country;
import model.GameModel;
import view.CommandPrompt;
import java.util.ArrayList;

import model.Player;

public class GameEngine {
    private GameModel d_GameModel;
    private CommandPrompt d_CpView;
    private MapController d_MapController;
    private ArrayList<Player> d_PlayerList;
    private PlayerController d_PlayerController;

    public GameEngine(CommandPrompt p_CpView, GameModel p_GameModel) {
        d_GameModel = p_GameModel;
        d_CpView = p_CpView;
        d_MapController = new MapController(this.d_GameModel.getMap());
        d_CpView.commandSendButtonListener(new CommandListener());
    }

    public class CommandListener implements ActionListener {
        private boolean d_MapDone = false;
        private boolean d_StartUpDone = false;
        private boolean d_AssignCountriesDone = false;

        @Override
        public void actionPerformed(ActionEvent l_E) {
            try {
                String l_CommandStringFromInput = d_CpView.getCommandInput().trim();
                switch (l_CommandStringFromInput.split(" ")[0]) {
                    case "editcontinent":
                        if (d_MapDone == false) {
                            try {
                                String l_AckMsg = d_MapController.editMap("editcontinent", l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_AckMsg + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage());
                                d_CpView.setCommandAcknowledgement("\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant Edit Map In This Phase" + "\n");
                        }
                        break;

                    case "editcountry": {
                        if (d_MapDone == false) {
                            try {
                                String l_AckMsg = d_MapController.editMap("editcountry", l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_AckMsg + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage());
                                d_CpView.setCommandAcknowledgement("\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant Edit Map In This Phase" + "\n");
                        }
                    }
                        break;

                    case "editneighbor": {
                        if (d_MapDone == false) {
                            try {
                                String l_AckMsg = d_MapController.editMap("editneighbor", l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_AckMsg + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage());
                                d_CpView.setCommandAcknowledgement("\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant Edit Map In This Phase" + "\n");
                        }
                    }
                        break;

                    case "showmap": {
                        showMap(d_MapDone);
                    }
                        break;

                    case "savemap": {
                        if (d_MapDone == false) {
                            try {
                                String l_Result = d_MapController.saveMap(l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_Result + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage() + "\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant Save Map In This Phase" + "\n");
                        }
                    }
                        break;

                    case "editmap": {
                        if (d_MapDone == false) {
                            try {
                                String l_Result = d_MapController.loadMap(l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_Result + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(
                                        "The Mapfile Doesnt Exist. Please Create A New Map" + "\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant Edit Another Map In This Phase" + "\n");
                        }
                    }
                        break;

                    case "validatemap": {
                        if (d_MapDone == false) {
                            try {
                                d_CpView.setCommandAcknowledgement(d_MapController.validateMap());
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage() + "\n");
                            }
                        } else {
                            d_CpView.setCommandAcknowledgement("Cant validate Map In This Phase" + "\n");
                        }
                    }
                        break;

                    case "loadmap": {
                        try {
                            String l_Result = d_MapController.loadMap(l_CommandStringFromInput);
                            this.d_MapDone = true;
                            d_CpView.setCommandAcknowledgement(l_Result + "\n");
                        } catch (Exception p_Exception) {
                            d_CpView.setCommandAcknowledgement(p_Exception.getMessage() + "\n");
                        }
                    }
                        break;

                    case "gameplayer": {
                        if (d_MapDone == true & d_StartUpDone == false) {
                            try {
                                String l_AckMsg1 = editPlayer("GamePlayer", l_CommandStringFromInput);
                                d_CpView.setCommandAcknowledgement(l_AckMsg1 + "\n");
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage() + "\n");
                                d_CpView.setCommandAcknowledgement("\n");
                            }
                        } else {
                            if (d_MapDone == false)
                                d_CpView.setCommandAcknowledgement(
                                        "\n" + "The Map is Not Loaded Yet to Add Players " + "\n");
                            if (d_StartUpDone == true)
                                d_CpView.setCommandAcknowledgement("\n"
                                        + "You are trying to remove or add players after the startup phase" + "\n");
                        }
                    }
                        break;

                    case "assigncountries": {
                        if (d_MapDone == true & d_AssignCountriesDone == false) {
                            try {
                                assignCountries();
                            } catch (Exception p_Exception) {
                                d_CpView.setCommandAcknowledgement(p_Exception.getMessage());
                                d_CpView.setCommandAcknowledgement("\n");
                                break;
                            }
                            d_AssignCountriesDone = true;
                            d_StartUpDone = true;
                            showAllPlayerWithArmies();
                            d_CpView.setCommandAcknowledgement("\n");
                            d_PlayerController = new PlayerController(d_GameModel, d_CpView);
                            d_PlayerController.playerIssueOrder();
                            d_PlayerController.playerNextOrder();
                        } else {
                            if (d_MapDone == false)
                                d_CpView.setCommandAcknowledgement(
                                        "\n" + "The Map is Not Loaded Yet to Add Assign Countries " + "\n");
                            if (d_AssignCountriesDone == true)
                                d_CpView.setCommandAcknowledgement("\n" + "StartUp Phase is already completed " + "\n");
                        }
                    }
                        break;
                    case "deploy":
                        break;

                    case "show":
                        if (d_MapDone == true) {
                            showAllPlayerWithArmies();
                            d_CpView.setCommandAcknowledgement("\n");
                        } else {
                            d_CpView.setCommandAcknowledgement(
                                    "The Map is not loaded yet to perform show operation" + "\n");
                        }
                        break;

                    case "reset":
                        d_MapController.reset();
                        d_CpView.setCommandAcknowledgement("The Map is Reset." + "\n");
                        break;

                    default:
                        d_CpView.setCommandAcknowledgement("Invalid Command. Please Re-try .\n");
                        break;
                }
                d_CpView.setCommandInput("");
            } catch (Exception p_Exception) {
                System.out
                        .println("Exception in ActionPerformed Method in ActionListener: " + p_Exception.getMessage());
            }
        }
    }

    public String editPlayer(String p_Command, String p_Str) throws Exception {
        String[] l_CommandArray = p_Str.split(" ");
        int l_Counter = 1;
        int l_AddCounter = 0;
        int l_RemoveCounter = 0;
        String l_ReturnString = "";
        if (l_CommandArray.length < 3)
            throw new Exception("Kindly provide valid Parameters for adding player");
        while (l_Counter < l_CommandArray.length) {
            if (l_CommandArray[l_Counter].equals("-add")) {
                d_GameModel.addPlayer(l_CommandArray[l_Counter + 1]);
                l_Counter += 2;
                l_AddCounter += 1;
            } else if (l_CommandArray[l_Counter].equals("-remove")) {
                d_GameModel.removePlayer(l_CommandArray[l_Counter + 1]);
                l_Counter += 2;
                l_RemoveCounter += 1;
            } else {
                break;
            }
        }
        if (l_AddCounter > 0) {
            l_ReturnString += "Number of Players Added: " + l_AddCounter + "\n";
        }
        if (l_RemoveCounter > 0) {
            l_ReturnString += "Number of Players Removed: " + l_RemoveCounter + "\n";
        }
        return l_ReturnString;
    }

    public void assignCountries() throws Exception {
        d_GameModel.startUpPhase();
    }

    public void showAllPlayerWithArmies() {
        d_PlayerList = d_GameModel.getAllPlayers();
        for (Player l_Player : d_PlayerList) {
            d_CpView.setCommandAcknowledgement(
                    "\n" + l_Player.getPlayerName() + "--> " + "armies assigned: " + l_Player.getPlayerArmies());
            d_CpView.setCommandAcknowledgement("\n" + "Countries Assigned: ");
            for (Country l_Country : l_Player.getCountryList()) {
                d_CpView.setCommandAcknowledgement(l_Country.getCountryName() + ",");
            }
        }
    }

    public void showMap(Boolean p_BooleanForGamePhaseStarted) {
        if (p_BooleanForGamePhaseStarted) {
            d_PlayerList = d_GameModel.getAllPlayers();
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
            if (l_ContinentList.size() > 0) {
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_CpView.setCommandAcknowledgement("\n");
                    for (Country l_Country : l_CountryList) {
                        d_CpView.setCommandAcknowledgement("Country: " + l_Country.getCountryName());
                        if (this.d_PlayerList != null) {
                            for (Player l_Player : d_PlayerList) {
                                if (l_Player.getCountryList().contains(l_Country)) {
                                    d_CpView.setCommandAcknowledgement("\n" + "--> Owner: " + l_Player.getPlayerName());
                                    d_CpView.setCommandAcknowledgement(
                                            "\n" + "--> Armies deployed: " + l_Country.getNoOfArmies());
                                }
                            }
                        }
                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (l_NeighborList.size() > 0) {
                            d_CpView.setCommandAcknowledgement("\n" + "--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_CpView.setCommandAcknowledgement(l_Str + ",");
                            }
                        }
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        } else {
            ArrayList<Continent> l_ContinentList = d_GameModel.getMap().getContinentList();
            if (l_ContinentList.size() > 0) {
                d_CpView.setCommandAcknowledgement("\n");
                for (Continent l_Continent : l_ContinentList) {
                    d_CpView.setCommandAcknowledgement("Continent: " + l_Continent.getContinentName() + "\n");
                    ArrayList<Country> l_CountryList = l_Continent.getCountryList();
                    d_CpView.setCommandAcknowledgement("Countries:" + "\n");
                    for (Country l_Country : l_CountryList) {
                        d_CpView.setCommandAcknowledgement(l_Country.getCountryName());
                        ArrayList<String> l_NeighborList = l_Country.getBorder();
                        if (l_NeighborList.size() > 0) {
                            d_CpView.setCommandAcknowledgement("--> Borders : ");
                            for (String l_Str : l_NeighborList) {
                                d_CpView.setCommandAcknowledgement(l_Str + " ");
                            }
                        }
                        d_CpView.setCommandAcknowledgement("\n");
                    }
                    d_CpView.setCommandAcknowledgement("\n");
                }
            }
        }
    }

}
