package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class ResponseNear {
    private String title;
    private String description;
    private int price;
    private int rooms;
    private int size;
    private OwnerId ownerId;
    private String city;
    private int zipcode;
    private String province;
    private Category categoryId;
    private String id;
    private String loc;
    private List<String> photos;

    public ResponseNear(String title, String description, int price, int rooms, int size, OwnerId ownerId, String city, int zipcode, String province, Category categoryId, String id, String loc, List<String> photos) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.size = size;
        this.ownerId = ownerId;
        this.city = city;
        this.zipcode = zipcode;
        this.province = province;
        this.categoryId = categoryId;
        this.id = id;
        this.loc = loc;
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseNear)) return false;
        ResponseNear that = (ResponseNear) o;
        final boolean b = price == that.price &&
                rooms == that.rooms &&
                size == that.size &&
                zipcode == that.zipcode &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(city, that.city) &&
                Objects.equals(province, that.province) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(id, that.id) &&
                Objects.equals(loc, that.loc) &&
                Objects.equals(photos, that.photos);
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, rooms, size, ownerId, city, zipcode, province, categoryId, id, loc, photos);
    }

    @Override
    public String toString() {
        return "ResponseNear{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", size=" + size +
                ", ownerId=" + ownerId +
                ", city='" + city + '\'' +
                ", zipcode=" + zipcode +
                ", province='" + province + '\'' +
                ", categoryId=" + categoryId +
                ", id='" + id + '\'' +
                ", loc='" + loc + '\'' +
                ", photos=" + photos +
                '}';
    }
}
