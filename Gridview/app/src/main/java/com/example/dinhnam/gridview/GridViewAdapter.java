package com.example.dinhnam.gridview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DINHNAM on 11/16/2016.
 */

public class GridViewAdapter extends ArrayAdapter<ImageItem> {


    public GridViewAdapter(Context context, int resource, ArrayList<ImageItem> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.grid_item,null);
        }
        ImageItem imageItem=getItem(position);
        if(imageItem!=null){
            TextView imageTile=(TextView)view.findViewById(R.id.textViewItem);
            ImageView image=(ImageView)view.findViewById(R.id.imageViewItem);
            imageTile.setTextColor(Color.WHITE);
            imageTile.setText(imageItem.getTitle());
            image.setImageBitmap(imageItem.getImage());
        }
        return view;

    }

}
