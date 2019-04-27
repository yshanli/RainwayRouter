package route.module;

public class Route {
    public String from;
    public String to;
    public int distance;

    public Route(String from, String to, int distance){
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route from " + this.from + ",to " + this.to + ",distance " + this.distance;
    }
}
