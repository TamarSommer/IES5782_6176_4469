package primitives;

public class Material {
    public int nShininess=0;
    public Double3 KD=new Double3(0);
    public Double3 KS= new Double3(0);

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
    public Material setKD(Double3 kD)
    {
        KD = kD;
        return this;
    }

    /**
     * @param kS the kS to set
     */
    public Material setKS(Double3 kS)
    {
        KS = kS;
        return this;
    }
    /**
     * @param kD the kD to set
     */
    public Material setKD(double kD)
    {
        this.KD = new Double3(kD);
        return this;
    }

    /**
     * @param kS the kS to set
     */
    public Material setKS(double kS)
    {
        this.KS = new Double3(kS);
        return this;
    }
}
