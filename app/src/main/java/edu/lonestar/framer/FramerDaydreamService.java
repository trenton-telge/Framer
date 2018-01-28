package edu.lonestar.framer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import edu.lonestar.framer.util.RemoteImage;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FramerDaydreamService extends DreamService {
    SharedPreferences sharedPref;
    static ImageView myImageView;
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        new DownloadDaemon().refresh();

        // Exit dream upon user touch?
        setInteractive(false);

        // Hide system UI?
        setFullscreen(true);

        // Keep screen at full brightness?
        setScreenBright(true);

        // Set the content view, just like you would with an Activity.
        setContentView(R.layout.framer_daydream);
        myImageView = findViewById(R.id.imageView);
        sharedPref = getApplication().getSharedPreferences("framer", Context.MODE_PRIVATE);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        displayNewImage();
        //TODO set matting size due to overscan
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        findViewById(R.id.bufferLeft).setMinimumWidth(findViewById(R.id.bufferLeft).getWidth()+(display.getWidth()*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferRight).setMinimumWidth(findViewById(R.id.bufferRight).getWidth()+(display.getWidth()*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferTop).setMinimumHeight(findViewById(R.id.bufferTop).getHeight()+(display.getHeight()*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferBottom).setMinimumHeight(findViewById(R.id.bufferBottom).getHeight()+(display.getHeight()*sharedPref.getInt("overscan", 0)/200));
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayNewImage();
            }
        }, sharedPref.getInt("length_of_time", 30)*1000);
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
        if (DownloadDaemon.obunfiltered.size() > 0) {
            RemoteImage imageToDisplay = DownloadDaemon.obunfiltered.elementAt(ThreadLocalRandom.current().nextInt(0, DownloadDaemon.obunfiltered.size()));
            new DownloadTask().execute(imageToDisplay.getUrl());
            ((TextView) findViewById(R.id.artistText)).setText(imageToDisplay.getArtist());
            ((TextView) findViewById(R.id.titleText)).setText(String.format(new Locale("en"), "%s (%d)", imageToDisplay.getTitle(), imageToDisplay.getYear()));
        } else {
            myImageView.setImageResource(R.drawable.framer_banner);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayNewImage();
                }
            }, 500);
        }
        if (sharedPref.getBoolean("display_nameplate", false)) {
            findViewById(R.id.nameplateLayout).setVisibility(View.VISIBLE);
        } else findViewById(R.id.nameplateLayout).setVisibility(View.INVISIBLE);
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
