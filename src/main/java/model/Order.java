package model;

/**
 * 
 * Class for Order of the players
 *
 */
public class Order {

    private String d_Order;
    private String d_CountryName;
    private int d_NoOfArmies;
    private GameModel d_GameModel;
    private String d_ExecuteResult = "";

    public Order(String p_Order, GameModel p_GameModel) {
        this.d_Order = p_Order;
        this.d_GameModel = p_GameModel;
        deploy();
    }

    /**
     * This method sends the result after execution of execute method
     * 
     * @return returns if the order is successfully executed or not.
     */
    public String getExecuteResult() {
        return this.d_ExecuteResult;
    }

    /**
     * This method is used to set the result after the execution of the execute
     * method.
     * 
     * @param p_ExecuteResult String to set the executed result
     */
    public void setExecuteResult(String p_ExecuteResult) {
        this.d_ExecuteResult = p_ExecuteResult;
    }

    /**
     * Method to check if the command issued is correct or not and set country name
     * and the armies
     */
    public void deploy() {
        String[] l_Splitted = d_Order.split(" ");
        if (l_Splitted[0].equals("deploy")) {
            this.d_CountryName = l_Splitted[1];
            this.d_NoOfArmies = Integer.parseInt(l_Splitted[2]);
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * This method returns the order of the player
     * 
     * @return the order issues by the player
     */
    public String getOrder() {
        return this.d_Order;
    }

    /**
     * This method is used to execute the orders issued by the players.
     */
    public void execute() {
        int l_Flag = 0;
        for (Country l_Country : d_GameModel.getSelectedMap().getCountryList()) {
            if (l_Country.getCountryName().equals(d_CountryName)) {
                l_Flag = 1;
                int l_Armies = l_Country.getNoOfArmies();
                l_Country.setNoOfArmies(d_NoOfArmies + l_Armies);
                setExecuteResult("\n" + "The armies are succesfully deployed on " + d_CountryName);
            }
        }
        if (l_Flag == 0) {
            setExecuteResult("\n" + "The armies are  not succesfully deployed on " + d_CountryName);
        }
    }
}
