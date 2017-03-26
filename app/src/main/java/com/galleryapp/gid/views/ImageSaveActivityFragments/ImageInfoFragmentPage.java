package com.galleryapp.gid.views.ImageSaveActivityFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.galleryapp.gid.Config.Config;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.views.ImageSaveActivity;
import com.galleryapp.gid.views.MyLocationActivity;

import static android.app.Activity.RESULT_OK;


public class ImageInfoFragmentPage extends Fragment {

    private OnFragmentInteractionListener mListener;

    public View rootView;

    //ViewObject
    private ImageButton btnGetLocation;
    private EditText tbSetCaptionImage;
    private TextView tvLocationInfo;
    private double imageLattitudeValue;
    private double imageLongitudeValue;
    private float imagePositionAccurationValue;

    public double getImageLattitudeValue() {
        return imageLattitudeValue;
    }
    public void setImageLattitudeValue(double imageLattitudeValue) {
        this.imageLattitudeValue = imageLattitudeValue;
    }
    public double getImageLongitudeValue() {
        return imageLongitudeValue;
    }
    public void setImageLongitudeValue(double imageLongitudeValue) {
        this.imageLongitudeValue = imageLongitudeValue;
    }
    public float getImagePositionAccurationValue() {
        return imagePositionAccurationValue;
    }
    public void setImagePositionAccurationValue(float imagePositionAccurationValue) {
        this.imagePositionAccurationValue = imagePositionAccurationValue;
    }
    public void setLocationInfo(String info){
        if(this.tvLocationInfo != null){
            this.tvLocationInfo.setText(info);
        }
    }

    //fragmentInterfaces.
    public String getCaption(){
        return this.tbSetCaptionImage.getText().toString();
    }
    public Double getLattitude(){
        return this.imageLattitudeValue;
    }
    public Double getLongitude(){
        return this.imageLongitudeValue;
    }
    public Float getAccuration(){
        return this.imagePositionAccurationValue;
    }

    public ImageInfoFragmentPage() {
        // Required empty public constructor
    }
    public static ImageInfoFragmentPage newInstance(String param1, String param2) {
        ImageInfoFragmentPage fragment = new ImageInfoFragmentPage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.rootView = inflater.inflate(R.layout.fragment_image_info_fragment_page, container, false);
        this.InitializeVariable();
        return this.rootView;
    }

    private void InitializeVariable(){
        this.tvLocationInfo = (TextView) this.rootView.findViewById(R.id.tvLocationInfo);
        this.tbSetCaptionImage = (EditText)this.rootView.findViewById(R.id.tbImageCaption);
        this.btnGetLocation = (ImageButton)this.rootView.findViewById(R.id.btnGetLocation);

        this.btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myLocationActivity = new Intent(getContext(), MyLocationActivity.class);
                startActivityForResult(myLocationActivity, Config.REQUEST_LOCATION_VALUE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
