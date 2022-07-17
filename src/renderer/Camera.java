package renderer;

import geometries.Intersectable;
import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * camera producing ray through a view plane
 */
public class Camera {


    private Vector _vTo;            // vector pointing towards the scene
    private Vector _vUp;            // vector pointing upwards
    private Vector _vRight;
    private Point _p0;             // camera eye

    private double _distance;       // camera distance from view plane
    private double _width;          // view plane width
    private double _height;         // view plane height
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private int numOfRays;         //for picture improvment
    //mini project 2
    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private double print=0;

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }

    public Point getP0() {
        return _p0;
    }

    public double getDistance() {
        return _distance;
    }

    public double getWidth() {
        return _width;
    }

    public double getHeight() {
        return _height;
    }

    /**
     * set the num of rays for miniproject 1
     * @param numOfRays
     * @return
     */


    public Camera setNumOfRays(int numOfRays)
    {
        if(numOfRays <= 0)
            this.numOfRays=1;
        else
            this.numOfRays = numOfRays;
        return this;
    }


    /**
     * @param p0  origin point in 3D space
     * @param vUp
     * @param vTo
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if (!isZero(vUp.dotProduct(vTo)))
            throw new IllegalArgumentException("vTo and vUp should be orthogonal");
        _p0 = p0;

        _vTo = vTo.normalize();
        _vUp = vUp.normalize();

        _vRight = _vTo.crossProduct(vUp).normalize();
    }

    // chaining methods

    /**
     * set distance between the camera and its view plane
     *
     * @param distance the distnace for the view plane alligator
     * @return instance of camera for chaining
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        _distance = distance;
        return this;
    }

    /**
     * set view plane size
     *
     * @param width  physical width
     * @param height physical height
     * @return
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("Width or height cannot be negative!");
        this._width = width;
        this._height = height;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBasic rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }


    public Camera renderImage() throws MissingResourceException, IllegalArgumentException {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            if (_p0 == null) {
                throw new MissingResourceException("missing resource", Point.class.getName(), "");
            }
            if (_vUp == null || _vRight == null || _vTo == null) {
                throw new MissingResourceException("missing resource", Vector.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            Pixel.initialize(nY, nX, print);
                    if (numOfRays == 1 || numOfRays == 0) {
                        if (threadsCount == 0) {
                            for (int i = 0; i < nY; i++) {
                                for (int j = 0; j < nX; j++) {
                                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
                                }
                            }
                        } else {
                            renderImageThreaded();//calling the impruvment of miniproject2
                        }
                    } else {
                        for (int i = 0; i < nY; i++) {
                            for (int j = 0; j < nX; j++) {

                                if (threadsCount == 0) {
                                    List<Ray> rays = constructBeamThroughPixel(imageWriter.getNx(), imageWriter.getNy(), j, i, numOfRays);
                                    primitives.Color rayColor = rayTracer.traceRay(rays);
                                    imageWriter.writePixel(j, i, rayColor);
                                } else {
                                    while (threadsCount-- > 0) {
                                        new Thread(() -> {
                                            for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                                                imageWriter.writePixel(nX, nY, rayTracer.traceRay(constructBeamThroughPixel(imageWriter.getNx(), imageWriter.getNy(), nX, nY, numOfRays)));
                                            }
                                        }).start();
                                    }
                                }
                            }
                        }
                    }
        } catch (MissingResourceException e) {
        throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    return this;
    }


    /**
    * @param nX the amount of horizontal pixels
    * @param nY the amount of vertical pixels
    * @param j the row of a pixel
    * @param i the column of a pixel
    * @param raysAmount
    * @return list of rays
    */
    public List<Ray> constructBeamThroughPixel (int nX, int nY, int j, int i, int raysAmount )
    {
        int numOfRays = (int)Math.floor(Math.sqrt(raysAmount)); //num of rays in each row or column
        //there is no improvement
        if (numOfRays==1)
            return List.of(constructRay(nX, nY, j, i));
        //Ratio (pixel width & height)
        double Ry= _height/nY;
        double Rx=_width/nX;
        double Yi=(i-(nY-1)/2d)*Ry;
        double Xj=(j-(nX-1)/2d)*Rx;
        double PRy = Ry / numOfRays; //height distance between each ray
        double PRx = Rx / numOfRays; //width distance between each ray
        //The distance between the view plane and the camera cannot be 0
        if (isZero(_distance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        List<Ray> sampleRays = new ArrayList<>();

        //for each pixel in the pixels grid
        for (int i1 = 0; i1 < numOfRays; ++i1)
        {
            for (int j1 = 0; j1< numOfRays; ++j1)
            {
                sampleRays.add(constructRaysThroughPixel(PRy,PRx,Yi, Xj, i1, j1));//add the ray
            }
        }
        sampleRays.add(constructRay(nX, nY, j, i));//add the center  ray to pixel
        return sampleRays;
    }

    /**
      * the function treats each pixel like a little screen of its own and divide it to smaller "pixels".
            * Through each one we construct a ray. This function is similar to ConstructRayThroughPixel.
            * @param Ry height of each grid block we divided the pixel into
    * @param Rx width of each grid block we divided the pixel into
    * @param yi distance of original pixel from (0,0) on Y axis
    * @param xj distance of original pixel from (0,0) on X axis
    * @param j j coordinate of small "pixel"
            * @param i i coordinate of small "pixel"
            * @return beam of rays through pixel
    */
    private Ray constructRaysThroughPixel(double Ry,double Rx, double yi, double xj, int j, int i)
    {
        Point P = _p0.add(_vTo.scale(_distance)); //the center of the screen point

        double ySampleI =  (i *Ry + Ry/2d); //The pixel starting point on the y axis
        double xSampleJ=   (j *Rx + Rx/2d); //The pixel starting point on the x axis

        Point Pij = P; //The point at the pixel through which a beam is fired
        //Moving the point through which a beam is fired on the x axis
        if (!isZero(xSampleJ + xj))
        {
            Pij = Pij.add(_vRight.scale(xSampleJ + xj));
        }
        //Moving the point through which a beam is fired on the y axis
        if (!isZero(ySampleI + yi))
        {
            Pij = Pij.add(_vUp.scale(-ySampleI -yi ));
        }
        Vector Vij = Pij.subtract(_p0);

        return new Ray(_p0,Vij);//create the ray throw the point we calculate here
    }


    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        return pixelColor;
    }

    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();

    }

    public List<Intersectable.GeoPoint> findRay(int nX, int nY, Intersectable intersect) {
        Ray ray;
        List<Intersectable.GeoPoint> result = new LinkedList<>();
        List<Intersectable.GeoPoint> intersectionPoints;

        for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
                ray = constructRay(nX, nY, i, j);
                intersectionPoints = intersect.findGeoIntersections(ray) ;
                if (intersectionPoints != null) {
                    result.addAll(intersectionPoints);
                }
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     *
     * @param nX the amount of horizontal pixels
     * @param nY the amount of vertical pixels
     * @param j the row of a pixel
     * @param i the column of a pixel
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Vector dir;
        Point pCenter, pCenterPixel;
        double ratioY, ratioX, yI, xJ;

        pCenter = _p0.add(_vTo.scale(_distance)); //give us the center of the pixel
        //the size of each pixel
        ratioY = alignZero(_height / nY);
        ratioX = alignZero(_width / nX);

        pCenterPixel = pCenter;
        yI = alignZero(-1 * (i - (nY - 1) / 2d) * ratioY);
        xJ = alignZero((j - (nX - 1) / 2d) * ratioX);
        if (!isZero(xJ)) {
            pCenterPixel = pCenterPixel.add(_vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pCenterPixel = pCenterPixel.add(_vUp.scale(yI));
        }
        dir = pCenterPixel.subtract(_p0);
        return new Ray(dir,_p0);
    }

    /**
     * Calculates the ray that goes through the middle of a pixel i,j on the view
     * plane
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return The ray that goes through the middle of a pixel i,j on the view plane
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        // Image center:
        Point pC = _p0.add(_vTo.scale(this._distance));

        // Ratio:
        double Ry = _height / nY;
        double Rx = _width / nX;

        // Pixel[i,j] center
        double yi = alignZero(-(i - (nY - 1) / 2.0) * Ry);
        double xj = alignZero((j - (nX - 1) / 2.0) * Rx);

        Point pIJ = pC;

        // To avoid a zero vector exception
        if (xj != 0)
            pIJ = pIJ.add(_vRight.scale(xj));
        if (yi != 0)
            pIJ = pIJ.add(_vUp.scale(yi));

        Vector vIJ = pIJ.subtract(this._p0);

        return new Ray( vIJ, _p0);
    }

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Camera setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        Pixel.initialize(nY, nX, print);
        while (threadsCount-- > 0) {
            new Thread(() -> {
                for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone())
                    imageWriter.writePixel(pixel.col, pixel.row, castRay(nX, nY, pixel.col, pixel.row));
            }).start();
        }
        Pixel.waitToFinish();
    }

    /**
     * Set debug printing on
     * @return this Camera
     */
    public Camera setPrint(double print)
    {
        this.print=print;
        return this;
    }
}