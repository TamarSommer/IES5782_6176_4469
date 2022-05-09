package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC = 1,kL = 0,kQ = 0;

    /**
     * constructor for light
     *
     * @param intensity
     * @param position
     * @param c
     * @param l++
     * @param q
     * @return the intensity
     * @author Tamar sommer & Dvory azarkovitz
     */
    public PointLight(Color intensity, Point position, double c, double l, double q)
    {
        super(intensity);
        this.position = position;
        this.kC = c;
        this.kL = l;
        this.kQ = q;
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
    public PointLight setKL(double kL)
    {
        kL = kL;
        return this;
    }


    /**
     * setter to filed kq
     *
     * @author Tamar Sommer & Dvory azarkovitz
     * @param kQ the kQ to set
     * @return the object - builder
     */
    public PointLight setKQ(double kQ)
    {
        kQ = kQ;
        return this;
    }
}
