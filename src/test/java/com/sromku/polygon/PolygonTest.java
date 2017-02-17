package com.sromku.polygon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alex on 17/02/2017.
 */
public class PolygonTest {

    private Logger logger = LoggerFactory.getLogger(PolygonTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Create simple polygon and check that the point is inside
     */
    @Test
    public void testSimplePolygon() {
        Polygon polygon = Polygon.Builder()
                .addVertex(new Point(1, 3))
                .addVertex(new Point(2, 8))
                .addVertex(new Point(5, 4))
                .addVertex(new Point(5, 9))
                .addVertex(new Point(7, 5))
                .addVertex(new Point(6, 1))
                .addVertex(new Point(3, 1))
                .build();

        // Point is inside
        isInside(polygon, new Point(5.5f, 7));

        // Point isn't inside
        isInside(polygon, new Point(4.5f, 7));
    }

    /**
     * Create polygon two holes and check that the point is inside
     */
    @Test
    public void testPolygonWithHoles() {
        Polygon polygon = Polygon.Builder()
                .addVertex(new Point(1, 2)) // polygon
                .addVertex(new Point(1, 6))
                .addVertex(new Point(8, 7))
                .addVertex(new Point(8, 1))
                .close()
                .addVertex(new Point(2, 3)) // hole one
                .addVertex(new Point(5, 5))
                .addVertex(new Point(6, 2))
                .close()
                .addVertex(new Point(6, 6)) // hole two
                .addVertex(new Point(7, 6))
                .addVertex(new Point(7, 5))
                .build();

        // Point is inside
        isInside(polygon, new Point(6, 5));

        // Point isn't inside
        isInside(polygon, new Point(4, 3));

        // Point isn't inside
        isInside(polygon, new Point(6.5f, 5.8f));
    }

    /**
     * Check if point inside the polygon
     *
     * @param polygon
     * @param point
     */
    private void isInside(Polygon polygon, Point point) {
        boolean contains = polygon.contains(point);
        logger.info("The point {} is {} inside the polygon", point.toString(), (contains ? "" : "not "));
    }

}