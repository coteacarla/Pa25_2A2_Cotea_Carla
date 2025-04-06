package org.example;

import java.util.*;

public class SpanningTreeGenerator {

    private List<Dot> dots;
    private List<Line> lines;
    private List<List<Line>> allSpanningTrees;

    public SpanningTreeGenerator(List<Dot> dots, List<Line> lines) {
        this.dots = dots;
        this.lines = lines;
        this.allSpanningTrees = new ArrayList<>();
    }

    public List<List<Line>> generateSpanningTrees() {
        for (Dot start : dots) {
            Set<Dot> inTree = new HashSet<>();
            PriorityQueue<Line> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(l -> l.cost));
            List<Line> mst = new ArrayList<>();
            inTree.add(start);


            for (Dot dot : dots) {
                if (!dot.equals(start)) {
                    edgeQueue.offer(new Line(start, dot));
                }
            }

            while (!edgeQueue.isEmpty()) {
                Line edge = edgeQueue.poll();
                Dot newDot = null;

                if (!inTree.contains(edge.dot1)) {
                    newDot = edge.dot1;
                } else if (!inTree.contains(edge.dot2)) {
                    newDot = edge.dot2;
                }

                if (newDot != null) {
                    inTree.add(newDot);
                    mst.add(edge);

                    for (Dot dot : dots) {
                        if (!inTree.contains(dot)) {
                            edgeQueue.offer(new Line(newDot, dot));
                        }
                    }
                }
            }
            allSpanningTrees.add(mst);
        }
        return allSpanningTrees;
    }

    public int calculateTreeCost(List<Line> spanningTree) {
        int cost = 0;
        for (Line line : spanningTree) {
            cost += line.cost;
        }
        return cost;
    }


    public List<List<Line>> getSortedSpanningTrees() {
        allSpanningTrees.sort(Comparator.comparingInt(this::calculateTreeCost));
        return allSpanningTrees;
    }
}
