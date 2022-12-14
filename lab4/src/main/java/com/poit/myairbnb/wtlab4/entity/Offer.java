package com.poit.myairbnb.wtlab4.entity;

public class Offer {
    private long id;
    private String hotel;
    private int bedCount;

    public Offer(String hotel, int bedCount) {
        this.hotel = hotel;
        this.bedCount = bedCount;
    }

    public Offer(long id, String hotel, int bedCount) {
        this.id = id;
        this.hotel = hotel;
        this.bedCount = bedCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }
}
