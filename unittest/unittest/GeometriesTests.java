package unittest;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntsersections()
    {
        Geometries EmptyGeo=new Geometries();//no geometries added here
        //we implemented findIntersections of sphere, triangle and plane.
        Geometries geo=new Geometries(new Sphere(new Point(4,0,0),5),new Triangle(new Point(1,4,0),new Point(1,2,0),new Point(5,2,0)),new Plane(new Point(1,2,0),new Point(0,7,0),new Point(1,0,0)));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some (but not all) geometries are cut
        List<Point> result = geo.findIntersections(new Ray(new Vector(2,-4,-4),new Point(2, 5, 4)));
        assertEquals(3, result.size(),"Wrong number of intersection points");//the ray cuts the sphere twice and the plane once. the triangle is not cut. 3 intersections are expected. if didn't get 3, print the message.

        // =============== Boundary Values Tests ==================
        //TC02:Empty geometries collection
        assertEquals(null, EmptyGeo.findIntersections(new Ray(new Vector(2,-4,-4),new Point(2, 5, 4))), "Wrong number of intersection points");
        //TC03:No shape is cut
        assertEquals(null, geo.findIntersections(new Ray(new Vector(1,0,0),new Point(0, 0, 7))), "Wrong number of intersection points");
        //TC04:One shape is cut
        result = geo.findIntersections(new Ray(new Vector(-8,1,-10), new Point(0, 0, 10)));
        assertEquals(1 , result.size(),"Wrong number of intersection points");
        //TC05:All shapes are cut
        result = geo.findIntersections(new Ray(new Vector(0,-2,-4),new Point(2, 5, 4)));
        assertEquals(4 , result.size(),"Wrong number of intersection points");//4 intersections with all the shapes: 2 with the sphere, once with the triangle, once with the plane.
    }
}