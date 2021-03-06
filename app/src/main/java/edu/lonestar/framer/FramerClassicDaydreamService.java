package edu.lonestar.framer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import edu.lonestar.framer.util.RemoteImage;

/**
 * This class is a sample implementation of a DreamService. When activated, a
 * TextView will repeatedly, move from the left to the right of screen, at a
 * random y-value.
 * <p/>
 * Daydreams are only available on devices running API v17+.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FramerClassicDaydreamService extends DreamService {
    SharedPreferences sharedPref;

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
        setContentView(R.layout.framer_classic_daydream);
        sharedPref = getApplication().getSharedPreferences("framer", Context.MODE_PRIVATE);
        ImageView frame = findViewById(R.id.mimicFrame);
        frame.setBackground(getDrawable(R.drawable.frame_4k));
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayNewImage();
            }
        }, 0);
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
            int ran = new  Random().nextInt(DownloadDaemon.obunfiltered.size()-2);
            RemoteImage imageToDisplay = DownloadDaemon.obunfiltered.elementAt(ran);
            new FramerClassicDaydreamService.ImageDownloadTask().execute(imageToDisplay.getUrl());
        } else {
            ((ImageView)findViewById(R.id.imageViewMimic)).setImageResource(R.drawable.framer_banner);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayNewImage();
                }
            }, 2000);
        }
    }
    class ImageDownloadTask extends AsyncTask<String,Object,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0].trim());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap result) {
            ((ImageView)findViewById(R.id.imageViewMimic)).setImageBitmap(result);
        }
    }


}
