package edu.lonestar.framer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.lang.String;
import java.net.URLConnection;
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
            URL url = new URL("http://eventhorizonwebdesign.com/framed/api/index.php/images");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            String result = sb.toString();
            new RemoteImage(result);
            //ector String to store json lists and
            Vector<String> strings= new Vector<>();// Store the Json Lists
            strings.add(result.substring(0, result.indexOf("},{") + 1)); // copy to vector
            result = result.substring(result.indexOf("},{") + 1, result.length());
            // Remove the one which is just copied

            //  for loop to store in unfiltered vector
            obunfiltered= new Vector<>();
            for (String s: strings)
            {
                obunfiltered.addElement(new RemoteImage(s));
            }

            Collection<RemoteImage> collection = new ArrayList<RemoteImage>();
            // for loop
            for (Iterator<RemoteImage> iterator = collection.iterator();;)
            {

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
