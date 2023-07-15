package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wn;
    private HashMap<Double, List<String>> holder;
    private TimeSeries count;
    private List<Double> pop;
    private List<String> wordList;
    private int k;
    private NGramMap map;

    public HyponymsHandler(WordNet wn) {
        this.wn = wn;
        map = wn.createNGM();
    }

    @Override
    public String handle(NgordnetQuery q) {
        holder = new HashMap();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        k = q.k();
        pop = new ArrayList<>();
        List<String> hypos = getHyponymList(q, words);
        List<String> returner = new ArrayList<>();
        if (hypos == null) {
            return "[]";
        } else if (k == 0) {
            Collections.sort(hypos);
            return hypos.toString();
        } else {
            for (String word : hypos) {
                wordList = new ArrayList<>();
                count = map.countHistory(word, startYear, endYear);
                double popularity = totalPopularity(count.values());
                if (popularity != 0) {
                    pop.add(popularity);
                    if (holder.containsKey(popularity)) {
                        wordList = holder.get(popularity);
                        wordList.add(word);
                        holder.put(popularity, wordList);

                    } else {
                        wordList.add(word);
                        holder.put(popularity, wordList);
                    }
                }
            }
            Collections.sort(pop, Collections.reverseOrder());
            if (pop.size() == 0) {
                return "[]";
            } else if (pop.size() < k) {
                returner = s(holder, pop, returner, pop.size());
                Collections.sort(returner);
                return returner.toString();
            } else {
                returner = s(holder, pop, returner, k);
                Collections.sort(returner);
                return returner.toString();
            }
        }
    }
    public Double totalPopularity(Collection<Double> values) {
        Double sum = 0.0;
        for (double p : values) {
            sum += p;
        }
        return sum;
    }

    public List<String> s(HashMap<Double, List<String>> holding, List<Double> popper, List<String> returner, int ker) {
        for (int i = 0; i < ker; i++) {
            wordList = holding.get(popper.get(0));
            Collections.sort(wordList, Collections.reverseOrder());
            returner.add(wordList.get(0));
            wordList.remove(0);
            holding.put(popper.get(0), wordList);
            popper.remove(0);
        }
        return returner;
    }

    public List<String> getHyponymList(NgordnetQuery q, List<String> words) {
        HashSet list = null;
        HashSet list2 = null;
        if (!words.isEmpty()) {
            for (String word : words) {
                if (list == null) {
                    list = (HashSet) wn.getHyponyms(word);
                } else {
                    list2 = (HashSet) wn.getHyponyms(word);
                    if (list == null || list2 == null) {
                        list = null;
                        break;
                    } else if (list2.size() > list.size()) {
                        list2.retainAll(list);
                        list = list2;
                    } else {
                        list.retainAll(list2);
                    }
                }
            }
        }
        if (list == null) {
            return null;
        }
        List c = Arrays.asList(list.toArray());
        return c;
    }
}
