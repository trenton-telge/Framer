package edu.lonestar.framer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.service.dreams.DreamService;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import edu.lonestar.framer.util.RemoteImage;

/**
 * This class is a sample implementation of a DreamService. When activated, a
 * TextView will repeatedly, move from the left to the right of screen, at a
 * random y-value.
 * <p/>
 * Daydreams are only available on devices running API v17+.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FramerDaydreamService extends DreamService {
    static ImageView myImageView;
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Exit dream upon user touch?
        setInteractive(false);

        // Hide system UI?
        setFullscreen(true);

        // Keep screen at full brightness?
        setScreenBright(true);

        // Set the content view, just like you would with an Activity.
        setContentView(R.layout.framer_daydream);
        myImageView = findViewById(R.id.imageView);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        displayNewImage();
        /*
        TODO set the following
            buffer (overscan + 3 in)
        */

        //TODO set timer on next displayNewImage call
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void displayNewImage(){
        RemoteImage imageToDisplay = DownloadDaemon.obfiltered.elementAt(ThreadLocalRandom.current().nextInt(0, DownloadDaemon.obfiltered.size()+ 1));
        new DownloadTask().execute(imageToDisplay.getUrl());
        ((TextView) findViewById(R.id.artistText)).setText(imageToDisplay.getArtist());
        ((TextView) findViewById(R.id.titleText)).setText(String.format(new Locale("en"),"%s (%d)", imageToDisplay.getTitle(), imageToDisplay.getYear()));
    }
    static class DownloadTask extends AsyncTask<String,Object,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            myImageView.setImageBitmap(result);
        }
    }

}
