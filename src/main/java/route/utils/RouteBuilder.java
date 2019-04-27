package route.utils;

import route.module.Route;

public class RouteBuilder {
    public Route build(String input) {
        System.out.println("input:" + input);

        if (input == null){
            return null;
        }

        String inputAfterTrim = input.trim();
        if (inputAfterTrim.isEmpty() || inputAfterTrim.length() != 3){
            return null;
        }

        String from = inputAfterTrim.substring(0, 1);
        String to = inputAfterTrim.substring(1, 2);
        int distance = Integer.valueOf(inputAfterTrim.substring(2, 3));

        if (distance <= 0) {
            return null;
        }
        return new Route(from, to, distance);
    }

}
