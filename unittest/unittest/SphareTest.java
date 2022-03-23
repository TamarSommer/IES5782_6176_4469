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
    public void findIntersections(Ray ray) {
        Sphere sphere = new Sphere(new Point(1.0D, 0.0D, 0.0D), 1.0D);
        assertEquals( (Object)null, sphere.findIntersections(new Ray(new Vector(1.0D, 1.0D, 0.0D),new Point(-1.0D, 0.0D, 0.0D))),"Wrong number of points, ray's line out of sphere");
        Point p0 = new Point(0.0651530771650466D, 0.355051025721682D, 0.0D);
        Point p1 = new Point(1.53484692283495D, 0.844948974278318D, 0.0D);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1.0D, 0.0D, 0.0D), new Vector(3.0D, 1.0D, 0.0D)));
        assertEquals( 2L, (long)result.size(),"Wrong number of points, ray crosses the sphere twice");
        boolean flag = false;

        try {
            if (Util.isZero(((Point)result.get(0)).subtract(p0).length()) && Util.isZero(((Point)result.get(1)).subtract(p1).length())) {
                flag = true;
            } else if (Util.isZero(((Point)result.get(0)).subtract(p1).length()) && Util.isZero(((Point)result.get(1)).subtract(p0).length())) {
                flag = true;
            }

            assertEquals( true, flag,"Wrong points");
        } catch (IllegalArgumentException var7) {
        }

        result = sphere.findIntersections(new Ray(new Point(0.5D, 0.5D, 0.0D), new Vector(3.0D, 1.0D, 0.0D)));
        assertEquals( 1L, (long)result.size(),("Wrong number of points, the ray starts inside the sphere"));
        assertEquals(List.of(p1), result,"Wrong point");
        assertEquals((Object)null, sphere.findIntersections(new Ray(new Point(5.0D, 2.0D, 0.0D), new Vector(3.0D, 1.0D, 0.0D))),"Wrong number of points, the ray starts after the sphere");
        result = sphere.findIntersections(new Ray(new Point(2.0D, 0.0D, 0.0D), new Vector(-1.0D, 0.0D, 1.0D)));
        assertEquals( List.of(new Point(1.0D, 0.0D, 1.0D)), result,"Wrong point, the ray starts at sphere and goes inside");
        assertEquals( (Object)null, sphere.findIntersections(new Ray(new Point(2.0D, 0.0D, 0.0D), new Vector(1.0D, 0.0D, 0.0D))),"Wrong point, the ray starts at sphere and goes outside");
        result = sphere.findIntersections(new Ray(new Point(-1.0D, 0.0D, 0.0D), new Vector(1.0D, 0.0D, 0.0D)));
        assertEquals( 2L, (long)result.size(),"Wrong number of points");
        if (((Point)result.get(0)).getD1() > ((Point)result.get(1)).getD1()) {
            result = List.of((Point)result.get(1), (Point)result.get(0));
        }

        assertEquals(List.of(new Point(0.0D, 0.0D, 0.0D), new Point(2.0D, 0.0D, 0.0D)), result, "wrong points");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 1.0D, 0.0D), new Vector(0.0D, -1.0D, 0.0D)));
        assertEquals(List.of(new Point(1.0D, -1.0D, 0.0D)), result, "Wrong point, the ray starts at sphere and goes inside");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 0.5D, 0.0D), new Vector(0.0D, -1.0D, 0.0D)));
        assertEquals(List.of(new Point(1.0D, -1.0D, 0.0D)), result, "wrong point, the ray starts inside");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 0.0D, 0.0D), new Vector(0.0D, -1.0D, 0.0D)));
        assertEquals(List.of(new Point(1.0D, -1.0D, 0.0D)), result, "wrong point, the ray starts at the center");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 1.0D, 0.0D), new Vector(0.0D, 1.0D, 0.0D)));
        assertEquals((Object)null, result, "wrong point, the ray starts at sphere and goes outside");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 2.0D, 0.0D), new Vector(0.0D, 1.0D, 0.0D)));
        assertEquals((Object)null, result, "wrong point, the ray starts after sphere");
        result = sphere.findIntersections(new Ray(new Point(0.0D, 1.0D, 0.0D), new Vector(0.0D, 1.0D, 0.0D)));
        assertEquals((Object)null, result, "wrong point, tangent line, ray starts before sphere");
        result = sphere.findIntersections(new Ray(new Point(1.0D, 1.0D, 0.0D), new Vector(0.0D, 1.0D, 0.0D)));
        assertEquals((Object)null, result, "wrong point, tangent line, ray starts at sphere");
        result = sphere.findIntersections(new Ray(new Point(2.0D, 1.0D, 0.0D), new Vector(0.0D, 1.0D, 0.0D)));
        assertEquals((Object)null, result, "wrong point, tangent line, ray starts after sphere");
        result = sphere.findIntersections(new Ray(new Point(-1.0D, 0.0D, 0.0D), new Vector(0.0D, 0.0D, -1.0D)));
        assertEquals((Object)null, result, "wrong point, the ray is orthogonal to the continuing line starts from the center");
    }
    }
}