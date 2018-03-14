package pl.edu.pwste.goco.senior.Tools;

import android.location.LocationManager;

/**
 * Created by goco on 13.03.2018.
 */

public class LocationTools {
    /**
     * @param firstPlaceLatitude
     * @param firstPlaceLongitude
     * @param secondPlaceLatitude
     * @param secondPlaceLongitude
     * @return distance between locations in meters
     */
    public static Double calculateDistanceBetweenLocations(double firstPlaceLatitude, double firstPlaceLongitude, double secondPlaceLatitude, double secondPlaceLongitude) {
        double R = 6378.137; // Radius of earth in KM
        double dLat = secondPlaceLatitude * Math.PI / 180 - firstPlaceLatitude * Math.PI / 180;
        double dLon = secondPlaceLongitude * Math.PI / 180 - firstPlaceLongitude * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(firstPlaceLatitude * Math.PI / 180) * Math.cos(secondPlaceLatitude * Math.PI / 180) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;
        return d * 1000; // meters
    }
}
