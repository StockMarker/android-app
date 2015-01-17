package me.stockmarker.android;

import java.util.Date;

/**
 * Created by ktomega on 1/17/15.
 */
public class Company {
    private String name;
    private String descr;
    private Date date;

    public Company(String n, String d, Date dt) {
        name = n;
        descr = d;
        date = dt;
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
}
