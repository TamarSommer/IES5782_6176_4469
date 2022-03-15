package unittest;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}
     */
    @Test
    public void findIntersections() {
        try {
            Plane pl = new Plane(new Vector(0, 0, -1), new Point(0, 0, -3));
            Ray r;

            // ============ Equivalence Partitions Tests ==============
            // The Ray is neither orthogonal nor parallel to the plane
            // TC01: the ray intersects the plane
            r = new Ray(new Vector(2, 1, -1), new Point(1, 1, 0));
            List<Point> result = pl.findIntersections(r);
            assertEquals(List.of(new Point(7, 4, -3)), result);

            // TC02: the ray does not intersect the plane
            r = new Ray(new Vector(2, 1, 1), new Point(1, 1, 0));
            result = pl.findIntersections(r);
            assertEquals(null, result);

            // =============== Boundary Values Tests ==================
            // Ray is parallel to the plane
            // TC03: the ray is included in the plane
            r = new Ray(new Vector(2, 1, 0), new Point(1, 2, -3));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! the ray is parallel and included in the plane");
            // TC04: the ray is not included in the plane
            r = new Ray(new Vector(2, 1, 0),new Point(1, 2, -2));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! the ray is parallel and not included in the plane");

            // Ray is orthogonal to the plane
            // TC05: Ray starts before the plane
            r = new Ray(new Vector(0, 0, -1), new Point(1, 1, 0));
            assertEquals(List.of(new Point(1, 1, -3)), pl.findIntersections(r), "wrong intersection! the ray is orthogonal and starts before the plane");
            // TC06: Ray starts in the plane
            r = new Ray(new Vector(0, 0, -1), new Point(1, 1, -3));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! the ray is orthogonal and starts in the plane");
            // TC07: Ray starts after the plane
            r = new Ray(new Vector(0, 0, -1), new Point(1, 1, -4));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! the ray is orthogonal and starts after the plane");

            // starting point is in the plane
            // TC08: Starting point of the ray is on the plane, but the vector is not included in the plane
            r = new Ray(new Vector(2, 1, -1), new Point(1, 1, -3));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! Starting point of the ray is on the plane, but the vector is not included in the plane");
            // TC09: Starting point of the ray is equal to the point represents the plane- q0
            r = new Ray(new Vector(2, 1, -1), new Point(0, 0, -3));
            assertEquals(null, pl.findIntersections(r), "wrong intersection! Starting point of the ray is equal to the point represents the plane- q0");

        }
        catch(IllegalArgumentException e) {}
    }

}