package com.example.spectrus.stockide.AppObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.spectrus.stockide.R;

import java.util.ArrayList;

/**
 * QuoteAdapter needed for listing.
 */

public class QuoteAdapter extends ArrayAdapter<FoundQuote> {

    // Variables needed for the Adapter
    Context context;
    int layoutId;
    ArrayList<FoundQuote> data;

    // Constructor
    public QuoteAdapter(Context context, int layoutId, ArrayList<FoundQuote> data) {
        super(context, layoutId);
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        QuoteDisplay holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new QuoteDisplay();
            holder.text_00 = (TextView)row.findViewById(R.id.text_000);
            holder.text_01 = (TextView)row.findViewById(R.id.text_001);
            holder.text_02 = (TextView)row.findViewById(R.id.text_002);
            row.setTag(holder);
        } else {
            holder = (QuoteDisplay) row.getTag();
        }

        // Display the Quote elements
        FoundQuote fQuote = data.get(position);
        holder.text_00.setText(fQuote.symbol);
        holder.text_01.setText(fQuote.name);
        holder.text_02.setText(fQuote.exchange);
        return row;
    }

    // Display Elements
    static class QuoteDisplay {
        TextView text_00;
        TextView text_01;
        TextView text_02;
    }

}
