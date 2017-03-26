package com.galleryapp.gid.models;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by noverio.joe on 3/22/17.
 */

public class ImageModel {

    private String caption;
    private Double locImgLattitude;
    private Double locImgLongitude;
    private float locImgLocationAccuration;
    private Bitmap image;

    public ImageModel() {}

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getCaption() {
        return caption;
    }
    public Bitmap getImage() {
        return image;
    }
    public float getLocImgLocationAccuration() {
        return locImgLocationAccuration;
    }
    public void setLocImgLocationAccuration(float locImgLocationAccuration) {
        this.locImgLocationAccuration = locImgLocationAccuration;
    }
    public Double getLocImgLongitude() {
        return locImgLongitude;
    }
    public void setLocImgLongitude(Double locImgLongitude) {
        this.locImgLongitude = locImgLongitude;
    }
    public Double getLocImgLattitude() {
        return locImgLattitude;
    }
    public void setLocImgLattitude(Double locImgLattitude) {
        this.locImgLattitude = locImgLattitude;
    }
}
