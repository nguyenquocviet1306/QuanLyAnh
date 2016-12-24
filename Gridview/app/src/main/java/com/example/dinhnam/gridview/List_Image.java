package com.example.dinhnam.gridview;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class List_Image extends AppCompatActivity {
    GridView gridView;

    private int imageArra[] = {R.drawable.folder_icon,R.drawable.pic1};
    GridViewAdapter adapter=null;
    ArrayList<ImageItem> model=new ArrayList<ImageItem>();
    String pathFolder=null;
    String date;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "trình chiếu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        gridView=(GridView)findViewById(R.id.gridViewImage);
        adapter=new GridViewAdapter(this,R.layout.grid_item,model);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("folder");
        type=packageFromCaller.getInt("type");
        pathFolder = packageFromCaller.getString("pathFolder");
        //ViewPageAdapter adapter1 = new ViewPageAdapter(this,model.get().getPath() );
        if(type==0) {
            adapter.addAll(scanFolder(pathFolder));
            Toast.makeText(this,pathFolder,Toast.LENGTH_SHORT).show();
        }
        else
        if(type==1){
            date=pathFolder;
            adapter.addAll(getImgaDate(this,date));
            Toast.makeText(this,"ảnh ngày "+date,Toast.LENGTH_SHORT).show();
        }
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(List_Image.this, showImage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("path", model.get(position).getPath());
                    bundle.putString("title", model.get(position).getTitle());
                    intent.putExtra("image", bundle);
                    startActivity(intent);
            }
        });
    }
    public ArrayList<ImageItem> getImgaDate(Activity activity,String date) {//quet thu muc hoac anh
        ArrayList<ImageItem> images=new ArrayList<ImageItem>();
        ImageItem imageItem;
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String PathOfImage=null;
        String ImageName=null;
        File file;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);
                file = new File(PathOfImage);
                Date lastDate = new Date(file.lastModified());//date của image
                String temp=lastDate.toString();
                if(temp.equals(date)){//nếu trùng với date thì thêm vào
                    ImageName=PathOfImage.substring(PathOfImage.lastIndexOf("/")+1);
                    Bitmap bm=bitmapSampleSize(PathOfImage);
                    imageItem = new ImageItem(bm, ImageName, PathOfImage, false);
                    images.add(imageItem);
                }
            }
        return  images;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_time) {
            Toast.makeText(this,"time",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(List_Image.this, SlideShowActivity.class);
            startActivity(intent1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
