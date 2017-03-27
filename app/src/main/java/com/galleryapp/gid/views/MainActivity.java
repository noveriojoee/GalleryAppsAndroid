package com.galleryapp.gid.views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.galleryapp.gid.Adapters.ContentImageListAdapter;
import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.models.ImageModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContentImageListAdapter adapter;
    private ArrayList<ImageModel> listOfImage;
    private RecyclerView recyclerView;

    private BottomNavigationView bottomNavigationMenu;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_take_picture:
                    OnCameraBottomNavigationClicked();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.InitiateEventListener();
    }

    private void InitiateEventListener(){
        this.listOfImage = new ArrayList<ImageModel>();

        this.recyclerView = (RecyclerView)this.findViewById(R.id.ImageList);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        this.adapter = new ContentImageListAdapter(this,this.listOfImage);
        this.recyclerView.setAdapter(adapter);

        //Initiate Bottom Navigation begin
        this.bottomNavigationMenu = (BottomNavigationView)findViewById(R.id.main_bottom_navigation);
        this.bottomNavigationMenu.setOnNavigationItemSelectedListener(this.mOnNavigationItemSelectedListener);
        //Initiate Bottom Navigation end
    }

    private void OnCameraBottomNavigationClicked(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            this.askForPermission(Manifest.permission.CAMERA,Config.PERMISSION_CAMERA);
        }else{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, Config.REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Intent imageReviewActivity = new Intent(this, ImageSaveActivity.class);
            imageReviewActivity.putExtra("displayedImage",imageBitmap);

            startActivityForResult(imageReviewActivity,Config.REQUEST_IMAGE_REVIEW);
        }
        else if(requestCode == Config.REQUEST_IMAGE_REVIEW && resultCode == Activity.RESULT_OK){
            Bitmap imageReviewResult = data.getParcelableExtra("imageReviewResult");
            String imageCaption = data.getStringExtra("imageCaption");
            Double imageLattitude = data.getDoubleExtra("imageLattitude",0);
            Double imageLongitude = data.getDoubleExtra("imageLongitude",0);
            Float imagePositionAccuration = data.getFloatExtra("imageAccuracy",0);
            ImageModel dataImage = new ImageModel();

            dataImage.setImage(imageReviewResult);
            dataImage.setCaption(imageCaption);
            dataImage.setLocImgLocationAccuration(imagePositionAccuration);
            dataImage.setLocImgLattitude(imageLattitude);
            dataImage.setLocImgLongitude(imageLongitude);

            this.listOfImage.add(dataImage);
            this.adapter.notifyDataSetChanged();
        }
    }

    //Implements Asking for permissions runtime.
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    //Implements onPermissions Request Result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {
                //Camera
                case 5:
                    this.OnCameraBottomNavigationClicked();
                    break;
            }

            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    }