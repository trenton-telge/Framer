package edu.lonestar.framer;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.lang.String;
import java.util.Vector;
import edu.lonestar.framer.util.RemoteImage;
/**
 * Created by LUONG LUONG on 1/27/2018.
 */


public class DownloadDaemon
{
    Vector<RemoteImage> parseString()
    {
        // Get http response, include try catch for handle exception
        Vector<RemoteImage> ob= new Vector<>(); // Work on this one later
        try
        {
            String initialData =  IOUtils.toString(URI.create("http://eventhorizonwebdesign.com/framed/api/index.php/images"));
            new RemoteImage(initialData);
            //ector String to store json lists and
            Vector<String> strings= new Vector<>();// Store the Json Lists
            strings.add(initialData.substring(0, initialData.indexOf("},{") + 1)); // copy to vector
            initialData = initialData.substring(initialData.indexOf("},{") + 1, initialData.length());
            // Remove the one which is just copied

            //  for loop to store in unfiltered vector
            Vector<RemoteImage> obunfiltered= new Vector<>();
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
