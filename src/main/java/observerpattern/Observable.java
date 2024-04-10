package observerpattern;

import java.util.ArrayList;
/**
 * Observable class which has methods to connect/disconnect with observers and
 * notifies if there is any update.
 */
public class Observable {

    ArrayList<Observer> d_observers = new ArrayList<Observer>();
    
	/**
	 * This method attaches the Observer object with the observable.
	 * 
	 * @param p_observer Object of Observer which is going to be attached with
	 *                   Observable.
	 */
    public void attach(Observer p_observer){
        this.d_observers.add(p_observer);
    }

    /**
	 * This method detaches the Observer object with the observable.
	 * 
	 * @param p_observer Object of Observer which is going to be detached with
	 *                   Observable.
	 */
    public void detach(Observer p_observer){
        this.d_observers.remove(p_observer);
    }
    
	/**
	 * This is notify method of the Observable which calls the update method on
	 * every change/addition of log.
	 * 
	 * @param p_observable Object of observable
	 */
    public void notify(Observable p_observable){
        for(Observer l_observer : d_observers){
            l_observer.update(p_observable);
        }
    }
}
