package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FileDictionary implements Dictionary {
    private Set<String> words;
    public FileDictionary(String wordFile) throws IOException {
        words = new HashSet<String>();
        try(BufferedReader br = new BufferedReader(new FileReader(wordFile))){
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    public List<String> lookup(String prefix){
        return words.parallelStream().filter(word->word.startsWith(prefix.toLowerCase())).collect(Collectors.toList());
    }
}
