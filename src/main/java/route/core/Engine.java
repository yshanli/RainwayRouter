package route.core;

import route.module.Route;
import route.module.RouteContainer;
import route.utils.TripParser;

import java.util.*;

public class Engine {
    private RouteContainer routeContainer;

    public Engine(RouteContainer routeContainer) {
        this.routeContainer = routeContainer;
    }

    public int calculateDistance(String tripRoute) {
        List<String> tripSites = TripParser.parseTripSites(tripRoute);

        if (tripSites.size() < 2) {
            return 0;
        }
        int distance = 0;

        for (int i = 0; i < tripSites.size() - 1; i++) {
            Optional<Route> route = this.routeContainer.getRoute(tripSites.get(i), tripSites.get(i + 1));
            distance += route.orElse(new Route()).distance;
            if (distance == 0) {
                break;
            }
        }
        return distance;
    }

    public List<String> findPossibleRoutesByMaxStop(String startingSite, String endingSite, int maxStop) {
        List<String> foundTripSitesStr = new ArrayList<>();

        if (maxStop <= 0 || startingSite.isEmpty() || endingSite.isEmpty()) {
            return foundTripSitesStr;
        }

        Map<String, Route> nextPossibleRoutes = routeContainer.getRoute(startingSite).orElse(new HashMap<>());
        findNextSiteByMaxStop(nextPossibleRoutes, startingSite, endingSite, maxStop, foundTripSitesStr);
        return foundTripSitesStr;
    }

    private void findNextSiteByMaxStop(Map<String, Route> nextPossibleRoutes,
                                       String tripSitesStr,
                                       String endingSite,
                                       int maxStop,
                                       List<String> foundTripSitesStr) {
        if (maxStop <= 0 || nextPossibleRoutes.size() == 0) {
            return;
        }

        if (maxStop == 1 && nextPossibleRoutes.containsKey(endingSite)) {
            String fullSitesStr = tripSitesStr + "-" + endingSite;
            foundTripSitesStr.add(fullSitesStr);
        } else {
            nextPossibleRoutes.forEach((key, value) -> {
                if (key.equals(endingSite)) {
                    String fullSitesStr = tripSitesStr + "-" + endingSite;
                    foundTripSitesStr.add(fullSitesStr);
                } else {
                    findNextSiteByMaxStop(routeContainer.getRoute(key).orElse(new HashMap<>()),
                            tripSitesStr + "-" + key,
                            endingSite,
                            maxStop - 1,
                            foundTripSitesStr);
                }
            });
        }

    }

    public List<String> findPossibleRoutesByExactMaxStop(String startingSite, String endingSite, int exactMaxStop) {
        List<String> foundTripSitesStr = new ArrayList<>();
        if (exactMaxStop <= 0 || startingSite.isEmpty() || endingSite.isEmpty()) {
            return foundTripSitesStr;
        }

        findNextSiteByExactMaxStop(routeContainer.getRoute(startingSite).orElse(new HashMap<>()), startingSite, endingSite, exactMaxStop, foundTripSitesStr);
        return foundTripSitesStr;
    }

    private void findNextSiteByExactMaxStop(Map<String, Route> nextPossibleRoutes,
                                            String tripSitesStr,
                                            String endingSite,
                                            int remainStops,
                                            List<String> foundTripSitesStr) {
        if (remainStops <= 0 || nextPossibleRoutes.size() == 0) {
            return;
        }

        if (remainStops == 1 && nextPossibleRoutes.containsKey(endingSite)) {
            String fullSitesStr = tripSitesStr + "-" + endingSite;
            foundTripSitesStr.add(fullSitesStr);
        } else {
            nextPossibleRoutes.forEach((key, value) ->
                    findNextSiteByExactMaxStop(routeContainer.getRoute(key).orElse(new HashMap<>()),
                    tripSitesStr + "-" + key,
                    endingSite,
                    remainStops - 1,
                    foundTripSitesStr));
        }
    }

    public List<String> findShortestTripRoutes(String startingSite, String endingSite) {
        // Here we assume the worst case is starting site has to go through all sites to ending site
        // We have 3 sites (A/B/C) now.
        // For A-B-C-A, A has to go through 3 stops to itself
        // For A-B-C, A has to go through 2 stops to C
        int maxStop;
        if (startingSite.equals(endingSite)) {
            maxStop = routeContainer.totalSite();
        } else {
            maxStop = routeContainer.totalSite() - 1;
        }
        List<String> routes = this.findPossibleRoutesByMaxStop(startingSite, endingSite, maxStop);

        List<String> shortestRoutes = new ArrayList<>();
        if (routes.size() == 0) {
            return shortestRoutes;
        }

        int shortestDistance = this.calculateDistance(routes.get(0));
        for (String route : routes) {
            int distance = this.calculateDistance(route);
            if (distance < shortestDistance) {
                shortestRoutes.clear();
                shortestRoutes.add(route);
                shortestDistance = distance;
            } else if(distance == shortestDistance){
                shortestRoutes.add(route);
            }
        }

        return shortestRoutes;
    }

    public List<String> findPossibleRoutesByMaxDistance(String startingSite, String endingSite, int maxDistance) {
        List<String> foundTripSitesStr = new ArrayList<>();
        if (maxDistance <= 0 || startingSite.isEmpty() || endingSite.isEmpty()) {
            return foundTripSitesStr;
        }

        findNextSiteByMaxDistance(routeContainer.getRoute(startingSite).orElse(new HashMap<>()), startingSite, endingSite, maxDistance, foundTripSitesStr);
        return  foundTripSitesStr;
    }

    private void findNextSiteByMaxDistance(Map<String, Route> nextPossibleRoutes,
                                           String tripSitesStr,
                                           String endingSite,
                                           int maxDistance,
                                           List<String> foundTripSitesStr) {
        if (nextPossibleRoutes.size() == 0 || maxDistance - this.calculateDistance(tripSitesStr) <= 0) {
            return;
        }

        nextPossibleRoutes.forEach((key, vlaue) -> {
            if (key.equals(endingSite)) {
                String fullSitesStr = tripSitesStr + "-" + endingSite;
                if (maxDistance - this.calculateDistance(fullSitesStr) > 0) {
                    foundTripSitesStr.add(fullSitesStr);

                    findNextSiteByMaxDistance(routeContainer.getRoute(endingSite).orElse(new HashMap<>()),
                            fullSitesStr,
                            endingSite,
                            maxDistance,
                            foundTripSitesStr);
                }
            } else {
                String fullSitesStr = tripSitesStr + "-" + key;
                if (maxDistance - this.calculateDistance(fullSitesStr) > 0) {
                    findNextSiteByMaxDistance(routeContainer.getRoute(key).orElse(new HashMap<>()),
                            fullSitesStr,
                            endingSite,
                            maxDistance,
                            foundTripSitesStr);
                }
            }

        });
    }
}
