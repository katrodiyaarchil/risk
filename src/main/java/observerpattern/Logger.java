package observerpattern;

import java.io.*;

/**
 * This is a concrete class which implements observer interface.
 * Object of this class is attached with the Observable LogEntryBuffer.
 * On any change, the object of this class will be notified.
 */
public class Logger implements Observer {
    private static int d_count = 0;

    /**
     * This method will internally update the Log file on each notification from
     * observable.
     */
    @Override
    public void update(Observable p_observable){
        String l_update = ((LogEntryBuffer) p_observable).getResult();
        String l_path = "resource/logFile";
        if(d_count == 0){
            try{
                PrintWriter l_printWriter = new PrintWriter(l_path);
                l_printWriter.println();
                l_printWriter.close();
                d_count++;
            } catch (FileNotFoundException e){

            } catch (Exception e){

            }
        }
        try{
            File l_file = new File(l_path);
            FileWriter l_fileWriter = new FileWriter(l_file, true);
            BufferedWriter l_bufferedWriter = new BufferedWriter(l_fileWriter);
            PrintWriter l_PrintWriter = new PrintWriter(l_bufferedWriter);
            l_PrintWriter.println(l_update);
            l_PrintWriter.close();
            l_bufferedWriter.close();
            l_fileWriter.close();

        }
        catch (IOException ioe){

        } catch (Exception e){

        }

    }
}
