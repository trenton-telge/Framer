package edu.lonestar.framer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

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
        Set<String> activeSets = sharedPref.getStringSet("active_groups", new HashSet<>());
        if (activeSets.isEmpty()) {
            ((ImageView)findViewById(R.id.imageViewMimic)).setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_background));
            return;
        }
        List<String> setsList = new ArrayList<>(activeSets);
        Collections.shuffle(setsList);
        String pickedPack = setsList.get(0);
        TypedArray images = getStringResourceByName(pickedPack);
        int selectedID = new Random().nextInt(images.length());
        Drawable result = images.getDrawable(selectedID);
        Logger.getAnonymousLogger().info("Loaded drawable " + (selectedID + 1) + " of " + images.length());
        ((ImageView)findViewById(R.id.imageViewMimic)).setImageDrawable(result);
        images.recycle();

        if (sharedPref.getBoolean("auto_rotate", false)) {
            final Handler handler = new Handler();
            handler.postDelayed(this::displayNewImage, 1000*60*60);
        }
    }
    private TypedArray getStringResourceByName(String aString) {
        String packageName = getPackageName();
        int resId = getResources().getIdentifier(aString, "array", packageName);
        Logger.getAnonymousLogger().info("Using image pack " + aString);
        return getResources().obtainTypedArray(resId);
    }

}
