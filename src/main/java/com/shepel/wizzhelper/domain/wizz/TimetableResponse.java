package com.shepel.wizzhelper.domain.wizz;

import java.util.List;

public class TimetableResponse {

    List<Flight> outboundFlights;
    List<Flight> returnFlights;

    public List<Flight> getOutboundFlights() {
        return outboundFlights;
    }

    public void setOutboundFlights(List<Flight> outboundFlights) {
        this.outboundFlights = outboundFlights;
    }

    public List<Flight> getReturnFlights() {
        return returnFlights;
    }

    public void setReturnFlights(List<Flight> returnFlights) {
        this.returnFlights = returnFlights;
    }
}
