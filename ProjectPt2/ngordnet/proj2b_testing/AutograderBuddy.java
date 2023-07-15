package ngordnet.proj2b_testing;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.main.HyponymsHandler;
import ngordnet.main.WordNet;
import ngordnet.ngrams.NGramMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class AutograderBuddy {
    /**
     * Returns a HyponymHandler
     */

    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
        WordNet a = new WordNet(synsetFile, hyponymFile, wordFile, countFile);
        HyponymsHandler b = new HyponymsHandler(a);
        return b;
    }
    @Test
    public void checker(){
        String synsetFile = "data/wordnet/synsets.txt";
        String hyponymFile = "data/wordnet/hyponyms.txt";
        String countFile = "data/ngrams/total_counts.csv";
        String wordFile = "data/ngrams/top_49887_words.csv";
        WordNet a = new WordNet(synsetFile, hyponymFile, wordFile, countFile);
        HyponymsHandler b = new HyponymsHandler(a);
        List c = new ArrayList();
        String lst = "employ";
        String[] lst2 = lst.split(",");
        for (String item : lst2) {
            c.add(item);
        }
        NgordnetQuery d = new NgordnetQuery(c, 1470, 2019, 8);
        String e = b.handle(d);
        System.out.println(e);
    }
}
