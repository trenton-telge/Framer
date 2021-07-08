package edu.lonestar.framer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Vector;

import edu.lonestar.framer.util.ArtistSwitchModel;

/**
 * Created by husse on 1/27/2018.
 */

public class ArtistListAdapter extends ArrayAdapter<ArtistSwitchModel> {

    private int lastPosition = -1;

    Vector data;

    private class ViewHolder {
        private TextView artistNameView = null;
        private CheckBox artistCheckBox = null;
    }

    ArtistListAdapter(Context context, Vector<ArtistSwitchModel> data) {
        super(context, R.layout.row, data);
        this.data = data;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View localConvertView = convertView;
        // Get the data item for this position
        ArtistSwitchModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;// view lookup cache stored in tag

        if (localConvertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            localConvertView = inflater.inflate(R.layout.row, parent, false);
            viewHolder.artistNameView = localConvertView.findViewById(R.id.artistName);
            viewHolder.artistCheckBox = localConvertView.findViewById(R.id.artistCheck);

            localConvertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) localConvertView.getTag();
        }

        lastPosition = position;

        if (dataModel != null) {
            viewHolder.artistNameView.setText(dataModel.name);
            viewHolder.artistCheckBox.setChecked(dataModel.selected);
        }
        // Return the completed view to render on screen
        return localConvertView;


    }



}






