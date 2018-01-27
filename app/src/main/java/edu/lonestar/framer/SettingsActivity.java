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
import android.os.Bundle;
import android.widget.ListView;

import java.util.Vector;

import edu.lonestar.framer.util.ArtistSwitch;

public class SettingsActivity extends Activity {
    Custom_adapter dataadapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // setting the id to the list view
        final ListView list_view_authors = (ListView) findViewById(R.id.list);
        // creating test string vector
        Vector<ArtistSwitch> testing = new Vector<ArtistSwitch>();

        for (int x = 10; x<100;x++)
        {
            ArtistSwitch tester = new ArtistSwitch("Hussein",true);
            testing.add(tester);
        }
        // creating the adapter object
        dataadapter = new Custom_adapter(this,testing);
                // setting the adapter
        list_view_authors.setAdapter(dataadapter);
    }
}
