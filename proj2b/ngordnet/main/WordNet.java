package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;

import java.util.*;
import static java.lang.Integer.parseInt;

public class WordNet {
    private Graph graph; // holds each of the synset -> hyponym matchups
    // wrapper for a graph -> a class that contains
    // an instance of another class and adds functions
    private HashMap ids;// holds the id -> word match up
    private HashMap label; // holds all words and the multiple ids they can have in the synset file
    private NGramMap ngm;

    public WordNet(String synset, String hyponym, String wordFile, String countFile) {

        graph = new Graph();
        // build the graph -> add all the edges
        ids = new HashMap();
        label = new HashMap();
        In sreader = new In(synset);
        In hreader = new In(hyponym);
        String[] line;
        ngm = new NGramMap(wordFile, countFile);
        /**
         * In the while loop, the synset file is read, then the hashmap
         * checks if the synset is already in it. In this case, it means there
         * is a word with multiple ids. It creates a new list with all ids already
         * present in the hashmap and adds the new id into the key in the hashmap.
         */
        while (!sreader.isEmpty() && sreader.hasNextLine()) {
            Set reference = new HashSet<Integer>();
            line = sreader.readLine().split(",");
            int id = parseInt(line[0]);
            String word = line[1];
            if (label.containsKey(word)) {
                reference.addAll((Collection<String>) label.get(word));
                reference.add(id);
                label.put(word, reference);
                ids.put(id, word);
            }
            /**
             * Checks if there are multiple words in one synset word. If so,
             * each word in the synset word is analyzed if it exists in the
             * hashmap. Any that exists .
             */
            if (word.contains(" ")) {
                String[] word_split = word.split(" ");
                ids.put(id, word);
                for (String item : word_split) {
                    reference = new HashSet();
                    if (label.containsKey(item)) {
                        reference.addAll((Collection) label.get(item));
                        reference.add(id);
                        label.put(item, reference);
                    } else {
                        reference.add(id);
                        label.put(item, reference);
                    }
                }
            } else {
                ids.put(id, word);
                reference.add(id);
                label.put(word, reference);
            }
        }
        /**
         * The while loop reads hyponyms, splits based on ids and first
         * adds the parent synset node. Then each synset's hyponym is analyzed
         * in a for loop, adds a node, and connects the hyponym as the child of
         * the synset. After, each hyponym of the hyponym is added as the child
         * of that hyponym.
         */
        while (!hreader.isEmpty() && hreader.hasNextLine()) {
            line = hreader.readLine().split(",");
            int id = parseInt(line[0]);
            graph.addNode(id);
            /**
             * adds each word's id list into the node and connects synset to all hyponym node.
             * MUST: add each id's hyponyms in id list into the synset node as well since it's the same term.
             */
            for (int hyp = 1; hyp < line.length; hyp++) {
                int idhyp = parseInt(line[hyp]);
                graph.addNode(idhyp); // gets ids for hyponym and adds
                graph.addEdge(id, idhyp);
            }
        }
    }

    public NGramMap createNGM() {
        return ngm;
    }

    public Set getHyponyms(String item) {
        if (!label.containsKey(item)) {
            return null;
        }
        HashSet i = (HashSet) label.get(item);
        Set everything = new HashSet<>();
        Set returner = new HashSet<>();
        for (Object ind : i) {
            int index = (int) ind;
            everything.addAll(graph.traverse(index));
            for (Object id : everything) {
                int indexer = (int) id;
                String word = (String) ids.get(indexer);
                if (word.contains(" ")) {
                    String[] worder = word.split(" ");
                    for (String z : worder) {
                        returner.add(z);
                    }
                } else {
                    returner.add(word);
                }
            }
        }
        return returner;
    }
}
