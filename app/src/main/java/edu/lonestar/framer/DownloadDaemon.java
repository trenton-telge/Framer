package edu.lonestar.framer;
import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import edu.lonestar.framer.util.RemoteImage;
/**
 * Created by LUONG LUONG on 1/27/2018.
 */


public class DownloadDaemon
{
    static Vector<RemoteImage> obfiltered = new Vector<>();
    static Vector<RemoteImage> obunfiltered = new Vector<>();
    public void parseString()
    {

    }
    static class RefreshDataTask extends AsyncTask<Object, Object, Void> {

        @Override
        protected Void doInBackground(Object... objects) {
            try
            {
                // Get http response, include try catch for handle exception
                URL url = new URL("http://eventhorizonwebdesign.com/framed/api/index.php/images");
                URLConnection urlConnection = url.openConnection();
                InputStream is = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int numCharsRead;
                char[] charArray = new char[1024];
                StringBuilder sb = new StringBuilder();
                while ((numCharsRead = isr.read(charArray)) > 0) {
                    sb.append(charArray, 0, numCharsRead);
                }
                String result = sb.toString();
                new RemoteImage(result);
                //Vector String to store json lists and
                Vector<String> strings= new Vector<>();// Store the Json Lists
                strings.add(result.substring(0, result.indexOf("},{") + 1)); // copy to vector
                result = result.substring(result.indexOf("},{") + 1, result.length());
                // Remove the one which is just copied

                //  for loop to store in unfiltered vector
                obunfiltered = new Vector<>();
                for (String s: strings)
                {
                    obunfiltered.addElement(new RemoteImage(s));
                }
                obfiltered = new Vector<>();
                for (RemoteImage ob : obunfiltered){

                    //TODO if ob.artist is a checked artist, add to opfiltered
                }


            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SettingsActivity.refreshArtistSwitchVector();
        }
    }
}
