package observerpattern;

/**
 * Class which extends the Observable class. To log any changes in file, we use
 * the object of this class.
 */
public class LogEntryBuffer extends Observable{
    private String d_result;
    private Logger d_logger;

    /**
     * Constructor which creates the object of Observer logger and attach it with
     * this class.
     */
    public LogEntryBuffer(){
        d_logger = new Logger();
        this.attach(d_logger);
    }

    /**
     * This method is called when we want to log anything in the file.
     * 
     * @param p_result String to be logged.
     */
    public void setResult(String p_result){
        this.d_result = p_result;
        notify(this);
    }

    /**
     * This method returns the string which was logged.
     * 
     * @return Result string which was logged
     */
    public String getResult(){
        return this.d_result;
    }
}
