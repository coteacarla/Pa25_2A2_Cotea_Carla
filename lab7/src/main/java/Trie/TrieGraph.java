package Trie;

import dictionary.FileDictionary;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TrieGraph {
    private Graph<String, DefaultEdge> trie;
    private final String ROOT="";
    private Set<String> wordEnds = new HashSet<>();

    public TrieGraph(String wordFile) throws IOException {
        trie = new DefaultDirectedGraph(DefaultEdge.class);
        trie.addVertex(ROOT);
        buildTrie(wordFile);
    }
    private void buildTrie(String wordFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(wordFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                insertWord(line.trim().toLowerCase());
            }
        }
    }
    private void insertWord(String word) {
        String currentPrefix = ROOT;

        for (int i = 0; i < word.length(); i++) {
            String nextPrefix = word.substring(0, i + 1);
            if (!trie.containsVertex(nextPrefix)) {
                trie.addVertex(nextPrefix);
                trie.addEdge(currentPrefix, nextPrefix);
            }
            currentPrefix = nextPrefix;
        }
        wordEnds.add(word);
    }


    public void displayDFS(){
        System.out.println("DFS");
        DepthFirstIterator<String, DefaultEdge> iter = new DepthFirstIterator<>(trie, ROOT);
        while(iter.hasNext()) {
            String node = iter.next();
            System.out.println(node);
        }
    }

    public List<String> lookup(String prefix) {
        List<String> results = new ArrayList<>();

        if (!trie.containsVertex(prefix)) return results;

        DepthFirstIterator<String, DefaultEdge> dfs = new DepthFirstIterator<>(trie, prefix);
        while (dfs.hasNext()) {
            String node = dfs.next();
            if (wordEnds.contains(node)) {
                results.add(node);
            }
        }

        return results;
    }


    public static void main(String[] args) throws IOException {
        String wordFile = "C:/Users/User/Documents/words.txt";
        FileDictionary fileDict = new FileDictionary(wordFile);
        TrieGraph trieGraph = new TrieGraph(wordFile);

        String prefix = "ca";

        System.out.println("Lookup in FileDictionary:");
        fileDict.lookup(prefix).forEach(System.out::println);

        System.out.println("Lookup in Trie:");
        trieGraph.lookup(prefix).forEach(System.out::println);
    }
}
