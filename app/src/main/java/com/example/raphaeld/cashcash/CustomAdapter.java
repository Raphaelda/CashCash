package com.example.raphaeld.cashcash;

import com.example.raphaeld.cashcash.ElementList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Raphaeld on 28/11/2017.
 */
public class CustomAdapter extends ArrayAdapter<ElementList> {

    private ArrayList<ElementList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtPrix;
        TextView txtDevise;
        TextView txtNote;
    }

    public CustomAdapter(ArrayList<ElementList> data, Context context) {
        super(context, R.layout.element, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ElementList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.element, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date);
            viewHolder.txtPrix = (TextView) convertView.findViewById(R.id.price);
            viewHolder.txtDevise = (TextView) convertView.findViewById(R.id.devise);
            viewHolder.txtNote = (TextView) convertView.findViewById(R.id.note);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }



        viewHolder.txtName.setText(dataModel.getNom());
        viewHolder.txtDate.setText(dataModel.getMydate());
        viewHolder.txtPrix.setText(dataModel.getPrix());
        viewHolder.txtDevise.setText(dataModel.getDevise());
        viewHolder.txtNote.setText(dataModel.getNote());

        // Return the completed view to render on screen
        return convertView;
    }
}