package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{

    private Vector direction;
    /**
     * constructor for light
     *
     * @param intensity
     * @return the intensity
     * @author Tamar sommer & Dvory azarkovitz
     */
    protected DirectionalLight(Color intensity) {
        super(intensity);
    }

    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }
}
