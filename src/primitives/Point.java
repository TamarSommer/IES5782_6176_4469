package primitives;

import static primitives.Util.isZero;
//point class
//fuhh
public class Point {
    public final Double3 dpoint;
    public Point(double d1,double d2,double d3)
    {
        dpoint = new Double3(d1,d2,d3);
    }
    public Point(Double3 d)
    {
        dpoint = d;
    }
    	public double getD1()
        {
            return dpoint.d1;
        }
        public double getD2()
        {
            return dpoint.d2;
        }
        public double getD3()
        {
            return dpoint.d3;
        }
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if(!(obj instanceof Point))
            return false;
        Point p = (Point) obj;
        //if (this.dpoint.equals(dpoint.ZERO))
        //    return false;
        return this.dpoint.equals((p.dpoint));
    }
    @Override
    public String toString(){
        return String.format("Point: "+ dpoint.toString());
    }
    //substrucing
    public Vector subtract(Point p2){
        double x = this.dpoint.d1-p2.dpoint.d1;
        double y = this.dpoint.d2-p2.dpoint.d2;
        double z = this.dpoint.d3-p2.dpoint.d3;
        Vector newVector = new Vector(x,y,z);
        return newVector;
    }
    //
    //add function
    public 	Point add (Point p2){
        double x = p2.dpoint.d1+this.dpoint.d1;
        double y = p2.dpoint.d2+this.dpoint.d2;
        double z = p2.dpoint.d3+this.dpoint.d3;
        Point newpoint = new Point(x,y,z);
        return newpoint;

    }
    //add function
    public 	Point add (Vector v2){
        double x = v2.dpoint.d1+this.dpoint.d1;
        double y = v2.dpoint.d2+this.dpoint.d2;
        double z = v2.dpoint.d3+this.dpoint.d3;
        Point newpoint = new Point(x,y,z);
        return newpoint;

    }
    //distance function
    public double distanceSquared(Point p2){
        double x = p2.dpoint.d1-this.dpoint.d1;
        double y = p2.dpoint.d2-this.dpoint.d2;
        double z = p2.dpoint.d3-this.dpoint.d3;
        double dis = x*x+y*y+z*z;
        return dis;
    }
    public double distance(Point p)	{
        double  dis = distanceSquared(p);
        dis = Math.sqrt(dis);
        return dis;
    }

}