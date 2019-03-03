package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class AddPropiedad {

    private String title;
    private String description;
    private int price;
    private int rooms;
    private int size;
    private String categoryId;
    private String address;
    private String zipcode;
    private String city;
    private String province;
    private String loc;

    public AddPropiedad(String title, String description, int price, int rooms, int size, String categoryId, String address, String zipcode, String city, String province, String loc) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.size = size;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.province = province;
        this.loc = loc;
    }

    public AddPropiedad() {

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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddPropiedad that = (AddPropiedad) o;
        return price == that.price &&
                rooms == that.rooms &&
                size == that.size &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zipcode, that.zipcode) &&
                Objects.equals(city, that.city) &&
                Objects.equals(province, that.province) &&
                Objects.equals(loc, that.loc);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, rooms, size, categoryId, address, zipcode, city, province, loc);
    }

    @Override
    public String toString() {
        return "AddPropiedad{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", size=" + size +
                ", categoryId='" + categoryId + '\'' +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", loc='" + loc + '\'' +
                '}';
    }
}
