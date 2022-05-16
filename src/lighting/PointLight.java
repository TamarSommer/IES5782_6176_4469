package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1,kL=0,kQ=0;
    private Vector kk = new Vector(1,0,0);
    /**
     * constructor for light
     *
     * @param intensity
     * @return the intensity
     * @author Tamar sommer & Dvory azarkovitz
     */
    public PointLight(Color intensity, Point p) {
        super(intensity);
        this.position = p;
    }





    /**
     * setter to filed kc
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param KC the kC to set
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
     * @param KL the kL to set
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
     * @param KQ the kQ to set
     * @return the object - builder
     */
    public PointLight setKQ(double KQ)
    {
        kQ = KQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double d = position.distance(p);
        Color iL = getIntensity().scale((1 / (kC + kL * d + kQ * d * d)));
        return iL;
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
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

    @Override
    public double getDistance(Point point)
    {
        return position.distance(point);
    }
}
