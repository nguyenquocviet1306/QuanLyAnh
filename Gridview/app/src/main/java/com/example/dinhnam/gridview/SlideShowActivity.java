package com.example.dinhnam.gridview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Date;
import java.util.ArrayList;

public class SlideShowActivity extends AppCompatActivity {
    ViewPager myPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);


        //ViewPageAdapter adapter1 = new ViewPageAdapter(this,imageArra );
        //myPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        //myPager.setAdapter(adapter1);
        //myPager.setCurrentItem(2);
    }

    public ArrayList<ImageItem> scanFolder(String path){//quét cách ảnh có trong thư  mục
        ArrayList<ImageItem> images=new ArrayList<ImageItem>();
        File file=new File(path);
        File[] listFileImage;
        ImageItem imageItem;
        if(file.isDirectory()){
            listFileImage=file.listFiles(
                    new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String name) {
                            return name.contains(".png")||name.contains(".jpg");
                        }
                    }
            );//them list cac image trong thu muc
            for(int i=0;i<listFileImage.length;i++){
                String PathOfImage=listFileImage[i].getAbsolutePath();
                String ImageName=PathOfImage.substring(PathOfImage.lastIndexOf("/")+1);
                Bitmap bm=bitmapSampleSize(PathOfImage);
                Date lastDate = new Date(file.lastModified());
                imageItem = new ImageItem(bm, ImageName, PathOfImage, false);
                images.add(imageItem);
            }
        }
        return images;
    }

    public Bitmap bitmapSampleSize(String path){
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
        //BitmapFactory.decodeFile(path, options); //Đọc thông tin ảnh
        options.inSampleSize = 16; //Scale bitmap xuống 16 lần
        options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
        bm=BitmapFactory.decodeFile(path, options);
        return bm;
    }
}
