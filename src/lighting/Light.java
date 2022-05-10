package lighting;
import primitives.Color;


/**
 * class for light - abstract class
 *
 * @author Tamar sommer & Dvory azarkovitz
 */
abstract class Light
{

    private Color intensity;


    /**
     * constructor for light
     *
     * @author Tamar sommer & Dvory azarkovitz
     * @return the intensity
     */
    protected Light(Color intensity)
    {
        this.intensity=intensity;
    }

    /**
     * getter to intensity
     *
     * @author Tamar sommer & Dvory azarkovitz
     * @return intensity Color
     * */
    public Color getIntensity()
    {
        return intensity;
    }


}
