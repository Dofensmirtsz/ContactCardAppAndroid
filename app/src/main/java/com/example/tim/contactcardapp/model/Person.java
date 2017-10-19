package com.example.tim.contactcardapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tim on 5-9-2017.
 */

public class Person implements Parcelable {

    private Name name;
    private String email;
    private Picture picture;

    public Person(Name name, String email, Picture picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public Person() {
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    protected Person(Parcel in) {
        name = (Name) in.readValue(Name.class.getClassLoader());
        email = in.readString();
        picture = (Picture) in.readValue(Picture.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeString(email);
        dest.writeValue(picture);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}