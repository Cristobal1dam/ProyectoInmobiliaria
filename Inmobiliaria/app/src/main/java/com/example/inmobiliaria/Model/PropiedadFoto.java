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
    private boolean isFav;

    public PropiedadFoto() { }

    public PropiedadFoto(String id, String title, String description, int price, int rooms, Category categoryId, String address, String zipcode, String city, String province, String loc, OwnerId ownerId, List<String> photos, boolean isFav) {
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
        this.isFav = isFav;
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

    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropiedadFoto)) return false;
        PropiedadFoto that = (PropiedadFoto) o;
        return getPrice() == that.getPrice() &&
                getRooms() == that.getRooms() &&
                isFav() == that.isFav() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCategoryId(), that.getCategoryId()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getZipcode(), that.getZipcode()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getProvince(), that.getProvince()) &&
                Objects.equals(getLoc(), that.getLoc()) &&
                Objects.equals(getOwnerId(), that.getOwnerId()) &&
                Objects.equals(getPhotos(), that.getPhotos());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getPrice(), getRooms(), getCategoryId(), getAddress(), getZipcode(), getCity(), getProvince(), getLoc(), getOwnerId(), getPhotos(), isFav());
    }

    @Override
    public String
    toString() {
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
                ", isFav=" + isFav +
                '}';
    }
}
