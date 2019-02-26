package com.example.inmobiliaria.Model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavResponse {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("favs")
    @Expose
    private List<String> favs = null;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    /**
     * No args constructor for use in serialization
     *
     */
    public FavResponse() {
    }

    /**
     *
     * @param updatedAt
     * @param picture
     * @param id
     * @param v
     * @param keywords
     * @param email
     * @param createdAt
     * @param name
     * @param favs
     * @param role
     * @param password
     */
    public FavResponse(String role, List<String> favs, List<String> keywords, String id, String picture, String name, String email, String password, String createdAt, String updatedAt, Integer v) {
        super();
        this.role = role;
        this.favs = favs;
        this.keywords = keywords;
        this.id = id;
        this.picture = picture;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.v = v;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getFavs() {
        return favs;
    }

    public void setFavs(List<String> favs) {
        this.favs = favs;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }



}