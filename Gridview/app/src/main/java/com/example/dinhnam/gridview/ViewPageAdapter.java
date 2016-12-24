package com.example.dinhnam.gridview;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Admin on 12/22/2016.
 */

public class ViewPageAdapter extends PagerAdapter {

    Context context;
    int imageArray[];

    public ViewPageAdapter(Context context, int[] imageArray) {
        this.context = context;
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }
    public Object instantiateItem(View collection, int position) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setBackgroundResource(imageArray[position]);
        ((ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View)arg1);
    }
    @Override
    public Parcelable saveState() {
        return null;
    }
}
