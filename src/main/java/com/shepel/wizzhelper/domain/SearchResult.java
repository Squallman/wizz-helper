package com.shepel.wizzhelper.domain;

import com.shepel.wizzhelper.domain.wizz.Flight;

import java.util.List;

public class SearchResult {

    String date;
    int price;
    List<Flight> flights;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
