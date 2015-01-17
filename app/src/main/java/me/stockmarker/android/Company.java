package me.stockmarker.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ktomega on 1/17/15.
 */
public class Company implements Parcelable {
    private String name;
    private String descr;
    private Date date;

    public static final Parcelable.Creator<Company> CREATOR
            = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public Company(String n, String d, Date dt) {
        name = n;
        descr = d;
        date = dt;
    }

    public Company(Parcel in) {
        this(in.readString(), in.readString(), (Date)in.readSerializable());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return descr;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(descr);
        out.writeSerializable(date);
    }

    public String toString() {
        return getName() + ", " + getDescription() + ", " + getDate().toString();
    }
}
