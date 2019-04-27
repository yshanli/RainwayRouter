package route.utils;

import java.util.ArrayList;
import java.util.List;

public class TripParser {
    public static List<String> parseTripSites(String trip){
        if (trip == null || trip.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> tripSites = new ArrayList<>();

        String[] sites = trip.split("-");
        for (String site1 : sites) {
            String site = site1.trim();
            if (site.length() == 1) {
                tripSites.add(site);
            }
        }
        return tripSites;
    }
}
