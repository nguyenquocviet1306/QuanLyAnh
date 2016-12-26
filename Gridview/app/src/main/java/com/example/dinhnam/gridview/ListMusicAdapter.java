package com.example.dinhnam.gridview;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DINHNAM on 12/25/2016.
 */

public class ListMusicAdapter extends ArrayAdapter<music> {
    public ListMusicAdapter(Context context, int resource, List<music> musics) {
        super(context, resource,musics);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.music_item, null);
        }
        music m = getItem(position);
        if ( m!= null) {
            TextView txt1 = (TextView) view.findViewById(R.id.textView);
            ImageView img = (ImageView)view.findViewById(R.id.imageViewMusic);
            img.setImageResource(R.drawable.musicicon);
            txt1.setTextColor(Color.WHITE);
            txt1.setText(m.getName());
        }
        return view;
    }

}
