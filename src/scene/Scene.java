package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.AmbientLight;
import primitives.Double3;

import java.util.Arrays;
import java.util.LinkedList;

public class Scene {

    public String name;					//scene's name
    public Color backGround=Color.BLACK;//default color of the background (unless it was changed)
    public AmbientLight ambientLight=new AmbientLight();	//ambient light of the scene's objects
    public Geometries geometries = new Geometries();//the geometries that are in the scene

    /*************** constructor *****************/
    /**
     * restart the name of scene and restarts an empty geometries list.
     * @param _name
     */
    public Scene(String _name) {
        name=_name;
        geometries= new Geometries();//empty list of geometries
    }

    /*************** setters *****************/
    /**
     * @param geometries
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setGeometries(Geometries geometries)
    {
        this.geometries = geometries;
        return this;
    }

    /**
     * @param ambientLight
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setAmbientLight(AmbientLight ambientLight)
    {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * @param backGround
     * @return the scene itself to allow design pattern of builder- to concatenate calls to setters.
     */
    public Scene setBackGround(Color backGround)
    {
        this.backGround = backGround;
        return this;
    }



}
