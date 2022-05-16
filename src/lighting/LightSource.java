package lighting;

import primitives.*;

public interface LightSource {
    /**
     * A function that return the intensity at a point
     *
     * @author Tamar Sommer & dvora azarkovitz
     * @param p Point value
     * @return intensity color in this point
     */
    public Color getIntensity(Point p);


    /**
     * A function that return the  vector L of the lighting direction at a point
     *
     * @author Tamar Sommer & dvora azarkovitz
     * @param p Point value
     * @return the lighting direction on a point
     */
    public Vector getL(Point p);

    /**
     * A function that return the distance between 2 points
     *
     * @author Tamar Gavrieli & Odeya Sadoun
     * @param point Point value
     * @return double value for the distance
     * */
    double getDistance(Point point);

}
