package edu.lonestar.framer;


import java.util.Vector;

import edu.lonestar.framer.util.RemoteImage;
/**
 * Created by LUONG LUONG on 1/27/2018.
 */


public class DownloadDaemon
{
    static Vector<RemoteImage> obunfiltered;
    Vector<RemoteImage> parseString()
    {
        // Get http response, include try catch for handle exception
        Vector<RemoteImage> ob= new Vector<>(); // Work on this one later
        try
        {
            String initialData =  ""; //TODO
            new RemoteImage(initialData);
            //ector String to store json lists and
            Vector<String> strings= new Vector<>();// Store the Json Lists
            strings.add(initialData.substring(0, initialData.indexOf("},{") + 1)); // copy to vector
            initialData = initialData.substring(initialData.indexOf("},{") + 1, initialData.length());
            // Remove the one which is just copied

            //  for loop to store in unfiltered vector
            obunfiltered= new Vector<>();
            for (String s: strings)
            {
                obunfiltered.addElement(new RemoteImage(s));
            }



        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return new Vector(); // double check what to return
    }






    // Vector object Filtered
        static Vector<RemoteImage> obfiltered= new Vector<>();


}
