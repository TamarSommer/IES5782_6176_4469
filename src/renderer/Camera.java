package renderer;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

public class Camera {
    //camera:
    private Point p0;		//camera's position
    private Vector vTo;		//directed forward to camera
    private Vector vUp;		//directed upwards to camera
    private Vector vRight;	//the right direction of camera

    //view plane:
    private double width =0;	//the view plane's width
    private double height = 0;	//the view plane's height
    private double distance = 0;//the distance of the camera from the view plane

    private ImageWriter img;
    private RayTracerBase rtb;

    public Camera setImageWriter(ImageWriter img2)
    {
        img = img2;
        return this;
    }
    public Camera setRayTracerBase(RayTracerBase rtb2)
    {
        rtb= rtb2;
        return this;
    }

    public void renderImage()
    {
        if(p0==null||vTo ==null ||vUp ==null||vRight ==null ||width==0 ||height ==0||distance==0||img ==null||rtb ==null )
            throw new UnsupportedOperationException("Wrong object intilisation");
    }
    public void printGrid(int interval, Color color)
    {

    }


    /* ************* Getters/Setters *******/
    /**
     * @return the position point
     */
    public Point getp0()
    {
        return p0;
    }

    /**
     * @return the forward vector
     */
    public Vector getvTo()
    {
        return vTo;
    }

    /**
     * @return the upwards vector
     */
    public Vector getvUp()
    {
        return vUp;
    }

    /**
     * @return the right-directed vector
     */
    public Vector getvRight()
    {
        return vRight;
    }

    /**
     * @return the dis of canera from v-p
     */
    public double getDistance()
    {
        return distance;
    }
    /**
     * @return the width of v-p
     */
    public double getwidth()
    {
        return width;
    }
    /**
     * @return the height of v-p
     */
    public double getheight()
    {
        return height;
    }

    /* ********* Constructor ***********/
    /**
     * A new Camera
     * @param _p0 camera location
     * @param _vUp- the upwards vector
     * @param _vTo- the forward vector
     */
    public Camera(Point _p0, Vector _vTo, Vector _vUp )
    {
        if(!isZero(_vUp.dotProduct(_vTo)))//if they are orthogonal-the dot product=0. vUp and vTo must be orthogonal
            throw new IllegalArgumentException("Vectors are not orthogonal");

        p0 = new Point(_p0.dPoint);//location of camera
        vTo = _vTo.normalize();	//camera's vectors must be normalized
        vUp = _vUp.normalize();	//camera's vectors must be normalized
        vRight = vTo.crossProduct(vUp).normalize();//we used crossProduct, since vRight is the normal to vTo and vUp plane.
    }
    /**
     * @param _width of view plane
     * @param _height of view plane
     * @return the camera itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Camera setVpSize(double _width, double _height)//set the size of the view-plane
    {
        width=_width;
        height=_height;
        return this;
    }
    /**
     * @param _distance of camera from view plane
     * @return the camera itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Camera setVpDistance(double _distance)//set the distance from the camera to viewPlane
    {
        distance=_distance;
        return this;
    }


    public Ray constructRay(int nX, int nY, int j, int i){
        if (isZero(distance)) //we assume that distance between camera and view plane must be > than 0.
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point Pij= getCenterOfPixel(nX, nY, j, i);//pij is the center of the pixel

        Vector Vij = Pij.subtract(p0);//Vij is the vector from the camera to Pij

        return new Ray(p0, Vij);//create the ray
    }
    public Point getCenterOfPixel(int nX, int nY, int j, int i)
    {
        Point Pc = p0.add(vTo.scale(distance));//Pc is the center point in view plane. exactly in front of camera- vTo.
        //Pc might be in a pixel (if the numbers of pixels are uneven)
        //or between the pixels (if the numbers of pixels are even).
        //we will test the 2 cases in "CameraTests".

        double Ry = height / nY;//the y ratio on view plane
        double Rx = width / nX;	//the x ratio on view plane

        double yi = ((i - nY / 2d) * Ry + Ry / 2d);//calc the y value- the scalar of hazaza on tzir y
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);//calc the x value- the scalar of hazaza on tzir x

        Point Pij = Pc;		//start nevigating from the center point on view plane

        //we only have to do hazaza on the tzir when it's not 0.
        //if the y or x value=0, we don't need to do hazaza in that tzir, vRight (x) or vUp- y.
        if (!isZero(xj))
        {
            Pij = Pij.add(vRight.scale(xj));
        }
        if (!isZero(yi))
        {
            Pij = Pij.add(vUp.scale(-yi)); //we use -yi and not yi since we start counting the rows (i)
            //from up to down (like a matrix), opposite to tzir y in reality and to vUp.
        }
        //now Pij is the center of the wanted pixel
        return Pij;
    }

}
