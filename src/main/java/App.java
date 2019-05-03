import route.interfaces.SiteFileLoaderInterface;
import route.core.Engine;
import route.impl.SiteFileLoader;
import route.module.RouteContainer;
import route.utils.RouteBuilder;

import java.io.File;
import java.util.List;

public class App
{
    private Engine engine;
    private SiteFileLoaderInterface siteFileLoader;
    private RouteContainer routeContainer = new RouteContainer();

    private final static String DEFAULT_INPUT_SITE_FILE_NAME =
            App.class.getResource("example_input.txt").getPath();
            //App.class.getClassLoader().getResource("./").getPath() + "example_input.txt";

    public static void main( String[] args )
    {
        App app = new App();
        // load input file and init route engine
        String routeFilePath = DEFAULT_INPUT_SITE_FILE_NAME;
        if (args.length > 0) {
            routeFilePath = args[0];
        }

        File file = new File(DEFAULT_INPUT_SITE_FILE_NAME);
        if (!file.exists()){
            System.out.println("input file: " + DEFAULT_INPUT_SITE_FILE_NAME + " DOES NOT exist");
            return;
        }
        app.init(routeFilePath);

        // run test
        app.run();
    }

    private void init(String routeFilePath){
        System.out.println("routeFilePath is " + routeFilePath);
        this.siteFileLoader = new SiteFileLoader(this.routeContainer, new RouteBuilder());
        this.siteFileLoader.loadSiteFile(routeFilePath);

        this.engine = new Engine(this.routeContainer);
    }

    private void run() {
        this.testCalculateDistance("A-A");
        this.testCalculateDistance("A-B-C");
        this.testCalculateDistance("A-D");
        this.testCalculateDistance("A-D-C");
        this.testCalculateDistance("A-E-B-C-D");
        this.testCalculateDistance("A-E-D");

        this.findPossibleRoutesByMaxStop("C", "C", 3);

        this.findPossibleRoutesByExactMaxStop("A", "C", 4);

        this.findShortestTripRoutes("A", "C");
        this.findShortestTripRoutes("B", "B");

        this.findPossibleRoutesByMaxDistance("C", "C", 30);
    }

    private void testCalculateDistance(String trip){
        int distance = this.engine.calculateDistance(trip);

        if (distance == 0) {
            System.out.println(trip + ": NO SUCH ROUTE");
        } else {
            System.out.println("The distance of the route " + trip + " is " + distance);
        }
    }

    private void findPossibleRoutesByMaxStop(String startingSite, String endingSite, int maxStop){
        List<String> tripList = this.engine.findPossibleRoutesByMaxStop(startingSite, endingSite, maxStop);
        System.out.println("Trip number of trips starting at "+startingSite
                +" and ending at " + endingSite+
                " with a maximum of " + maxStop + " stops is " + tripList.size());
        this.printTrips(tripList);
    }

    private void findPossibleRoutesByExactMaxStop(String startingSite, String endingSite, int exactMaxStop){
        List<String> tripList = this.engine.findPossibleRoutesByExactMaxStop(startingSite, endingSite, exactMaxStop);
        System.out.println("The number of trips starting at " + startingSite +
                " and ending at "+ endingSite +" with exactly " + exactMaxStop + " stops is " + tripList.size());
        this.printTrips(tripList);
    }

    private void findShortestTripRoutes(String startingSite, String endingSite){
        List<String> tripList = this.engine.findShortestTripRoutes(startingSite, endingSite);
        System.out.println("The number of the shortest route (in terms of distance to travel) from " + startingSite +
                " to "+ endingSite +" is " + tripList.size());
        this.printTrips(tripList);
    }

    private void findPossibleRoutesByMaxDistance(String startingSite, String endingSite, int maxDistance){
        List<String> tripList = this.engine.findPossibleRoutesByMaxDistance(startingSite, endingSite, maxDistance);
        System.out.println("The number of different routes from " + startingSite +
                " to "+ endingSite +" with a distance of less than " + maxDistance + " is " + tripList.size());
        this.printTrips(tripList);
    }

    private void printTrips(List<String> tripList){
        System.out.println("Possible trip route start ------------");
        for (String trip: tripList) {
            System.out.println(trip + ", distance is " + this.engine.calculateDistance(trip));
        }
        System.out.println("Possible trip route end --------------");
    }
}