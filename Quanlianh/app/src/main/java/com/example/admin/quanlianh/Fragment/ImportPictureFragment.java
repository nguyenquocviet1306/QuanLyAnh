package com.example.admin.quanlianh.Fragment;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.os.EnvironmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.quanlianh.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImportPictureFragment extends Fragment {


    public ImportPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_import_picture, container, false);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent,CAMERA_REQUEST);
        return view;
    }

    public void onImageGalleryClicked(View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDicertory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDicertory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data,"image/*");
//        startActivityForResult(photoPickerIntent, Image.class.getModifiers());
    }

}
