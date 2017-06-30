package com.tapptitude.fragmentmapapp;

/**
 * Created by alexpuhalschi on 30/06/2017.
 */

public class CoordinatesListItem {
    private String title;
    private double[] coordinates;

    public CoordinatesListItem(String title, double[] coordinates) {
        this.title = title;
        this.coordinates = coordinates;
    }

    public String getTitle() {
        return title;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

}
