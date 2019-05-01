package byow.Core;

/**
 * Utility class that represents a weighted edge.
 * Created by hug.
 */
public class WeightedEdge<Vertex> {
    private Vertex v;
    private Vertex w;
    private double weight;

    public WeightedEdge(Vertex v, Vertex w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    Vertex from() {
        return v;
    }
    public Vertex to() {
        return w;
    }
    double weight() {
        return weight;
    }
}
