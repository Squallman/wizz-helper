package com.shepel.wizzhelper.domain.wizz;

import java.util.Date;
import java.util.List;

public class Flight {

    private String departureStation;
    private String arrivalStation;
    private Date departureDate;
    private Price price;
    private String priceType;
    private List<Date> departureDates;
    private String classOfService;
    private boolean hasMacFlight;

    public String getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public List<Date> getDepartureDates() {
        return departureDates;
    }

    public void setDepartureDates(List<Date> departureDates) {
        this.departureDates = departureDates;
    }

    public String getClassOfService() {
        return classOfService;
    }

    public void setClassOfService(String classOfService) {
        this.classOfService = classOfService;
    }

    public boolean isHasMacFlight() {
        return hasMacFlight;
    }

    public void setHasMacFlight(boolean hasMacFlight) {
        this.hasMacFlight = hasMacFlight;
    }
}
