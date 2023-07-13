package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.ngrams.NGramMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TestHistoryTextHandler {
    private String item;
    private String[] list;
    private int countYear;
    private int wordYear;
    @Test
    public void tester() {
        In wordFile = new In("./data/ngrams/top_14377_words.csv");
        NGramMap ngm = new NGramMap("./data/ngrams/top_14377_words.csv", "./data/ngrams/total_counts.csv");
        String holder;
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("activation");
        list2.add("actions");
        /**
        boolean notFirst = false;
        while (!wordFile.isEmpty()) {
            list = wordFile.readLine().split("\\t");
            holder = list[0];
            if (notFirst && !item.equals(holder)) {
                list2.add(holder);
            }
            item = holder;
            notFirst = true;
        }
         **/
        NgordnetQuery a = new NgordnetQuery(list2, 1663, 1855, 1);
        HistoryTextHandler b = new HistoryTextHandler(ngm);
        System.out.print(b.handle(a));
    }
}
