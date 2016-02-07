package com.chauffr.home;

/**
 * Created by Wenyue on 7/02/2016.
 */
public class BookingInfoHolder {

    private static final BookingInfoHolder holder = new BookingInfoHolder();
    public static BookingInfoHolder getInstance() {return holder;}

    private String toPlaceId;
    private String fromPlaceId;
    private String toAirport;
    private String fromAirport;
    private String flightNumber;
    private int clientId, chauffeurId;
    private boolean isInternationalFlight = false;


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(int chauffeurId) {
        this.chauffeurId = chauffeurId;
    }


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public boolean isInternationalFlight() {
        return isInternationalFlight;
    }

    public void setIsInternationalFlight(boolean isInternationalFlight) {
        this.isInternationalFlight = isInternationalFlight;
    }

    public String getToPlaceId() {
        return toPlaceId;
    }

    public void setToPlaceId(String toPlaceId) {
        this.toPlaceId = toPlaceId;
    }

    public String getFromPlaceId() {
        return fromPlaceId;
    }

    public void setFromPlaceId(String fromPlaceId) {
        this.fromPlaceId = fromPlaceId;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }




}
