package edu.lonestar.framer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import java.util.Random;

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

        setContentView(R.layout.framer_classic_daydream);
        sharedPref = getApplication().getSharedPreferences("framer", Context.MODE_PRIVATE);
        ImageView frame = findViewById(R.id.mimicFrame);
        frame.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.frame_4k));
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        final Handler handler = new Handler();
        handler.postDelayed(this::displayNewImage, 0);
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
        TypedArray images = getResources().obtainTypedArray(R.array.telgephotos);
        int selectedID = new Random().nextInt(images.length());
        Drawable result = images.getDrawable(selectedID);
        ((ImageView)findViewById(R.id.imageViewMimic)).setImageDrawable(result);
        images.recycle();
        /*
        ((ImageView)findViewById(R.id.imageViewMimic)).setImageResource(R.drawable.framer_banner);
        final Handler handler = new Handler();
        handler.postDelayed(this::displayNewImage, 2000);
        */
    }

}
