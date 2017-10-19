package com.example.tim.contactcardappandroid;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tim on 5-9-2017.
 */

public class Person implements Parcelable {

    String firstName;
    String lastName;
    String image;
    String email;

    public Person(String firstName, String lastName, String image, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.email = email;
    }

    protected Person(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        image = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(image);
        dest.writeString(email);
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