package com.dciets.androidutils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dciets.androidutils.eyecandy.CircleTransform;
import com.dciets.androidutils.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by marc-antoinehinse on 2015-10-10.
 */
public class CustomArrayAdapter extends ArrayAdapter<Object> {
    private Context mContext;
    private LayoutInflater mInflater = null;

    private ArrayList<Object> roommates;

    public CustomArrayAdapter(Context context, ArrayList<Object> roommates) {
        super(context, R.layout.roommate_item, roommates);
        mContext = context;
        this.roommates = roommates;
    }

    static class ViewHolder {
        public TextView name;
        public ImageView avatar;
        public CheckBox checkBox;
    }

    private LayoutInflater getInflater(){
        if(mInflater == null)
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return mInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if(convertView == null){ // Only inflating if necessary is great for performance
            rowView = getInflater().inflate(R.layout.roommate_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.name = (TextView) rowView.findViewById(R.id.name);
            holder.avatar = (ImageView) rowView.findViewById(R.id.avatar);
            holder.checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
            rowView.setTag(holder);
        } else{
            rowView = convertView;
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        Object r = getItem(position);

        holder.name.setText("Name");

        Picasso.with(getContext()).load("http://static.independent.co.uk/s3fs-public/thumbnails/image/2013/01/24/12/v2-cute-cat-picture.jpg").transform(new CircleTransform()).into(holder.avatar);

        return rowView;
    }
}