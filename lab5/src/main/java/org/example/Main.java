package org.example;
import models.Image;
import clique.ImageGraph;
import clique.BronKerboschMatrix;
import repository.Repository;
import shell.Shell;
import java.util.Set;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Repository repo=new Repository();
        Shell terminal = new Shell(repo);
        terminal.run();
        /*  //bonus
          List<Image> images = List.of(
                    new Image("img1", "/path1", List.of("cat", "pet")),
                    new Image("img2", "/path2", List.of("dog", "pet")),
                    new Image("img3", "/path3", List.of("cat", "wild")),
                    new Image("img4", "/path4", List.of("nature", "landscape")),
                    new Image("img5", "/path5", List.of("pet", "wild"))
            );

            ImageGraph graphMatrix = new ImageGraph(images);
            int[][] matrix = graphMatrix.getAdjacencyMatrix();
            BronKerboschMatrix bronKerbosch = new BronKerboschMatrix(matrix, images);
            List<Set<Image>> cliques = bronKerbosch.findMaxCliques();
            System.out.println("Maximal Cliques:");
            for (Set<Image> clique : cliques) {
                System.out.println(clique);
            }*/
    }
}
