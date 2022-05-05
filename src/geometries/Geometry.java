package geometries;

import primitives.Color;
import primitives.Vector;

import primitives.Point;


import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {
    protected Color emission=Color.BLACK;//the own color of the geometry
    /**
     * @return the emission light
     */
    public Color getEmission() {
        return emission;
    }
    /**
     * set emission and return the object itself
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;

    }
    /**
     * return the normal vector from the shape
     * @param p point to get the normal from {@link Point}
     * @return normal vector {@link Vector}
     */
    public abstract Vector getNormal(Point p);
}

