package com.example.dinhnam.gridview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class List_Image extends AppCompatActivity {
    GridView gridView;
    GridViewAdapter adapter=null;
    ArrayList<ImageItem> model=new ArrayList<ImageItem>();
    String pathFolder=null;
    String date;
    int type;
    String pathMusic=null;
    public Bundle myBackupBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__image);
        myBackupBundle=savedInstanceState;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(List_Image.this, playMusic.class);
                //startActivity(intent);
                openMusic();
                //new Thread(showPicture).start();

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
    private void show(final int incr) {

        runOnUiThread(new Runnable() {
            public void run() {

                setContentView(R.layout.activity_show_image);
                ImageView imageView=(ImageView)findViewById(R.id.imageView);
                String path=model.get(incr).getPath();
                Bitmap bm= BitmapFactory.decodeFile(path);
                imageView.setImageBitmap(bm);
                Random rand = new Random();
                int r = rand.nextInt(9);
                Animation animation=null;
                Toast.makeText(List_Image.this,r+"",Toast.LENGTH_SHORT).show();
                switch (r){
                    case 0:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide);break;
                    case 1:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide1);break;
                    case 2:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide2);break;
                    case 3:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide3);break;
                    case 4:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide4);break;
                    case 5:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide5);break;
                    case 6:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.move);break;
                    case 7:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.fade);break;
                    case 8:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.anime);break;
                    case 9:  animation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide6);break;
                }
                imageView.startAnimation(animation);

            }
        });
        SystemClock.sleep(10000);

    }
    private Runnable showPicture=new Runnable() {
        @Override
        public void run() {
            MediaPlayer mediaPlayer;
            mediaPlayer = new  MediaPlayer();
            try {
                mediaPlayer.setDataSource(pathMusic);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for(int i=0;i<model.size();i++) {
                show(i);
            }
            mediaPlayer.stop();

        }

    };

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
    public ArrayList<music> getALlMusic(Activity activity){
        ArrayList<music> listmusic=new ArrayList<music>();
        music m;
        Uri uri;
        Cursor cursor;
        int column_index_data;
        String PathOfMusic=null;
        String NameMusic=null;
        uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Audio.Media.DATA};
        cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        while (cursor.moveToNext()) {
            PathOfMusic = cursor.getString(column_index_data);
            NameMusic= PathOfMusic.substring(PathOfMusic.lastIndexOf("/")+1);
            m=new music(NameMusic,PathOfMusic);
            listmusic.add(m);
        }
        return  listmusic;
    }
    public void openMusic(){
        setContentView(R.layout.activity_play_music);
        ListView listView;
        ArrayAdapter<music> adapter=null;
        final ArrayList<music> musicList=new ArrayList<music>();
        listView=(ListView)findViewById(R.id.listMusic);
        adapter=new ListMusicAdapter(this,R.layout.music_item,musicList);
        adapter.addAll(getALlMusic(this));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pathMusic= musicList.get(position).getPath();
                Toast.makeText(List_Image.this,pathMusic,Toast.LENGTH_SHORT).show();
                new  Thread(showPicture).start();
            }
        });
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

        return super.onOptionsItemSelected(item);
    }



}

