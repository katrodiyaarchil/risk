package model;


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

    public String getExecuteResult() {
        return this.d_ExecuteResult;
    }

    public void setExecuteResult(String p_ExecuteResult) {
        this.d_ExecuteResult = p_ExecuteResult;
    }

    public void deploy() {
        String[] l_Splitted = d_Order.split(" ");
        if (l_Splitted[0].equals("deploy")) {
            this.d_CountryName = l_Splitted[1];
            this.d_NoOfArmies = Integer.parseInt(l_Splitted[2]);
        } else {
            System.out.println("Invalid command");
        }
    }

    public String getOrder() {
        return this.d_Order;
    }

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
