package com.example.inmobiliaria.Model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class Propiedad {

    private String id;
    private String title;
    private String description;
    private int price;
    private int rooms;
    private Category categoryId;
    private String address;
    private String zipcode;

    public Propiedad(String id, String title, String description, int price, int rooms, Category categoryId, String address, String zipcode) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.rooms = rooms;
        this.categoryId = categoryId;
        this.address = address;
        this.zipcode = zipcode;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Propiedad propiedad = (Propiedad) o;
        return Double.compare(propiedad.price, price) == 0 &&
                rooms == propiedad.rooms &&
                Objects.equals(id, propiedad.id) &&
                Objects.equals(title, propiedad.title) &&
                Objects.equals(description, propiedad.description) &&
                Objects.equals(categoryId, propiedad.categoryId) &&
                Objects.equals(address, propiedad.address) &&
                Objects.equals(zipcode, propiedad.zipcode);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, price, rooms, categoryId, address, zipcode);
    }

    @Override
    public String toString() {
        return "Propiedad{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                ", categoryId=" + categoryId +
                ", address='" + address + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
