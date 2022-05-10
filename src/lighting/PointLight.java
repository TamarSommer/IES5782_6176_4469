package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC,kL,kQ;

    /**
     * constructor for light
     *
     * @param intensity
     * @return the intensity
     * @author Tamar sommer & Dvory azarkovitz
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        position = p;
    }


    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }
    /**
     * setter to filed position
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param position the position to set
     * @return the object - builder
     */
    public PointLight setPosition(Point position)
    {
        this.position = position;
        return this;
    }


    /**
     * setter to filed kc
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param kC the kC to set
     * @return the object - builder
     */
    public PointLight setKC(double KC)
    {
        kC = KC;
        return this;
    }

    /**
     * setter to filed kl
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param kL the kL to set
     * @return the object - builder
     */
    public PointLight setKl(double KL)
    {
        kL = KL;
        return this;
    }


    /**
     * setter to filed kq
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param kQ the kQ to set
     * @return the object - builder
     */
    public PointLight setKQ(double KQ)
    {
        kQ = KQ;
        return this;
    }
}
