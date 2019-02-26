package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;
import java.util.Objects;

public class PropiedadFoto {
    private String id;
    private String title;
    private String description;
    private int price;
    private int rooms;
    private Category categoryId;
    private String address;
    private String zipcode;
    private String city;
    private String province;
    private String loc;
    private OwnerId ownerId;
    private List<String> photos;

    public PropiedadFoto() { }

    public PropiedadFoto(String id, String title, String description, int price, int rooms, Category categoryId, String address, String zipcode, String city, String province, String loc, OwnerId ownerId, List<String> photos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
        this.ownerId = ownerId;
        this.photos = photos;
    }

    public void setPropiedad(Propiedad propiedad){
        this.id = propiedad.getId();
        this.title = propiedad.getTitle();
        this.description = propiedad.getDescription();
        this.price = propiedad.getPrice();
        this.rooms = propiedad.getRooms();
        this.categoryId = propiedad.getCategoryId();
        this.address = propiedad.getAddress();
        this.zipcode = propiedad.getZipcode();
        this.city = propiedad.getCity();
        this.province = propiedad.getProvince();
        this.loc = propiedad.getLoc();
        this.ownerId = propiedad.getOwnerId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
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
        if (!(o instanceof PropiedadFoto)) return false;
        PropiedadFoto that = (PropiedadFoto) o;
        return price == that.price &&
                rooms == that.rooms &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zipcode, that.zipcode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(province, that.province) &&
                Objects.equals(loc, that.loc) &&
                Objects.equals(ownerId, that.ownerId) &&
                Objects.equals(photos, that.photos);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, rooms, categoryId, address, zipcode, city, province, loc, ownerId, photos);
    }

    @Override
    public String toString() {
        return "PropiedadFoto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", categoryId=" + categoryId +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", loc='" + loc + '\'' +
                ", ownerId=" + ownerId +
                ", photos=" + photos +
                '}';
    }
}
