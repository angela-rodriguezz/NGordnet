package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private TimeSeries countSeries;
    private HashMap label;
    private TimeSeries wordSeries;

    private In wordList;
    private In countList;
    private String item;
    private String[] list;
    private String[] list2;
    private int countYear;
    private int wordYear;
    private double wordnumTimes;
    private double countnumTimes;
    private String holder;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordList = new In(wordsFilename);
        countList = new In(countsFilename);
        label = new HashMap();
        wordSeries = new TimeSeries();
        countSeries = new TimeSeries();
        boolean notFirst = false;
        while (!wordList.isEmpty() && wordList.hasNextLine()) {
            list2 = wordList.readLine().split("\\t");
            holder = list2[0];
            if (notFirst && !item.equals(holder)) {
                label.put(item, wordSeries);
                wordSeries = new TimeSeries();
                wordYear = parseInt(list2[1]);
                wordnumTimes = parseInt(list2[2]);
                wordSeries.put(wordYear, wordnumTimes);
            } else {
                wordYear = parseInt(list2[1]);
                wordnumTimes = parseInt(list2[2]);
                wordSeries.put(wordYear, wordnumTimes);
            }
            item = holder;
            notFirst = true;
        }
        while (!countList.isEmpty() && countList.hasNextLine()) {
            list = countList.readLine().split(",");
            countYear = parseInt(list[0]);
            countnumTimes = parseLong(list[1]);
            countSeries.put(countYear, countnumTimes);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        if (!this.label.containsKey(word)) {
            return new TimeSeries();
        }
        return (TimeSeries) this.label.get(word);
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!this.label.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(countHistory(word), startYear, endYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return this.countSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries newi = countHistory(word);
        if (!newi.isEmpty()) {
            newi = newi.dividedBy(totalCountHistory());
            return newi;
        }
        return newi;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries newu = countHistory(word, startYear, endYear);
        if (!newu.isEmpty()) {
            newu = newu.dividedBy(totalCountHistory());
            return newu;
        }
        return newu;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        Object[] items = words.toArray();
        TimeSeries measure = new TimeSeries();
        for (int i = 0; i < items.length; i++) {
            if (measure.isEmpty() && !weightHistory((String) items[i]).isEmpty()) {
                measure = weightHistory((String) items[i]);
            } else if (!weightHistory((String) items[i]).isEmpty()) {
                measure = measure.plus(weightHistory((String) items[i]));
            }
        }
        return measure;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        Object[] items = words.toArray();
        TimeSeries measure = new TimeSeries();
        for (int i = 0; i < items.length; i++) {
            if (measure.isEmpty() && !weightHistory((String) items[i], startYear, endYear).isEmpty()) {
                measure = weightHistory((String) items[i], startYear, endYear);
            } else if (!weightHistory((String) items[i], startYear, endYear).isEmpty()) {
                measure = measure.plus(weightHistory((String) items[i], startYear, endYear));
            }
        }
        return measure;
    }
}
