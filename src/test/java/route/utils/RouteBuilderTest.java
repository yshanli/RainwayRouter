package route.utils;

import org.junit.Test;
import route.module.Route;

import static org.junit.Assert.*;

public class RouteBuilderTest {
    private RouteBuilder builder = new RouteBuilder();

    @Test
    public void buildWithNormalInput() {
        String input = "AB4";
        Route route = builder.build(input);

        assertNotNull(route);
        assertEquals("A", route.from);
        assertEquals("B", route.to);
        assertEquals(4, route.distance);
    }

    @Test
    public void buildWithMalformedInput() {
        Route route = builder.build(null);
        assertNull(route);

        route = builder.build("AB");
        assertNull(route);

        route = builder.build("AB44");
        assertNull(route);

        route = builder.build("");
        assertNull(route);
    }

}