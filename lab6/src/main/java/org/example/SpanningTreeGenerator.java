package org.example;

import java.util.*;

public class SpanningTreeGenerator {

    private List<Dot> dots;
    private List<Line> lines;

    public SpanningTreeGenerator(List<Dot> dots, List<Line> lines) {
        this.dots = dots;
        this.lines = lines;
    }

    public List<Line> generateMinimumSpanningTree(Dot start) {
        if (dots == null || dots.size() < 2) {
            return new ArrayList<>();
        }

        Set<Dot> inTree = new HashSet<>();
        PriorityQueue<Line> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(l -> l.cost));
        List<Line> mst = new ArrayList<>();

        inTree.add(start);

        for (Dot dot : dots) {
            if (!dot.equals(start)) {
                Line newLine = new Line(start, dot);
                if (!lines.contains(newLine)) {
                    edgeQueue.offer(newLine);
                }
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
                if (!lines.contains(edge)) {
                    mst.add(edge);
                }

                for (Dot dot : dots) {
                    if (!inTree.contains(dot)) {
                        Line newLine = new Line(newDot, dot);
                        if (!lines.contains(newLine)) {
                            edgeQueue.offer(newLine);
                        }
                    }
                }
            }
        }
        return mst;
    }
}