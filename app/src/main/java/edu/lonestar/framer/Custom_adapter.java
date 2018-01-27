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
    static Vector<String> holder = new Vector<String>();

    Custom_adapter(Context context, Vector<ArtistSwitch> collector) {
        // resource the array itself
        // context is the background information
        // stuff your custom layout in here to be sent to each layout
         //connecting it after it gets passed
        super(context, R.layout.row, collector);
        this.names = collector;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row, parent, false);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        // just trying to set the text of the checkbox
        cb.setText(names.get(position).getName());
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
                holder.add(cb.getText().toString());
            }
            // deleting from the string vector
            else {
                holder.remove(cb.getText().toString());
            }
        }
    };

    private Vector<String> artistNames(){
        Vector<String> finalResults = new Vector<String>();
        for (RemoteImage i: DownloadDaemon.obunfiltered){
            if (!finalResults.contains(i.getArtist())){
                finalResults.add(i.getArtist());
            }
        }
        return finalResults;
    }

}






