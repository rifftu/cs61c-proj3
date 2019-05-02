package byow.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TileGraph implements AStarGraph<IPoint>, Serializable {
    @Override
    public List<WeightedEdge<IPoint>> neighbors(IPoint b) {
        List<IPoint> neighbors = b.neighbors();
        List<WeightedEdge<IPoint>> neighborEdges = new ArrayList<>();
        for (IPoint n : neighbors) {
            neighborEdges.add(new WeightedEdge<>(b, n, 1));
        }
        return neighborEdges;
    }

    @Override
    public double estimatedDistanceToGoal(IPoint s, IPoint goal) {
        double dx = (goal.x - s.x);
        double dy = (goal.y - s.y);
        double actual = Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
        return (int) actual;
    }


}
