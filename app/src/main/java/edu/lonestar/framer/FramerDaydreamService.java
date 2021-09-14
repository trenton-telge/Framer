package edu.lonestar.framer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FramerDaydreamService extends DreamService {
    SharedPreferences sharedPref;
    static boolean wantsAdaptive = false;
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // Exit dream upon user touch?
        setInteractive(false);

        // Hide system UI?
        setFullscreen(true);

        // Keep screen at full brightness?
        setScreenBright(true);

        setContentView(R.layout.framer_daydream);
        sharedPref = getApplication().getSharedPreferences("framer", Context.MODE_PRIVATE);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        //displayNewImage();
        //TODO set matting size due to overscan
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();
        }
        Point size = new Point();
        if (display != null) {
            display.getSize(size);
        }
        assert display != null;
        findViewById(R.id.bufferLeft).setMinimumWidth(findViewById(R.id.bufferLeft).getWidth()+(size.x*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferRight).setMinimumWidth(findViewById(R.id.bufferRight).getWidth()+(size.x*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferTop).setMinimumHeight(findViewById(R.id.bufferTop).getHeight()+(size.y*sharedPref.getInt("overscan", 0)/200));
        findViewById(R.id.bufferBottom).setMinimumHeight(findViewById(R.id.bufferBottom).getHeight()+(size.y*sharedPref.getInt("overscan", 0)/200));
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
            ((ImageView)findViewById(R.id.imageView)).setImageResource(R.drawable.framer_banner);
            final Handler handler = new Handler();
            handler.postDelayed(this::displayNewImage, 2000);
        if (sharedPref.getBoolean("display_nameplate", false)) {
            findViewById(R.id.nameplateLayout).setVisibility(View.VISIBLE);
        } else findViewById(R.id.nameplateLayout).setVisibility(View.INVISIBLE);
        wantsAdaptive = sharedPref.getBoolean("adaptive_matting", false);
    }

    public int calculateAverageColor(android.graphics.Bitmap bitmap, int pixelSpacing) {
        int R = 0; int G = 0; int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i += pixelSpacing) {
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        return Color.rgb(R / n, G / n, B / n);
    }

}
