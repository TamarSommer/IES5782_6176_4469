package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * common interface for all graphic object that intersect with a ray {@link Ray}
 */
public interface Intersectable {
    /** find all intersection points from the array
     * @param r ray pointing towards graphic object
     * @return immutable list of intersection points {@link Point}
     */
    List<Point> findIntersections(Ray r);
}
