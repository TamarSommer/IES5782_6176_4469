package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {

    List<Intersectable> _intersectablesList;

    public Geometries() {
        _intersectablesList = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables) {
        _intersectablesList = new LinkedList<>();
        Collections.addAll(_intersectablesList, intersectables);
    }

    public void add(Intersectable... intersectables) {
        Collections.addAll(_intersectablesList, intersectables);
    }

//    public void remove(Intersectable... intersectables) {
//        _intersectablesList.removeAll(List.of(intersectables));
//    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : _intersectablesList) {
            //get intersections points of a particular item from _intersectables
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if (itemPoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itemPoints);
            }
        }
        return result;
    }

    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = null;
        for (Intersectable item : _intersectablesList) {
            //get intersections points of a particular item from _intersectables
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if (itemPoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itemPoints);
            }
        }
        return result;
    }
}





























/*package geometries;

import java.util.List;

import primitives.*;
import java.util.ArrayList;

/**
 * interface of composite- contains a list of geometries that are intersectable.
 * (and not implements all "Geometry interface" because we cannot define a normal to the composite object- it contains a few objects.
 * but it does implement "Intersectable interface" because we want to check the intersections with all the geometries we have.
 * @author efrat & esti
 */
/*public class Geometries extends Intersectable
{
    private List<Intersectable> geometries; //list of geometries- all implement intersactable interface

    /*************** ctors *****************/
    /**
     * default ctor- restart new empty arrayList of geometries
     */
 /*   public Geometries()
    {
        super();
        geometries=new ArrayList<Intersectable>();//we chose arrayList because its faster to get into the items-
        //it takes O(1) and we need to pass on the list of geometries as fast as we can when we get the intersections with them all, for all the rays!
        //its more important than adding geometries to the list or deleting, which take O(n) here in arrayList.
    }

    /**
     * copy-ctor. copies the given array of geometries.
     */
 /*   public Geometries(Intersectable...mygeometries)
    {
        geometries=new ArrayList<Intersectable>();
        for(int i=0;i<mygeometries.length;i++)
            geometries.add(mygeometries[i]);
    }

    /*************** add *****************/
    /**
     * function that adds new geometries to the list
     */
 /*   public void add(Intersectable...mygeometries)
    {
        for(int i=0;i<mygeometries.length;i++)
            geometries.add(mygeometries[i]);
    }

    /**
     * @param ray
     * @return a list of intersections of the ray with all the geometries in the list. all the composite component.
     * we are using the design pattern of composite- here in one function we collect all the intersections of our geometry shapes by using their own "findInresection" function.
     */
    /**
     * @param ray
     * @return a list of intersections of the ray with all the geometries in the list. all the composite component.
     * we are using the design pattern of composite- here in one function we collect all the intersections of our geometry shapes by using their own "findInresection" function.
     */
  /*  @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> intersections=new ArrayList<Point>();//new empty list of points and geometries
        for(int i=0; i<geometries.size(); i++) 				 //move on all the geometries
        {
            if(geometries.get(i).findIntersections(ray)!=null) //if there are intersections to the ray with the specific shape
            {
                for(int j=0; j<geometries.get(i).findIntersections(ray).size(); j++) //move on all the intersections point with this shape
                {
                    intersections.add(geometries.get(i).findIntersections(ray).get(j));//add them to general list of intersections
                }
            }
        }
        if(intersections.size()==0)
            return null;//No intersection at all
        return intersections;//return all the intersections
    }
}*/