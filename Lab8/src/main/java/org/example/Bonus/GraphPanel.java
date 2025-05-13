package org.example.Bonus;

import org.example.Class.City;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphPanel extends JPanel {
    private final Map<Integer, City> cities;
    private final Graph<Integer, DefaultEdge> graph;
    private final List<Set<Integer>> biconnectedComponents;

    public GraphPanel(Map<Integer, City> cities,
                      Graph<Integer, DefaultEdge> graph,
                      List<Set<Integer>> biconnectedComponents) {
        this.cities = cities;
        this.graph = graph;
        this.biconnectedComponents = biconnectedComponents;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        g2.setColor(Color.LIGHT_GRAY);
        for (DefaultEdge edge : graph.edgeSet()) {
            Integer src = graph.getEdgeSource(edge);
            Integer tgt = graph.getEdgeTarget(edge);
            City city1 = cities.get(src);
            City city2 = cities.get(tgt);
            if (city1 != null && city2 != null) {
                int x1 = transformX(city1.getLongitude());
                int y1 = transformY(city1.getLatitude());
                int x2 = transformX(city2.getLongitude());
                int y2 = transformY(city2.getLatitude());
                g2.drawLine(x1, y1, x2, y2);
            }
        }


        for (Map.Entry<Integer, City> entry : cities.entrySet()) {
            City city = entry.getValue();
            int x = transformX(city.getLongitude());
            int y = transformY(city.getLatitude());

            g2.setColor(Color.RED);
            g2.fillOval(x - 5, y - 5, 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString(city.getName(), x + 6, y - 6);
        }
    }

    private int transformX(double longitude) {

        return (int) ((longitude + 180) * (getWidth() / 360.0));
    }

    private int transformY(double latitude) {

        return (int) ((90 - latitude) * (getHeight() / 180.0));
    }
}
