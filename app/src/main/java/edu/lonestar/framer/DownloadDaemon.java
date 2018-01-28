package edu.lonestar.framer;
import android.graphics.Color;
import android.os.AsyncTask;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;
import android.graphics.Bitmap;
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
            try {
                // Get http response, include try catch for handle exception
                URL url = new URL("http://eventhorizonwebdesign.com/framed/api/index.php/images");
                URLConnection urlConnection = url.openConnection();//url from string
                InputStream is = urlConnection.getInputStream();    //creating inputstream from url connection
                InputStreamReader isr = new InputStreamReader(is);  // create buffer from inputstream

                int numCharsRead;   //declare position
                char[] charArray = new char[1024];  //set buffer size
                StringBuilder sb = new StringBuilder();//Initialize string builder
                while ((numCharsRead = isr.read(charArray)) > 0) {  //while the file is not ended
                    sb.append(charArray, 0, numCharsRead);  // append the next 1024 bytes
                }
                String result = sb.toString();  //set the result string to fully build appendix
                //Vector String to store json lists and
                Vector<String> strings = new Vector<>();// Store the Json Lists
                strings.add(result.substring(0, result.indexOf("},{") + 1)); // copy to vector
                while (result.contains("},{")) {
                    result = result.substring(result.indexOf("},{") + 1, result.length());
                    strings.add(result.substring(0, result.indexOf("},{") + 1));
                }

                //  for loop to store in unfiltered vector
                obunfiltered = new Vector<>();
                for (String s : strings) {
                    obunfiltered.addElement(new RemoteImage(s));
                }
                obfiltered = new Vector<>();
                for (RemoteImage ob : obunfiltered) {
                    //TODO if ob.artist is a checked artist, add to opfiltered

                }
            }


            catch(Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
// finding average number of pixels in each color
        public void adt(Bitmap b)
        {

            ; //assign your bitmap here
            int redColors = 0;
            int greenColors = 0;
            int blueColors = 0;
            int pixelCount = 0;

            for (int y = 0; y < b.getHeight(); y++)
            {
                for (int x = 0; x < b.getWidth(); x++)
                {
                    int c = b.getPixel(x, y);
                    pixelCount++;
                    redColors += Color.red(c);
                    greenColors += Color.green(c);
                    blueColors += Color.blue(c);
                }
            }
            // calculate average of bitmap r,g,b values
            int red = (redColors/pixelCount);
            int green = (greenColors/pixelCount);
            int blue = (blueColors/pixelCount);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            SettingsActivity.refreshArtistSwitchVector();
        }
    }
}
