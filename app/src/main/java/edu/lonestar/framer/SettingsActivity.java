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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

import edu.lonestar.framer.util.ArtistSwitchModel;
import edu.lonestar.framer.util.RemoteImage;

import static java.lang.reflect.Array.getInt;

public class SettingsActivity extends Activity {

    SharedPreferences sharedPref;
    ArtistListAdapter dataadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
// repopulate fields
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPref = this.getSharedPreferences("framer", Context.MODE_PRIVATE);
        // setting the id to the list view
        final ListView list_view_artists = (ListView) findViewById(R.id.list);

        // creating the adapter object
        dataadapter = new ArtistListAdapter(this, refreshArtistSwitchVector());
                // setting the adapter
        new DownloadDaemon().parseString();

        list_view_artists.setAdapter(dataadapter);
        // settings id's
        final Switch Adaptive_matting = findViewById(R.id.switch2);
        final Switch displayNameplateSwitch = findViewById(R.id.displayNameplateSwitch);
        final EditText minutes_changes = findViewById(R.id.editText);
        final EditText get_overscan = findViewById(R.id.editText3);
        final TextView displayname_test = findViewById(R.id.displayNameplateSwitchLabel);
        final TextView matting = findViewById(R.id.textView3);
        final TextView delay = findViewById(R.id.textView4);
        final TextView set_overscan = findViewById(R.id.textView2);
        final TextView title = findViewById(R.id.textView);
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
        int minutes = preferences.getInt("length_of_time",0);
        int overscan_amount = preferences.getInt("overscan",0);

        // setting the overscan
        minutes_changes.setText(String.valueOf(minutes));
        get_overscan.setText(String.valueOf(overscan_amount));



        // setting listiners
        get_overscan.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor c = sharedPref.edit();
                // just saving whether the adaptive matting is on or not
                // converting the number
                int amount = Integer.parseInt(get_overscan.getText().toString());
                c.putInt("overscan",amount);
                c.apply();

            }
        });

        minutes_changes.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                SharedPreferences.Editor t = sharedPref.edit();
                // just saving whether the adaptive matting is on or not
                // converting the number
                int number = Integer.parseInt(minutes_changes.getText().toString());
                t.putInt("length_of_time",number );
                t.apply();

            }
        });
        Adaptive_matting.setChecked(sharedPref.getBoolean("adaptive_matting", false));
        Adaptive_matting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor a = sharedPref.edit();
                // just saving whether the adaptive matting is on or not
                a.putBoolean("adaptive_matting", Adaptive_matting.isChecked());
                a.apply();

            }
        });

        displayNameplateSwitch.setChecked(sharedPref.getBoolean("display_nameplate", false));
        displayNameplateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor e = sharedPref.edit();
                // just saving the name plate and whether it was checked
                e.putBoolean("display_nameplate", displayNameplateSwitch.isChecked());
                e.apply();

            }
        });
        //
        //
    }
// returning the actual vector

    public static Vector<ArtistSwitchModel> refreshArtistSwitchVector(){
        Vector<ArtistSwitchModel> finalArtists = new Vector<>();
        for (RemoteImage i: DownloadDaemon.obunfiltered){
            boolean found = false;
            for (ArtistSwitchModel s : finalArtists){
                if (s.name.equals(i.getArtist())){
                    found = true;
                }
            }
            if (!found){
                finalArtists.add(new ArtistSwitchModel(i.getArtist()));
            }
        }
        return finalArtists;
    }
}

