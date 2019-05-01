package byow.Core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TileGraph implements AStarGraph<iPoint>, Serializable {
    @Override
    public List<WeightedEdge<iPoint>> neighbors(iPoint b) {
        List<iPoint> neighbors = b.neighbors();
        List<WeightedEdge<iPoint>> neighborEdges = new ArrayList<>();
        for (iPoint n : neighbors) {
            neighborEdges.add(new WeightedEdge<>(b, n, 1));
        }
        return neighborEdges;
    }

    @Override
    public double estimatedDistanceToGoal(iPoint s, iPoint goal) {
        double dx = (goal.x - s.x);
        double dy = (goal.y - s.y);
        double actual = Math.sqrt((Math.pow(dx, 2) + Math.pow(dy, 2)));
        return (int) actual;
    }


}
