package unittest;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

//import primitives;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {

    @Test
    void subtract() {
        if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)))
            out.println("ERROR: Point - Point does not work correctly");
    }

    @Test
    void add() {
        if (!(p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0))))
            out.println("ERROR: Point + Vector does not work correctly");
    }

    @Test
    void scale() {
    }

    @Test
    void dotProduct() {
    }

    //test method for crossProdoct in vector
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows("crossProduct() for parallel vectors does not throw an exception",
                IllegalArgumentException.class, () -> v1.crossProduct(v3));
    }


        @Test
    void lengthSquared() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
        if (!isZero(u.length() - 1))
            out.println("ERROR: the normalized vector is not a unit vector");
        try { // test that the vectors are co-lined
            v.crossProduct(u);
            out.println("ERROR: the normalized vector is not parallel to the original one");
        } catch (Exception ex) {
        }
        if (v.dotProduct(u) < 0) {
            out.println("ERROR: the normalized vector is opposite to the original one");
        }
    }

    @Test
    void testEquals() {
    }
}