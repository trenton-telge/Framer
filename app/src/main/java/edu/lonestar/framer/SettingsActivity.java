/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.lonestar.framer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Switch;

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
        final Switch Adaptive_matting = findViewById(R.id.nameplateLabel);
        final Switch displayNameplateSwitch = findViewById(R.id.displayNameplateSwitch);
        final Button startButton = findViewById(R.id.startButton);
        // populating from shared preferences
        // had to use not because seemded to be on
        SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("adaptive_matting",false))
        {
            // moving the slider
            Adaptive_matting.toggle();
        }
        if (!preferences.getBoolean("display_nameplate",false))
        {
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

        Adaptive_matting.setChecked(sharedPref.getBoolean("adaptive_matting", false));
        Adaptive_matting.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor a = sharedPref.edit();
            // just saving whether the adaptive matting is on or not
            a.putBoolean("adaptive_matting", Adaptive_matting.isChecked());
            a.apply();

        });

        displayNameplateSwitch.setChecked(sharedPref.getBoolean("display_nameplate", false));
        displayNameplateSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor e = sharedPref.edit();
            // just saving the name plate and whether it was checked
            e.putBoolean("display_nameplate", displayNameplateSwitch.isChecked());
            e.apply();

        });
        //
        //
    }
}

