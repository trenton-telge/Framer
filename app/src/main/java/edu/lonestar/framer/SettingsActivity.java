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

public class SettingsActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // setting the id to the list view
        final ListView list_view_authors = (ListView) findViewById(R.id.list);
        // creating test string vector
        Vector<String> testing = new Vector<String>();
        testing.add("Hussein Syed");
        testing.add("Trenton Telge");
        testing.add("Allison Wykle");
        testing.add("Luong Luong");
        testing.add("1");
        testing.add("1");
        testing.add("1");
        testing.add("1");
        testing.add("1");
        testing.add("1");
        for (int x = 10; x<100;x++)
        {
            testing.add("1");
        }
        // creating the adapter object
        final Custom_adapter dataadapter = new Custom_adapter(this,testing);
                // setting the adapter
        list_view_authors.setAdapter(dataadapter);
    }
}
