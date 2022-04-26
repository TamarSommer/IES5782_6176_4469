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

    /**
     * Test method for pgetNormal in plane
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here- check if the normal is good for the plane. if not- print it's an incorrect normal.
        Point p1= new Point(1,2,0);
        Point p2= new Point(-4,7,0);
        Point p3= new Point(1,0,5);
        Plane p= new Plane(p1,p2, p3);
        Vector v1=p1.subtract(p2);
        Vector v2=p2.subtract(p3);
        Vector v3=p3.subtract(p1);
        Vector n=p.getNormal(p1);
        assertTrue(isZero(v1.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v2.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        assertTrue(isZero(v3.dotProduct(n)), "ERROR: incorrect normal to plane");//if the dot product== 0, it's really the normal to the plane
        try {
            new Plane(new Point(1,2,3),new Point(2,4,6),new Point(4,8,12)).getNormal();
            fail("GetNormal() should throw an exception, but it failed");
        } catch (Exception e) {}
      //  System.out.print("well done");
    }
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