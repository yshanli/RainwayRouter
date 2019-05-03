package route.module;

import java.util.*;

public class RouteContainer {
    public int totalRoute = 0;
    private Map<String, Map<String, Route>> siteRoutesMap = new HashMap<>();

    public int totalSite(){
        return siteRoutesMap.size();
    }

    public void add(Route route) {
        System.out.println("Add route: " + route);
        if (!siteRoutesMap.containsKey(route.from)) {
            Map<String, Route> toRouteMap = new HashMap<>();
            toRouteMap.put(route.to, route);
            siteRoutesMap.put(route.from, toRouteMap);
            this.totalRoute++;
        } else {
            siteRoutesMap.get(route.from).put(route.to, route);
            this.totalRoute++;
        }
    }

    public boolean find(String from, String to) {
        if (siteRoutesMap.containsKey(from) && siteRoutesMap.get(from).containsKey(to)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean find(String from) {
        if (siteRoutesMap.containsKey(from)) {
            return true;
        } else {
            return false;
        }
    }

    public Optional<Route> getRoute(String from, String to){
        if (this.find(from, to)){
            return Optional.of(siteRoutesMap.get(from).get(to));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Map<String, Route>> getRoute(String from){
        if (this.find(from)){
            return Optional.of(siteRoutesMap.get(from));
        } else {
            return Optional.empty();
        }
    }

}
