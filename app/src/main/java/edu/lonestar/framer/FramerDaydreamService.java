package edu.lonestar.framer;

import android.annotation.TargetApi;
import android.os.Build;
import android.service.dreams.DreamService;

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

    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        /*
        TODO set the following:
            image source
            buffer (overscan + 3 in)
            text
        */


    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // TODO: Dismantle resources
    }

    private void displayNewImage(){
        RemoteImage imageToDisplay = DownloadDaemon.obfiltered.elementAt(ThreadLocalRandom.current().nextInt(0, DownloadDaemon.obfiltered.size()+ 1));

    }

}
