package edu.lonestar.framer.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import edu.lonestar.framer.R;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {
    private final List<String> stringList;

    public StringAdapter(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);
        return new StringViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        String string = stringList.get(position);
        holder.bind(string);
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    static class StringViewHolder extends RecyclerView.ViewHolder {


        SharedPreferences sharedPref;
        private final CheckBox checkbox;
        private final TextView textView;

        private final Context context;

        public StringViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            textView = itemView.findViewById(R.id.text_view);
            this.context = context;
        }

        public void bind(String string) {
            textView.setText(string);

            sharedPref = context.getSharedPreferences("framer", Context.MODE_PRIVATE);
            Set<String> activeSet = sharedPref.getStringSet("active_groups", new HashSet<>());
            checkbox.setChecked(activeSet.contains(string));
            checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SharedPreferences.Editor e = sharedPref.edit();
                // just saving the name plate and whether it was checked
                Set<String> previousSet = new HashSet<>(sharedPref.getStringSet("active_groups", new HashSet<>()));
                if (isChecked) {previousSet.add(string);} else {previousSet.remove(string);}
                e.putStringSet("active_groups", previousSet);
                Logger.getLogger("bind").warning(previousSet.toString());
                e.apply();
            });
            // Set checkbox state or handle checkbox clicks here
        }
    }
}