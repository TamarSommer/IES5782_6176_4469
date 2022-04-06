package unittest;


import geometries.Geometries;
import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        //restart geometries
        Geometries geo=new Geometries(new Plane(new Point(0,1,1),new Point(1,0,1),new Point(0,0,1)),
                new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,0)),
                new Plane(new Point(0,1,2),new Point(1,0,2),new Point(0,0,2)));
        //plane1= parallel to [xy]: z=1,
        //plane2= [xy]: z=0,
        //plane3= parallel to [xy]: z=2

        // ============ Equivalence Partitions Tests ==============
        // TC01: the middle point is the closest
        Ray ray=new Ray(new Point(0, 0, -0.5),new Vector(0,0,1));
        List<Point> result=geo.findIntersections(ray);
        Point closePointAndGeo =ray.findClosestPoint(result);
        assertEquals(result.get(1), closePointAndGeo,"Wrong close intersection");

        // =============== Boundary Values Tests ==================
        // TC02: empty list
        ray=new Ray(new Point(0, 0, -5),new Vector(0,0,-1));
        result=geo.findIntersections(ray);
        closePointAndGeo =ray.findClosestPoint(result);
        assertEquals( null, closePointAndGeo,"Wrong close intersection");

        // TC03: the first point is the closest
        ray=new Ray(new Point(0, 0, 1.5),new Vector(0,0,-1));
        result=geo.findIntersections(ray);
        closePointAndGeo =ray.findClosestPoint(result);
        assertEquals(result.get(0), closePointAndGeo,"Wrong close intersection");

        // TC04: the last point is the closest
        ray=new Ray(new Point(0, 0, 2.5),new Vector(0,0,-1));
        result=geo.findIntersections(ray);
        closePointAndGeo =ray.findClosestPoint(result);
        assertEquals( result.get(2), closePointAndGeo,"Wrong close intersection");
    }
}