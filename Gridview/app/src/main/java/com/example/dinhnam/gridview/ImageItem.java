package com.example.dinhnam.gridview;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by DINHNAM on 11/16/2016.
 */

public class ImageItem {
    private Bitmap image;
    private String title;
    private String path;
    private boolean isFolder;
    public ImageItem(Bitmap image, String title, String path, boolean isfolder) {
        super();
        this.image = image;
        this.title = title;
        this.path=path;
        isFolder=isfolder;

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath(){
        return path;
    }
    public Boolean getisFolder(){
        return isFolder;
    }

}
