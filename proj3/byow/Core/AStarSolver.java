package byow.Core;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class AStarSolver<Vertex> {

    private AStarGraph<Vertex> g;
    private ArrayHeapMinPQ<Vertex> pq;
    private SolverOutcome outcome;
    private double timeSpent;
    private List<Vertex> solution;
    private int numStates;
    private Map<Vertex, Double> distTo;
    private Map<Vertex, WeightedEdge<Vertex>> edgeTo;
    private Vertex goal;
    private Vertex begin;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        pq = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        solution = new ArrayList<>();
        g = input;
        goal = end;
        begin = start;
        Stopwatch sw = new Stopwatch();
        numStates = 0;
        pq.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.);
        while (pq.size() > 0 && !pq.getSmallest().equals(end) && sw.elapsedTime() < timeout) {
            numStates++;
            for (WeightedEdge<Vertex> e : g.neighbors(pq.removeSmallest())) {
                if (sw.elapsedTime() > timeout) {
                    break;
                }
                relax(e);
            }
        }

        if (pq.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (pq.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            getSolution();
        } else if (sw.elapsedTime() > timeout) {
            outcome = SolverOutcome.TIMEOUT;
        }

        timeSpent = sw.elapsedTime();

    }
    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        if (outcome == SolverOutcome.SOLVED) {
            return distTo.get(goal);
        }
        return 0.;
    }
    public int numStatesExplored() {
        return numStates;
    }
    public double explorationTime() {
        return timeSpent;
    }


    private void relax(WeightedEdge<Vertex> e) {
        Vertex p = e.from();
        Vertex q = e.to();

        double w = e.weight();
        double h = g.estimatedDistanceToGoal(q, goal);
        double toHere = distTo.get(p);
        if (!distTo.containsKey(q) || distTo.get(q) > toHere + w) {
            edgeTo.put(q, e);
            distTo.put(q, toHere + w);
            if (pq.size() != 0 && pq.contains(q)) {
                pq.changePriority(q, toHere + w + h);
            } else {
                pq.add(q, toHere + w + h);
            }
        }

    }
    private void getSolution() {
        Vertex curr = goal;
        solution.add(curr);
        while (!curr.equals(begin) && edgeTo.containsKey(curr)) {
            WeightedEdge<Vertex> e = edgeTo.get(curr);
            curr = e.from();
            solution.add(curr);
        }
        Collections.reverse(solution);
    }
}
