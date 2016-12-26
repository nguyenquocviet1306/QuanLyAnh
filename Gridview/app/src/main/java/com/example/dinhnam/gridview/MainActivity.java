package com.example.dinhnam.gridview;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    GridViewAdapter adapter=null;
    ArrayList<ImageItem> model=new ArrayList<ImageItem>();
    int type=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabMain);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "trình chiếu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        gridView=(GridView)findViewById(R.id.gridView);
        adapter=new GridViewAdapter(this,R.layout.grid_item,model);
        adapter.addAll(getAllFolder(this,type));
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type<=1) {//truyen thong tin image cho show image
                    Intent intent = new Intent(MainActivity.this, List_Image.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("type", type);
                    if(type==0) {
                        bundle.putString("pathFolder", model.get(position).getPath());
                    }
                    if(type==1){
                        bundle.putString("pathFolder", model.get(position).getTitle());
                    }
                    intent.putExtra("folder", bundle);
                    startActivity(intent);
                }
                else
                    if(type==2){
                        Intent intent = new Intent(MainActivity.this, showImage.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", type);
                        bundle.putString("path", model.get(position).getPath());
                        bundle.putString("title", model.get(position).getTitle());
                        intent.putExtra("image", bundle);
                        startActivity(intent);
                    }

            }
        });
    }
    public ArrayList<ImageItem> getAllFolder(Activity activity,int mode) {//quet thu muc hoac anh
        ArrayList<ImageItem> images=new ArrayList<ImageItem>();
        ImageItem imageItem;
        Boolean add=true;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage=null;
        String PathOfFoder=null;
        String FolderName=null;
        String ImageName=null;
        File file;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        if(mode==0) {
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);
                file = new File(PathOfImage);
                PathOfFoder = file.getParent();
                for (int i = 0; i < images.size(); i++) {//kiem tra xem da ton tai folder chua
                    if (images.get(i).getPath().equals(PathOfFoder)) {
                        add = false;
                    }
                }
                if (add) {
                    FolderName = cursor.getString(column_index_folder_name);
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.folder_icon);
                    imageItem = new ImageItem(bm, FolderName, PathOfFoder, true);
                    images.add(imageItem);
                }
                add = true;
            }
        }
        else
        if(mode==1){
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);
                file = new File(PathOfImage);
                int j=0;
                Date lastDate = new Date(file.lastModified());//date của image
                for(int i=0;i<images.size();i++){//kiem tra da ton tai date hay chua
                    String date_i=images.get(i).getTitle();
                    String date=lastDate.toString();
                    if(date.equals(date_i)){
                        add=false;
                        break;
                    }
                }
                if(add) {//neu chua thi them vao arraylist
                    for (int i = 0; i < images.size(); i++) {//xep xep theo thu tu giam dan
                        File file1 = new File(images.get(i).getPath());
                        Date lastDate_i = new Date(file1.lastModified());
                        if (lastDate.before(lastDate_i)) {
                            j = i + 1;
                        }
                    }
                    //Bitmap bm = bitmapSampleSize(PathOfImage);
                    Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.folder_icon);
                    imageItem = new ImageItem(bm, lastDate.toString(), PathOfImage, false);
                    images.add(j, imageItem);
                }
                add=true;

            }
        }
        else
        if(mode==2){
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);
                file = new File(PathOfImage);
                int j=0;
                Date lastDate = new Date(file.lastModified());//date của image
                for(int i=0;i<images.size();i++){//nếu date nhỏ hơn thì xếp sau
                    File file1=new File(images.get(i).getPath());
                    Date lastDate_i = new Date(file1.lastModified());
                    if(lastDate.before(lastDate_i)){
                        j=i+1;
                    }
                }
                ImageName=PathOfImage.substring(PathOfImage.lastIndexOf("/")+1);
                Bitmap bm=bitmapSampleSize(PathOfImage);
                imageItem = new ImageItem(bm, ImageName, PathOfImage, false);
                images.add(j,imageItem);
            }
        }

        return  images;
    }

    public Bitmap bitmapSampleSize(String path){
        int maxWidth=100;
        int maxHeight=100;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //Chỉ đọc thông tin ảnh, không đọc dữ liwwuj
        BitmapFactory.decodeFile(path, options); //Đọc thông tin ảnh
        int Height= options.outHeight;
        int Width = options.outWidth;
        int k=Math.max(Height/maxHeight,Width/maxWidth);
        options.inSampleSize = k; //Scale bitmap xuống k lần
        options.inJustDecodeBounds = false; //Cho phép đọc dữ liệu ảnh ảnh
        return BitmapFactory.decodeFile(path, options);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_folder) {
            type=0;
            adapter.clear();
            adapter.addAll(getAllFolder(this,0));
            gridView.setAdapter(adapter);
            Toast.makeText(this,"folder",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_time) {
            type=1;
            adapter.clear();
            adapter.addAll(getAllFolder(this,1));
            gridView.setAdapter(adapter);
            Toast.makeText(this,"folder time",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.action_all) {
            type=2;
            adapter.clear();
            adapter.addAll(getAllFolder(this,2));
            gridView.setAdapter(adapter);
            Toast.makeText(this,"time line",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
