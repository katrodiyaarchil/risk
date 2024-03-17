package observerpattern;

public class LogEntryBuffer extends Observable{
    private String d_result;
    private Logger d_logger;


    public LogEntryBuffer(){
        d_logger = new Logger();
        this.attach(d_logger);
    }

    public void setResult(String p_result){
        this.d_result = p_result;
        notify(this);
    }

    public String getResult(){
        return this.d_result;
    }
}
