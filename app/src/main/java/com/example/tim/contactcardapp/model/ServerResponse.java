package com.example.tim.contactcardapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 19-10-2017.
 */

public class ServerResponse implements Parcelable {

    private List<Person> results;

    public ServerResponse() {

    }

    public List<Person> getResults() {
        return results;
    }

    protected ServerResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            results = new ArrayList<Person>();
            in.readList(results, Person.class.getClassLoader());
        } else {
            results = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (results == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(results);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ServerResponse> CREATOR = new Parcelable.Creator<ServerResponse>() {
        @Override
        public ServerResponse createFromParcel(Parcel in) {
            return new ServerResponse(in);
        }

        @Override
        public ServerResponse[] newArray(int size) {
            return new ServerResponse[size];
        }
    };
}