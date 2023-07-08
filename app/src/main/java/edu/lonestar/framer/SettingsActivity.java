package edu.lonestar.framer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import edu.lonestar.framer.util.StringAdapter;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsActivity extends Activity {

    SharedPreferences sharedPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
// repopulate fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPref = this.getSharedPreferences("framer", Context.MODE_PRIVATE);
        // setting the id to the list view

        // settings id's
        final Switch adaptiveMattingSwitch = findViewById(R.id.mattingSwitch);
        final Switch displayNameplateSwitch = findViewById(R.id.displayNameplateSwitch);
        final Switch autoRotateSwitch = findViewById(R.id.autoRotateSwitch);
        final Button startButton = findViewById(R.id.startButton);
        // populating from shared preferences
        // had to use not because seemded to be on
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("adaptive_matting", false)) {
            // moving the slider
            adaptiveMattingSwitch.toggle();
        }
        if (!preferences.getBoolean("display_nameplate", false)) {
            // moving the slider
            displayNameplateSwitch.toggle();
        }
        startButton.setOnClickListener(v -> {
            final Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.systemui", "com.android.systemui.Somnambulator");
            startActivity(intent);
        });
        /*
        minutes = preferences.getInt("length_of_time",0);
        overscan_amount = preferences.getInt("overscan",0);
        */

        adaptiveMattingSwitch.setChecked(sharedPref.getBoolean("adaptive_matting", false));
        adaptiveMattingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor a = sharedPref.edit();
            // just saving whether the adaptive matting is on or not
            a.putBoolean("adaptive_matting", adaptiveMattingSwitch.isChecked());
            a.apply();
        });

        displayNameplateSwitch.setChecked(sharedPref.getBoolean("display_nameplate", false));
        displayNameplateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor e = sharedPref.edit();
            // just saving the name plate and whether it was checked
            e.putBoolean("display_nameplate", displayNameplateSwitch.isChecked());
            e.apply();
        });

        autoRotateSwitch.setChecked(sharedPref.getBoolean("auto_rotate", false));
        autoRotateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor e = sharedPref.edit();
            // just saving the name plate and whether it was checked
            e.putBoolean("auto_rotate", autoRotateSwitch.isChecked());
            e.apply();
        });

        final RecyclerView groupsList = findViewById(R.id.groupsList);
        final List<String> groupsArray = Arrays.asList(getResources().getStringArray(R.array.photogroups));
        groupsList.setAdapter(new StringAdapter(groupsArray));
        groupsList.setLayoutManager(new LinearLayoutManager(this));
        //
        //
    }
}
