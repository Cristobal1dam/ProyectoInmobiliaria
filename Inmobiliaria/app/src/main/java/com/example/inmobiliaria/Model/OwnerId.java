package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class OwnerId {
    private String id;
    private String picture;
    private String name;

    public OwnerId(String id, String picture, String name) {
        this.id = id;
        this.picture = picture;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerId)) return false;
        OwnerId ownerId = (OwnerId) o;
        return Objects.equals(id, ownerId.id) &&
                Objects.equals(picture, ownerId.picture) &&
                Objects.equals(name, ownerId.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, picture, name);
    }

    @Override
    public String toString() {
        return "OwnerId{" +
                "id='" + id + '\'' +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
