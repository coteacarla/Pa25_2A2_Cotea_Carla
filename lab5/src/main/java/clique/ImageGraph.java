package clique;
import models.Image;

import java.util.*;

public class ImageGraph {
    private int[][] adjacencyMatrix;
    private List<Image> images;

    public ImageGraph(List<Image> images) {
        this.images = images;
        int n = images.size();
        adjacencyMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (shareCommonTag(images.get(i), images.get(j))) {
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;
                }
            }
        }
    }

    private boolean shareCommonTag(Image img1, Image img2) {
        for (String tag : img1.tags()) {
            if (img2.tags().contains(tag)) {
                return true;
            }
        }
        return false;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public List<Image> getImages() {
        return images;
    }
}
