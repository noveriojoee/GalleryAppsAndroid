package com.galleryapp.gid.views.MasterActivityFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.firebase.client.Firebase;
import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.Adapters.ContentImageListAdapter;
import com.galleryapp.gid.models.ImageModel;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by noverio.joe on 3/6/17.
 */

@SuppressLint("ValidFragment")
public class CameraFragmentPage extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private AppCompatActivity invokerActivity;
    private View rootView;
    private Button btnTookPhoto;
    private ImageView mImageView;
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> listOfImage;
    private ContentImageListAdapter adapter;
    private Firebase cloudReferences;

    public CameraFragmentPage(AppCompatActivity activity){
        this.invokerActivity = activity;
        this.listOfImage = new ArrayList<ImageModel>();
    }

    private void InitiateEventListener(){
        if (this.rootView != null){
            this.cloudReferences.setAndroidContext(this.getContext());
            this.btnTookPhoto = (Button)this.rootView.findViewById(R.id.btnTookPhoto);
            this.mImageView = (ImageView)this.rootView.findViewById(R.id.IVFotoResult);
            this.recyclerView = (RecyclerView)this.rootView.findViewById(R.id.imagegallery);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            this.adapter = new ContentImageListAdapter(getContext(),this.listOfImage);
            this.recyclerView.setAdapter(adapter);

            this.btnTookPhoto.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(invokerActivity.getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            });
        }
    }

    private void SaveToCloud(ImageModel image){
        this.cloudReferences = new Firebase(Config.FIREBASE_URL);
        this.cloudReferences.child("data_galery").setValue(image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_camera_master_activity, container, false);
        this.InitiateEventListener();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            String Caption = Integer.toString(this.listOfImage.size());
            ImageModel dataImage = new ImageModel();
            dataImage.setImage(imageBitmap);
            dataImage.setCaption("Image ke "+Caption);

            mImageView.setImageBitmap(imageBitmap);
            this.listOfImage.add(dataImage);

            this.adapter.notifyDataSetChanged();
        }
    }


}
