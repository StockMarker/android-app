package me.stockmarker.android;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ktomega on 1/17/15.
 */
public class Company implements Parcelable {
    private String name;
    private String ticker;
    private HashMap<String, String> otherData;

    public static final Parcelable.Creator<Company> CREATOR
            = new Parcelable.Creator<Company>() {
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public Company(String n, String t, HashMap<String, String> other) {
        name = n;
        ticker = t;
        otherData = other;
    }
    
    public Company(String n, String t) {
        this(n, t, new HashMap<String, String>());
    }

    public Company(Parcel in) {
        this(in.readString(), in.readString(), Utils.getStringHashMapFromParcel(in));
    }
    
    public void addData(String key, String val) {
        otherData.put(key, val);
    }

    public String getData(String key) {
        return otherData.get(key);
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public HashMap<String, String> getOtherData() {
        return otherData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(ticker);
        Utils.writeStringHashMapFromParcel(out, otherData);
    }

    public String toString() {
        return getName() + ", " + getTicker();
    }
}
