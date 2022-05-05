package lighting;
import primitives.*;

/**
 * AmbientLight class
 */
public class AmbientLight extends Light {

    /**
     *
     * @param iA
     * @param kA
     *  constructor build the ambient light intensity
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * default ctr
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
}