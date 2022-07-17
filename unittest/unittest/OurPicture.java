package unittest;


import java.util.ArrayList;
import java.util.List;

//import org.junit.Test;

import lighting.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import kMeans.*;
import scene.Scene;

import static java.awt.Color.YELLOW;


public class OurPicture {

    /**
     * implement the mini project2 part2-use Geometries objects to create the hierarchy
     */
    private final ImageWriter imageWriter = new ImageWriter("teapot", 800, 800);

    @Test
    public void miniPtest1() {


        ImageWriter imageWriter = new ImageWriter("mini1", 200, 200);
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPDistance(1000).setVPDistance(1000).setMultithreading(3).setPrint(0.1).setImageWriter(imageWriter);;

        Scene scene = new Scene("Test scene").setBackGround(Color.BLACK).setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE),new Double3(0.15)));


        Sphere A=new Sphere(new Point(0, 0, 0),20);
        A.setEmission(new Color(255,0,0));
        A.setMaterial(new Material(0.001, 1.5, 0, 0.7,100));
        Sphere B=new Sphere(new Point(60, 0, 400),20);
        B.setEmission(new Color(java.awt.Color.BLUE));
        B.setMaterial(new Material(0.001, 1.5,  0, 0.7,100));
        Sphere C=new Sphere(new Point(30,0,200),20);
        C.setEmission(new Color(56,115,8));
        C.setMaterial(new Material(0.001, 1.5,  0, 0.7,100));
        Sphere D= new Sphere(new Point(80, 15, 0),5);
        D.setEmission(new Color(java.awt.Color.RED));
        D.setMaterial(new Material(0.001, 1.5, 100, 0.7,0.2));
        Sphere E= new Sphere(new Point(90, 15, 0),5);
        E.setEmission(new Color(java.awt.Color.BLUE));
        E.setMaterial(new Material(0.001, 1.5,  100, 0.7,0.2));
        Sphere F= new Sphere(new Point(70, 15, 0),5);
        F.setEmission(new Color(56,115,8));
        F.setMaterial(new Material(0.001, 1.5, 100, 0.2, 0.7));
        Sphere G= new Sphere(new Point(-60, 15, 0),5);
        G.setEmission(new Color(java.awt.Color.RED));
        G.setMaterial(new Material(0.001, 1.5,  100, 0.7,0.2));
        Sphere H= new Sphere(new Point(-70,-5,80),25);
        H.setEmission(new Color(56,115,8));
        H.setMaterial(new Material(0.001, 1.5, 0, 1,100));
        Sphere I= new Sphere(new Point(-40, 10, 0),10);
        I.setEmission(new Color(java.awt.Color.BLUE));
        I.setMaterial(new Material(0.001, 1.5,  100, 0.7,0.2));
        Plane P= (Plane) new Plane(new Point(0, 20, 0), new Point(4, 20, 0),new Point(4,20, 2)).setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material(0.5, 0.5,  0, 0.7,0.5));



        Geometries geos2=new Geometries(A,B,C);
        Geometries geos3=new Geometries(D,E,F);
        Geometries geos4= new Geometries(G,H,I);
        Geometries geos1=new Geometries(geos2,geos3,geos4,E,P);

        scene.geometries.add(geos1);
        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(40, -40, -115),new Vector(-1, 1, 4)));
        scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(0, -10,0)));
       // scene.lights.add(new SpotLight(new Color(700, 400, 400))); //

        camera.setRayTracer(new RayTracerBasic(scene)).renderImage().printGrid(50, new Color(YELLOW));
        camera.writeToImage();

    }

    /**
     * implement the mini project2 part3- using an algorithm (k means) that automatically re-arranges the â€œflatâ€� sceneâ€™s geometries set into geometries hierarchy
     */
    @Test
    public void miniPtest2() {

        ImageWriter imageWriter = new ImageWriter("miniP", 800, 800);

        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
                .setVPDistance(1000).setVPSize(200, 200) //
                .setImageWriter(imageWriter) //
                .setPrint(0.1);

        Scene scene = new Scene("Test scene");

        scene.setBackGround(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

        Sphere A= (Sphere) new Sphere( new Point(0, 0, 0), 20).setMaterial(new Material(0.001, 1.5, 100, 0, 0.7)).setEmission(new Color(255,0,0));
        Sphere B= (Sphere) new Sphere(new Point(60, 0, 400), 20).setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material(0.001, 1.5, 100, 0, 0.7));
        Sphere C= (Sphere) new Sphere(new Point(30,0,200), 20).setEmission(new Color(56,115,8)).setMaterial(new Material(0.001, 1.5, 100, 0, 0.7));

        Sphere D= (Sphere) new Sphere(new Point(80, 15, 0),5).setEmission(new Color(java.awt.Color.RED)).setMaterial(new Material(0.001, 1.5, 100, 0.2, 0.7));
        Sphere E= (Sphere) new Sphere(new Point(90, 15, 0),5).setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material(0.001, 1.5, 100, 0.2, 0.7));
        Sphere F= (Sphere) new Sphere(new Point(70, 15, 0),5).setEmission(new Color(56,115,8)).setMaterial(new Material(0.001, 1.5, 100, 0.2, 0.7));

        Sphere G= (Sphere) new Sphere(new Point(-60, 15, 0),5).setEmission(new Color(java.awt.Color.RED)).setMaterial(new Material(0.001, 1.5, 100, 0.2,0.7));
        Sphere H= (Sphere) new Sphere(new Point(-70,-5,80),25).setEmission(new Color(56,115,8)).setMaterial(new Material(0.001, 1.5, 100, 0, 1));
        Sphere I= (Sphere) new Sphere(new Point(-40, 10, 0),10)
                .setEmission(new Color(java.awt.Color.BLUE))
                .setMaterial(new Material(0.001, 1.5, 100, 0.2, 0.7));

        Plane P= (Plane) new Plane(new Point(0, 20, 0), new Point(4, 20, 0),new Point(4,20, 2)).setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material(0.5, 0.5, 0, 0.5, 0.7));

        List<KPoint> points=new ArrayList<KPoint>();
        points.add(new KPoint(A));
        points.add(new KPoint(B));
        points.add(new KPoint(C));
        points.add(new KPoint(D));
        points.add(new KPoint(E));
        points.add(new KPoint(F));
        points.add(new KPoint(G));
        points.add(new KPoint(H));
        points.add(new KPoint(I));
        points.add(new KPoint(P));


        KMeans kmeans = new KMeans();
        kmeans.init(points);
        kmeans.calculate();
        List<Cluster> clusters=kmeans.getClusters();
        for(Cluster c: clusters) {
            Geometries geos = new Geometries();
            for(KPoint p: c.getPoints()) {
                geos.add(p.getGeometry());
            }
            scene.geometries.add(geos);
        }


        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(40, -40, -115),new Vector(-1, 1, 4),10).setKC(1).setKl(4E-4).setKQ(0.0005));

        scene.lights.add(
                new PointLight(new Color(700, 400, 400), new Point(0, -10,0),10).setKC(1).setKl(0.001).setKQ(0.0005));

        scene.lights.add((new SpotLight(new Color(700, 400, 400), //
                new Point(60, -50, 0), new Vector(0, 0, 1),10).setKC(1).setKl(4E-5).setKQ(0.0005)));



        camera.setRayTracer(new RayTracerBasic(scene))
                .renderImage();//.printGrid(50, new Color(black));
        camera.writeToImage();

    }
}