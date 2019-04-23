package byow.Core;

import java.util.List;

public class DynamicKD {

    private Node root;

    private class Node {
        Point myPoint;
        Node left;
        Node right;
        int dim;
        double[] coords;
        Node(Point p, int d) {
            myPoint = p;
            dim = d % 2;
            coords = new double[]{myPoint.getX(), myPoint.getY()};
        }
    }

    private class BestSet {
        Node node;
        double dist;
        BestSet(Node n) {
            node = n;
            dist = Double.POSITIVE_INFINITY;
        }
    }

    public DynamicKD() {
        root = null;
    }
    public Point nearest(double x, double y) {
        BestSet bs = new BestSet(root);
        checkNear(new Point(x, y, null), root, bs);
        return bs.node.myPoint;
    }

    private void checkNear(Point other, Node curr, BestSet bs) {
        if (curr == null) {
            return;
        }
        double[] dims = new double[]{other.getX(), other.getY()};
        if (Point.distance(curr.myPoint, other) < bs.dist) {
            bs.node = curr;
            bs.dist = Point.distance(curr.myPoint, other);
        }
        Node good = null;
        Node bad = null;
        if (dims[curr.dim] >= curr.coords[curr.dim]) {
            good = curr.right;
            bad = curr.left;
        } else {
            good = curr.left;
            bad = curr.right;
        }
        checkNear(other, good, bs);
        if (Math.abs(dims[curr.dim] - curr.coords[curr.dim]) < bs.dist) {
            checkNear(other, bad, bs);
        }
    }
    public void put(Point p) {
        root = add(p, root, 0);
    }
    private Node add(Point point, Node curr, int dim) {
        if (curr == null) {
            curr = new Node(point, dim);
            return curr;
        }
        if (curr.myPoint.equals(point)) {
            return curr;
        }
        double[] dims = new double[]{point.getX(), point.getY()};
        if (dims[dim % 2] >= curr.coords[dim % 2]) {
            curr.right = add(point, curr.right, dim + 1);
        } else {
            curr.left = add(point, curr.left, dim + 1);
        }
        return curr;
    }
}
