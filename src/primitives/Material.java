package primitives;

public class Material {
    public int nShininess=0;
    public Double3 KD= new Double3(0);
    public Double3 Ks = new Double3(0);
    public Double3 KT= new Double3(0);
    public Double3 KR=new Double3(0);

    /**
     * @param nShininess the nShininess to set
     */
    public Material setShininess(int nShininess)
    {
        this.nShininess = nShininess;
        return this;

    }

    /**
     * @param kD the kD to set
     */
    public Material setKd(double kD)
    {
        KD = new Double3(kD);
        return this;
    }

    /**
     * @param _kS the kS to set
     */
    public Material setKs(double _kS)
    {
        Ks = new Double3(_kS);
        return this;
    }
    public Material setKT(double kT)
    {
        KT = new Double3(kT);
        return this;
    }

    /**
     * @param kR the kR to set
     */
    public Material setKR(double kR)
    {
        KR = new Double3(kR);
        return this;
    }
}
