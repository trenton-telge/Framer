package edu.lonestar.framer;

import android.graphics.ColorSpace;
import android.widget.ArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

import edu.lonestar.framer.util.ArtistSwitch;
import edu.lonestar.framer.*;
import edu.lonestar.framer.util.RemoteImage;

/**
 * Created by husse on 1/27/2018.
 */

public class Custom_adapter extends ArrayAdapter<ArtistSwitch> {
    // creating the string vector to populate it
    Vector<ArtistSwitch> names = new Vector<ArtistSwitch>();
    // just a vector for testing making an object later
    // saving the vector taking from the class
    static Vector<String> holder = new Vector<String>();
    // just add counter
    int x = -1;

    // creating a object vector to hold all the classes
    public static Vector<ArtistSwitch> saved_data_artists = new Vector<ArtistSwitch>();

    Custom_adapter(Context context, Vector<ArtistSwitch> collector) {
        // resource the array itself
        // context is the background information
        // stuff your custom layout in here to be sent to each layout
         //connecting it after it gets passed
        super(context, R.layout.row, collector);
        new DownloadDaemon().parseString();
        this.names = collector;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        // just trying to set the text of the checkbox
        //setting holder
        // holder is supposedn to be filled with object names so we will just add
        // put artist names to go back
       holder =artistNames();

        cb.setText(holder.get(position));
        // since only one instance of the custom adaptor just save the position
        cb.setOnCheckedChangeListener(mListener);
        return convertView;


    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // creating the new object just to grab access
            CheckBox cb = (CheckBox) buttonView.findViewById(R.id.checkBox1);
            // testing if checked then adding it to the string value
            if (isChecked) {
                // adding the data to the vector
                // finding the data in a loop
                // resetting x
                x= -1;
                for(ArtistSwitch finding_name : saved_data_artists )
                {
                        // counter
                    x++;
                    if (finding_name.getName().equals(cb.getText().toString() ))
                    {
                        // changing it to true for the variable
                        finding_name.selected = true;
                        // changing the actual true variable
                        saved_data_artists.get(x).selected= true;

                    }
                }


            }else
            {
                // resetting counter
                x = -1;
                for(ArtistSwitch finding_name : saved_data_artists )
                {
                    // counter
                    x++;
                    if (finding_name.getName().equals(cb.getText().toString() ))
                    {
                        // changing it to true for the variable
                        finding_name.selected = true;
                        // changing the actual true variable
                        saved_data_artists.get(x).selected= false;

                    }
                }
            }
        }
    };
// method to return artists auto creates them
    // we must store them in a object
    private Vector<String> artistNames(){
        Vector<String> finalResults = new Vector<String>();
        for (RemoteImage i: DownloadDaemon.obunfiltered){
            if (!finalResults.contains(i.getArtist())){
                finalResults.add(i.getArtist());
                // populating just names
                ArtistSwitch new_addition = new ArtistSwitch(i.getArtist());
                //adding to the vector
                saved_data_artists.add(new_addition);
            }
        }
        return finalResults;
    }

}






