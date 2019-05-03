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

    public Route(){
        this.from = "";
        this.to = "";
        this.distance = 0;
    }

    @Override
    public String toString() {
        return "Route from " + this.from + ",to " + this.to + ",distance " + this.distance;
    }
}
