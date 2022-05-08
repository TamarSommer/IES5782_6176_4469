package primitives;

public class Material {
    public int nShininess=0;
    public double KD=0;
    public double KS=0;

    /**
     * @param nShininess the nShininess to set
     */
    public Material setnShininess(int nShininess)
    {
        this.nShininess = nShininess;
        return this;

    }

    /**
     * @param kD the kD to set
     */
    public Material setKd(double kD)
    {
        KD = kD;
        return this;
    }

    /**
     * @param kS the kS to set
     */
    public Material setKS(Double kS)
    {
        KS = kS;
        return this;
    }
}
