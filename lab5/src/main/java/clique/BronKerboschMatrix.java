package clique;
import models.Image;

import java.util.*;

public class BronKerboschMatrix {
    private List<Set<Image>> maximalCliques = new ArrayList<>();
    private int[][] matrix;
    private List<Image> images;
    private int n;

    public BronKerboschMatrix(int[][] matrix, List<Image> images) {
        this.matrix = matrix;
        this.images = images;
        this.n = images.size();
    }

    public List<Set<Image>> findMaxCliques() {
        Set<Integer> R = new HashSet<>(); // Current clique
        Set<Integer> P = new HashSet<>(); // Candidate vertices
        Set<Integer> X = new HashSet<>(); // Already processed

        for (int i = 0; i < n; i++) {
            P.add(i);
        }

        bronKerbosch(R, P, X);
        return maximalCliques;
    }

    private void bronKerbosch(Set<Integer> R, Set<Integer> P, Set<Integer> X) {
        if (P.isEmpty() && X.isEmpty()) {
            //daca nu mai am candidati si nu a fost nimeni respins ii adaug pe toti
            Set<Image> clique = new HashSet<>();
            for (int index : R) {
                clique.add(images.get(index));
            }
            maximalCliques.add(clique);
            return;
        }

        //copiez sa nu il stric cand iterez
        Set<Integer> Pcopy = new HashSet<>(P);
        for (int v : Pcopy) {
            Set<Integer> neighbors = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if (matrix[v][i] == 1) {
                    neighbors.add(i);
                }
                //adaug in neighbors img cu tag comun
            }

            Set<Integer> newR = new HashSet<>(R);
            newR.add(v);

            Set<Integer> newP = new HashSet<>(P);
            newP.retainAll(neighbors);

            Set<Integer> newX = new HashSet<>(X);
            newX.retainAll(neighbors);

            bronKerbosch(newR, newP, newX);

            P.remove(v);
            //il eliminam ca candidat
            X.add(v);
            //procesam
        }
    }
}
