package unittest;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for geometries.Sphare class
 * @author Tamar zommer, dvori azarkovitz
 */
class SphareTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p= new Point(1, 1, 6);
        Point p1=new Point(1,1,1);
        Sphere s = new Sphere(p1,5);
        Vector v= p1.subtract(p).normalize();

        assertEquals(v, s.getNormal(p), "Bad normal to sphere");//regular case
    }

    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0),1);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Wrong number of points, ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p0 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p1 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(),"Wrong number of points, ray crosses the sphere twice");
        //check if the calculated points are really equal to the expected-p0 and p1
        boolean flag=false;
        try {
            if (isZero((result.get(0).subtract(p0)).length()) && (isZero((result.get(1).subtract(p1)).length())))
                flag=true;
            else if(isZero((result.get(0).subtract(p1)).length()) && (isZero((result.get(1).subtract(p0)).length())))
                flag=true;
            assertEquals(true, flag, "Wrong points");//check if really equal
        }
        catch(IllegalArgumentException e) {}

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0.5, 0),
                new Vector(3, 1, 0)));
        assertEquals(1, result.size(),"Wrong number of points, the ray starts inside the sphere");
        assertEquals(List.of(p1), result,"Wrong point");//check if really equal

        // TC04: Ray starts after the sphere (0 points)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(5, 2, 0), new Vector(3, 1, 0))), "Wrong number of points, the ray starts after the sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),new Vector(-1, 0, 1)));
        assertEquals(List.of(new Point(1, 0, 1)), result, "Wrong point, the ray starts at sphere and goes inside");
        // TC06: Ray starts at sphere and goes outside (0 points)
        assertEquals(null, sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))), "Wrong point, the ray starts at sphere and goes outside");

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),new Vector(1, 0, 0)));
        assertEquals(2, result.size(),"Wrong number of points");
        if (result.get(0).getD1() > result.get(1).getD1())//change the order of points to match our knowledge
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0,0,0), new Point(2,0,0)), result, "wrong points");//check if really equal
        // TC08: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),new Vector(0, -1, 0)));
        assertEquals(List.of(new Point(1, -1, 0)), result, "Wrong point, the ray starts at sphere and goes inside");
        // TC09: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0),new Vector(0, -1, 0)));
        assertEquals(List.of(new Point(1, -1, 0)), result, "wrong point, the ray starts inside");
        // TC10: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),new Vector(0, -1, 0)));
        assertEquals(List.of(new Point(1, -1, 0)), result, "wrong point, the ray starts at the center");
        // TC11: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),new Vector(0, 1, 0)));
        assertEquals(null, result, "wrong point, the ray starts at sphere and goes outside");
        // TC12: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(new Point(1, 2, 0),new Vector(0, 1, 0)));
        assertEquals(null, result, "wrong point, the ray starts after sphere");

        // **** Group: Ray's line is tangent (=mashik) to the sphere- no intersections counted with the sphere.
        // TC13: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray(new Point(0, 1, 0),new Vector(0, 1, 0)));
        assertEquals(null, result, "wrong point, tangent line, ray starts before sphere");
        // TC14: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray(new Point(1, 1, 0),new Vector(0, 1, 0)));
        assertEquals(null, result, "wrong point, tangent line, ray starts at sphere");
        // TC15: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray(new Point(2, 1, 0),new Vector(0, 1, 0)));
        assertEquals(null, result, "wrong point, tangent line, ray starts after sphere");

        // **** Group: Special cases
        // TC16: Ray's line is outside, ray is orthogonal to a ray start from the sphere's center. not tangent.
        result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),new Vector(0, 0, -1)));
        assertEquals(null, result, "wrong point, the ray is orthogonal to the continuing line starts from the center");
    }
}
