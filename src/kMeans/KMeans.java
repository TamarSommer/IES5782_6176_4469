package kMeans;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import primitives.Point;


public class KMeans
{

    //Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 3;
    private int MAX_ITERATIONS = 100;

    private List<KPoint> points;
    private List<Cluster> clusters;

    public KMeans()
    {
        this.points = new ArrayList<KPoint>();
        this.clusters = new ArrayList<Cluster>();
    }

    /**
     * @return the clusters
     */
    public List<Cluster> getClusters()
    {

        return clusters;
    }

    //Initializes the process
    public void init(List<KPoint> points)
    {
        //add Points
        this.points = points;

        //Create Clusters
        //Set random centroids
        for (int i = 0; i < NUM_CLUSTERS; i++)
        {
            Cluster cluster = new Cluster(i);
            KPoint centroid = KPoint.createRandomPoint(-100,100);
            cluster.setCentroid(centroid);
            this.clusters.add(cluster);
        }
    }

    //The process to calculate the K Means, with iterating method.
    public void calculate()
    {
        boolean finish = false;
        int iteration = 0;
        // Add in new data, one at a time, recalculating centroids with each new one.
        while(!finish && iteration < MAX_ITERATIONS)
        {
            //Clear cluster state
            clearClusters();
            List<KPoint> lastCentroids = getCentroids();
            //Assign points to the closer cluster
            assignCluster();
            //Calculate new centroids.
            calculateCentroids();
            iteration++;
            List<KPoint> currentCentroids = getCentroids();
            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++)
            {
                distance += KPoint.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            if(distance == 0) { //if everything is as before - finish
                finish = true;
            }
        }
    }

    private void clearClusters()
    {
        for(Cluster cluster : clusters)
        {
            cluster.clear();
        }
    }

    private List<KPoint> getCentroids()
    {
        List<KPoint> centroids = new ArrayList<KPoint>(NUM_CLUSTERS);
        for(Cluster cluster : clusters)
        {
            KPoint aux = cluster.getCentroid();
            KPoint point = new KPoint(aux.getGeometry());
            centroids.add(point);
        }
        return centroids;
    }

    private void assignCluster()
    {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for(KPoint point : points)
        {
            min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++)
            {
                Cluster c = clusters.get(i);
                distance = KPoint.distance(point, c.getCentroid());
                if(distance < min)

                {
                    min = distance;
                    cluster = i;
                }
            }
            point.setClusterNumber(cluster);
            clusters.get(cluster).addPoint(point);
        }

    }

    private void calculateCentroids()
    {
        for(Cluster cluster : clusters)
        {
            double sumX = 0;
            double sumY = 0;
            double sumZ = 0;
            List<KPoint> listPoints = cluster.getPoints();
            int n_points = listPoints.size();

            for(KPoint point : listPoints) {
                sumX += point.getKPositionPoint().getD1();
                sumY += point.getKPositionPoint().getD2();
                sumZ += point.getKPositionPoint().getD3();}

            KPoint centroid = cluster.getCentroid();
            if(n_points > 0) {
                double newX = sumX / n_points;
                double newY = sumY / n_points;
                double newZ = sumZ / n_points;
                Point newCentroid=new Point(newX,newY,newZ);
                centroid.setPositionPoint(newCentroid);
            }
        }
    }
}