package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private final Color intensity;

    /*************** ctor *****************/
    /**
     * ctor that restarts Intensity of super class by Ia and ka
     * @param Ia
     * @param ka
     */
    public AmbientLight(Color Ia, Double3 ka) {
        intensity = Ia.scale(ka);//the father is "Light"- the basic light class
    }

    /*************** ctor *****************/
    /**
     * ctor that restarts Intensity of super class by Ia and ka
     */
    public AmbientLight() {
        intensity = Color.BLACK;//the father is "Light"- the basic light class
    }

    /*************** get *****************/
    /**
     * @return the Intensity
     */
    public Color getIntensity()
    {
    	return intensity;
    }
}

