package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class Photo {

            private String id;
            private String propertyId;
            private String imgurLink;
            private String deletehash;

    public Photo(String id, String propertyId, String imgurLink, String deletehash) {
        this.id = id;
        this.propertyId = propertyId;
        this.imgurLink = imgurLink;
        this.deletehash = deletehash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getImgurLink() {
        return imgurLink;
    }

    public void setImgurLink(String imgurLink) {
        this.imgurLink = imgurLink;
    }

    public String getDeletehash() {
        return deletehash;
    }

    public void setDeletehash(String deletehash) {
        this.deletehash = deletehash;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) &&
                Objects.equals(propertyId, photo.propertyId) &&
                Objects.equals(imgurLink, photo.imgurLink) &&
                Objects.equals(deletehash, photo.deletehash);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, propertyId, imgurLink, deletehash);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", imgurLink='" + imgurLink + '\'' +
                ", deletehash='" + deletehash + '\'' +
                '}';
    }
}
