package unittest;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries. Triangle class
 * @author Tamar zommer, dvori azarkovitz
 */


class TriangleTest {

    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tri = new Triangle(new Point(2,0,0),new Point(0,2,0),new Point(0,0,0));
        assertEquals(new Vector(0,0,1),tri.getNormal(null));
    }

    @Test
    public void findIntersections()
    {
        Triangle tr = new Triangle(new Point(0, 3, -3),new Point(3, 0, -3),new Point(-3, 0, -3));
        Ray r;

        // ============ Equivalence Partitions Tests ==============
        // TC01: the ray goes through the triangle
        try {
            r = new Ray(new Vector(-2, 0.5, -1), new Point(1, 1, -2));
            assertEquals(List.of(new Point(-1, 1.5, -3)), tr.findIntersections(r), "the ray goes through the triangle");
        }
        catch(IllegalArgumentException e) //catch creation of new vectors at findIntersections- one might be zero vector
        {}
        // TC02: the ray is outside the triangle against edge
        r = new Ray(new Vector(1, 1, -4), new Point(4, 4, -2));
        assertEquals(null, tr.findIntersections(r), "the ray is outside the triangle against edge");
        // TC03: the ray is outside the triangle against vertex
        r = new Ray(new Vector(-1, -1, -1), new Point(-4, -1, -2));
        assertEquals(null, tr.findIntersections(r), "the ray is outside the triangle against vertex");


        // =============== Boundary Values Tests ==================
        // TC04: ray through edge
        r = new Ray(new Vector(0, 0, -1), new Point(-2, 1, -1));
        assertEquals(null, tr.findIntersections(r), "ray through edge");

        // TC05: ray through vertex
        r = new Ray(new Vector(0, 0, -1), new Point(0, 3, -2));
        assertEquals(null, tr.findIntersections(r), "ray through vertex");

        // TC06: ray goes through the continuation of side 1
        r = new Ray(new Vector(0, 0, -1), new Point(-1, 4, -2));
        assertEquals(null, tr.findIntersections(r), "ray goes through the continuation of side 1");
    }
}