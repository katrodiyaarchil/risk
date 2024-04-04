package strategypattern;

import java.io.Serializable;
import java.util.ArrayList;

import model.Country;
import model.Order;
import observerpattern.LogEntryBuffer;


public abstract class Strategy implements Serializable {
    LogEntryBuffer d_Leb = new LogEntryBuffer();

    public abstract Order createOrder();


    public abstract String strategyName();


    public abstract Country toDefend();

    public abstract ArrayList<Country> toAttack();
}
