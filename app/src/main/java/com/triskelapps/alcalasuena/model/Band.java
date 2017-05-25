package com.triskelapps.alcalasuena.model;

import android.net.Uri;

import com.triskelapps.alcalasuena.api.common.ApiClient;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by julio on 18/05/17.
 */

public class Band extends RealmObject {

    public static final String NAME = "name";

    @PrimaryKey private int id;
    private String genre;
    private Tag tag;
    private String profile_image;
    private String description;
    private String name;
    private String embed_code;
    private String band_image;
    private int num_members;
    private String city;
    private RealmList<Event> events;

    public Uri getImageUrlFull() {
        if (getProfile_image() == null) {
            return null;
        }

        String urlFull = ApiClient.BASE_URL + getProfile_image();
        return Uri.parse(urlFull);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Band setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmbed_code() {
        return embed_code;
    }

    public void setEmbed_code(String embed_code) {
        this.embed_code = embed_code;
    }

    public String getBand_image() {
        return band_image;
    }

    public void setBand_image(String band_image) {
        this.band_image = band_image;
    }

    public int getNum_members() {
        return num_members;
    }

    public void setNum_members(int num_members) {
        this.num_members = num_members;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", genre = " + genre + ", tag = " + tag + ", profile_image = " + profile_image + ", description = " + description + ", name = " + name + ", embed_code = " + embed_code + ", band_image = " + band_image + ", num_members = " + num_members + ", city = " + city + "]";
    }

    public RealmList<Event> getEvents() {
        return events;
    }

    public void setEvents(RealmList<Event> events) {
        this.events = events;
    }
}
