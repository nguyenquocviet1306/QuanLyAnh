package com.example.dinhnam.gridview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.sql.Date;

public class showImage extends AppCompatActivity {
    private static final String TAG = showImage.class.toString();
    ImageView imageView;
    ShareDialog shareDialog;
    //Button sharebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        shareDialog = new ShareDialog(this);
        //sharebt = (Button) findViewById(R.id.bt_share);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=(ImageView)findViewById(R.id.imageView);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("image");
        String path=packageFromCaller.getString("path");
        final File file = new File(path);
        Date lastModDate = new Date(file.lastModified());
        final Bitmap bm= BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bm);
        Toast.makeText(this,path+"\n"+lastModDate.toString(),Toast.LENGTH_SHORT).show();
//        sharebt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "clicked");
//                if (ShareDialog.canShow(SharePhotoContent.class)) {
//                    //Bitmap image = BitmapFactory.decodeResource(getResources(),id);
//                    SharePhoto photo = new SharePhoto.Builder()
//                            .setBitmap(bm)
//                            .build();
//
//                    SharePhotoContent content = new SharePhotoContent.Builder()
//                            .addPhoto(photo)
//                            .build();
//
//                    ShareApi.share(content, null);
//                    shareDialog.show(content);
//
//
//                }
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

            Toast.makeText(this,"share",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "clicked");
            if (ShareDialog.canShow(SharePhotoContent.class)) {
                //Bitmap image = BitmapFactory.decodeResource(getResources(),id);
                Intent callerIntent=getIntent();
                Bundle packageFromCaller= callerIntent.getBundleExtra("image");
                String path=packageFromCaller.getString("path");
                final Bitmap bm= BitmapFactory.decodeFile(path);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bm)
                        .build();

                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();

                ShareApi.share(content, null);
                shareDialog.show(content);


            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}




