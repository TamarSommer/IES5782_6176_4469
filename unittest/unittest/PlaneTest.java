package unittest;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries. Plane class
 * @author Tamar zommer, dvori azarkovitz
 */
class PlaneTest {

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here- check if the normal is good for the plane. if not- print it's an incorrect normal.
        Point p1 = new Point(1, 2, 0);
        Point p2 = new Point(-4, 7, 0);
        Point p3 = new Point(1, 0, 5);
        Plane p = new Plane(p1, p2, p3);
        Vector v1 = p1.subtract(p2);
        Vector v2 = p2.subtract(p3);
        Vector v3 = p3.subtract(p1);
        Vector n = p.getNormal(p1);
        assertTrue(isZero(v1.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v2.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v3.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        try {
            new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(4, 8, 12)).getNormal(p1);//a case that all the points are on the same vector- cannot create the plane
            fail("GetNormal() should throw an exception, but it failed");
        } catch (Exception e) {
        }
    }

}