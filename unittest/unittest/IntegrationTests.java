package unittest;

import geometries.*;
import renderer.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * testing the combination of camera's "constructRayThroughPixel"
 * and triangle's, plane's and sphere's "findIntersections"
 *
 * @author esti & efrat
 *
 */
public class IntegrationTests
{
    Camera cam1 = new Camera(new Point(0,0,0), new Vector(0, 0, 1), new Vector(0, -1, 0));//p0=position of camera=(0,0,0)
    Camera cam2 = new Camera(new Point(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));//another camera, different position
    //all tests will be on view plane of 3x3:
    int Nx =3;//number of pixels on tzir x of view plane
    int Ny =3;//number of pixels on tzir y of view plane

    /**
     * help function- to implement the principle of DRY=dont repeat yourself,
     * we added a help func that receives an intersectable geometry, and the wanted camera,
     * calculates the number of intersections and returns it.
     * @param geometry- (intersectable, that implements the method "findIntersections"
     * @param cam
     * @return
     */
    private int constructRayAndCountNumIntersections(Intersectable geometry, Camera cam)
    {
        List<Point> results;//intersections list
        int count = 0;
        for (int i = 0; i < Nx; ++i) //for each index of width
        {
            for (int j = 0; j < Ny; ++j) //for each index of height
            {
                Ray ray = cam.setVpSize(3,3).setVpDistance(1).constructRay(Nx,Ny,j,i);//3x3. create ray through the pixel
                results = geometry.findIntersections(ray);//calculate all intersections of a ray and the geometry
                if (results != null)					  //if there are intersections of the specific ray with the geometry
                    count += results.size();			  //add the number of intersections to the general counter of intersections with the specific geometry.
            }
        }
        return count;//return the number of intersections of all rays with this geometry.
    }

    //==========tests for triangle=========/
    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}
     */
    @Test
    public void constructRayThroughPixelWithTriangle()
    {
        //TC 01: small triangle centered in front of camera- one intersection through the middle ray.
        Triangle tr1 =  new Triangle( new Point(-1, 1, 2),new Point(1, 1, 2),new Point(0, -1, 2));
        int count = constructRayAndCountNumIntersections(tr1,cam2);
        assertEquals(1,count,"too bad");
        System.out.println("count: "+count);

        //TC 02: higher, narrow triangle, placed in front of 2 centered- upper pixels. 2 intersections.
        Triangle tr2 =  new Triangle( new Point(-1, 1, 2),new Point(1, 1, 2),new Point(0, -20, 2));
        count = constructRayAndCountNumIntersections(tr2,cam2);
        assertEquals(2,count,"too bad");
        System.out.println("count: "+count);
    }

    //==========tests for sphere=========/
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}
     */
    @Test
    public void constructRayThroughPixelWithSphere()
    {
        //TC 01: radius=1, 2 intersections from the middle ray
        Sphere sph1 =  new Sphere( new Point(0, 0, 3),1);
        int count = constructRayAndCountNumIntersections(sph1,cam1);
        assertEquals(2,count,"too bad");
        System.out.println("count: "+count);

        //TC 02: radius=2.5, 2 intersections from each ray- total=18
        Sphere sph2 =  new Sphere( new Point(0, 0, 2.5),2.5);
        count = constructRayAndCountNumIntersections(sph2,cam2);
        assertEquals(18,count,"too bad");
        System.out.println("count: "+count);

        //TC 03: radius=2, 2 intersections from all 5 centered rays #, total=10
        Sphere sph3 =  new Sphere( new Point(0, 0, 2),2);
        count = constructRayAndCountNumIntersections(sph3,cam2);
        assertEquals(10,count,"too bad");
        System.out.println("count: "+count);

        //TC 04: radius=4, camera is inside the sphere, 1 intersection from each ray (one side), total- 9
        Sphere sph4 =  new Sphere( new Point(0, 0, 1),4);
        count = constructRayAndCountNumIntersections(sph4,cam2);
        assertEquals(9,count,"too bad");
        System.out.println("count: "+count);

        //TC 05: radius=0.5, sphere behind the camera, no intersections.
        Sphere sph5 =  new Sphere( new Point(0, 0, -1),0.5);
        count = constructRayAndCountNumIntersections(sph5,cam2);
        assertEquals(0,count,"too bad");
        System.out.println("count: "+count);
    }

    //==========tests for plane=========/
    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}
     */
    @Test
    public void constructRayThroughPixelWithPlane()
    {
        //TC 01: plane orthogonal to vTo, in front of camera. 9 intersections- all rays.
        Plane pl1 =  new Plane(new Vector(0,0,1), new Point(0, 0, 7));
        int count = constructRayAndCountNumIntersections(pl1,cam2);
        assertEquals(9,count,"too bad");
        System.out.println("count: "+count);

        //TC 02: plane is tilted but still all rays intersect it. total: 9.
        Plane pl2 =  new Plane( new Vector(0,1,3),new Point(0, 0, 2));
        count = constructRayAndCountNumIntersections(pl2,cam2);
        assertEquals(9,count,"too bad");
        System.out.println("count: "+count);

        //TC 03: plane is so tilted so the rays through the bottom row of pixels- don't reach the plane. total: 6.
        Plane pl3 =  new Plane(new Vector(0,3,1), new Point(0, 0, 2));
        count = constructRayAndCountNumIntersections(pl3,cam2);
        assertEquals(6,count,"too bad");
        System.out.println("count: "+count);
    }

}
