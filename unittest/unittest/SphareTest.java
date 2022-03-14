package unittest;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphare class
 * @author Tamar zommer, dvori azarkovitz
 */
class SphareTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point3D)}.
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
}