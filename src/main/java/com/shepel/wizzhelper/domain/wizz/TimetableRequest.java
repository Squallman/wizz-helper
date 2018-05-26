package com.shepel.wizzhelper.domain.wizz;

import java.util.List;

public class TimetableRequest {

    private List<FlightRequest> flightList;
    private String priceType;
    private int adultCount;
    private int childCount;
    private int infantCount;

    public List<FlightRequest> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightRequest> flightList) {
        this.flightList = flightList;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getInfantCount() {
        return infantCount;
    }

    public void setInfantCount(int infantCount) {
        this.infantCount = infantCount;
    }
}
