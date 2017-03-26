package com.galleryapp.gid.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.views.ImageSaveActivityFragments.ImageInfoFragmentPage;


public class ImageSaveActivity extends FragmentActivity implements ImageInfoFragmentPage.OnFragmentInteractionListener {


    private Bitmap displayedImage;
    private ImageView imageViewer;
    private Button btnSave;
    private Button btnRetake;
    private Button btnCancel;
    private Fragment imageInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_save);
        this.InitializeVariable();

        this.displayedImage = this.getIntent().getParcelableExtra("displayedImage");
        this.imageViewer.setImageBitmap(this.displayedImage);

    }

    private void InitializeVariable(){

        this.btnCancel = (Button) this.findViewById(R.id.btnCancel);
        this.btnRetake = (Button) this.findViewById(R.id.btnRetake);
        this.btnSave = (Button) this.findViewById(R.id.btnSave);
        this.imageViewer = (ImageView) this.findViewById(R.id.displayed_image_view);
        this.imageInfoFragment = (ImageInfoFragmentPage) this.getSupportFragmentManager().findFragmentById(R.id.location_info_image_fragment);

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.btnRetake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, Config.REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK
                        ,new Intent()
                                .putExtra("imageReviewResult",displayedImage)
                                .putExtra("imageCaption",((ImageInfoFragmentPage)imageInfoFragment).getCaption())
                                .putExtra("imageLattitude",((ImageInfoFragmentPage)imageInfoFragment).getLattitude())
                                .putExtra("imageLongitude",((ImageInfoFragmentPage)imageInfoFragment).getLongitude())
                                .putExtra("imageAccuracy",((ImageInfoFragmentPage)imageInfoFragment).getAccuration())
                );
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Config.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.displayedImage = imageBitmap;
            this.imageViewer.setImageBitmap(this.displayedImage);
        }
        else {
            Bundle extras = data.getExtras();
            ((ImageInfoFragmentPage)imageInfoFragment).setImageLattitudeValue((double) extras.get("LattitudeValue"));
            ((ImageInfoFragmentPage)imageInfoFragment).setImageLongitudeValue((double) extras.get("LongitudeValue"));
            ((ImageInfoFragmentPage)imageInfoFragment).setImagePositionAccurationValue((Float) extras.get("AccuracyValue"));
            ((ImageInfoFragmentPage)imageInfoFragment).setLocationInfo((String) extras.getString("locationAddress","Unknown"));
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        //Nothing To Do now,
    }
}
