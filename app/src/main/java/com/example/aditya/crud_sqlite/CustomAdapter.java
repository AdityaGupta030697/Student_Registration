package com.example.aditya.crud_sqlite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aditya on 01-06-2017.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<Student> listname;


    Context mContext;

    public CustomAdapter(Context mContext, ArrayList<Student> listname) {
        this.mContext = mContext;

        this.listname = listname;

    }

    @Override
    public int getCount() {
        return listname.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.list_adapter, parent, false);


        //String[] separated = listname.get(position).split("-");
        String name = listname.get(position).getName();





        TextView addresstext = (TextView) row.findViewById(R.id.text_view);

        addresstext.setText(name);
        return row;
    }
}

