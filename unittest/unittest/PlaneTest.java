package unittest;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries. Plane class
 * @author Tamar zommer, Dvori azarkovitz
 */
class PlaneTest {

    @Test
    void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        //To check the plane we need to check if the normal is good. if not - print it's an incorrect normal.
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
        // =============== Boundary Values Tests ==================

        try {
            new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(4, 8, 12)).getNormal(p1);//a case that all the points are on the same vector- cannot create the plane
            fail("GetNormal() should throw an exception, all the points are on the same vector");
        } catch (Exception e) {
        }
        //case that two points are identical
        try{
        Point p4 = new Point(1,2,3);
        Point p5 = new Point(1,2,3);
        Point p6 = new Point(1, 0, 5);
        Plane pl = new Plane(p4, p5, p6);
        fail("getNormal() should throw an exception - two points are identical");
        }catch (Exception e) {
        }
    }

}