package observerpattern;

import java.util.ArrayList;

public class Observable {

    ArrayList<Observer> d_observers = new ArrayList<Observer>();

    public void attach(Observer p_observer){
        this.d_observers.add(p_observer);
    }
    public void detach(Observer p_observer){
        this.d_observers.remove(p_observer);
    }

    public void notify(Observable p_observable){
        for(Observer l_observer : d_observers){
            l_observer.update(p_observable);
        }
    }
}
