package ngordnet.ngrams;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    private TimeSeries timer;
    private int starter;
    private int ender;
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        timer = ts;
        starter = startYear;
        ender = endYear;
        for (int i = starter; i <= ender; i++) {
            if (timer.containsKey(i)) {
                this.put(i, timer.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        if (this.size() == 0) {
            return new ArrayList<>();
        }
        List<Integer> list = new ArrayList<>();
        for (int i = this.firstKey(); i <= this.lastKey(); i++) {
            if (this.containsKey(i)) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        if (this.size() == 0) {
            return new ArrayList<>();
        }
        List<Double> list = new ArrayList<>();
        for (int i = this.firstKey(); i <= this.lastKey(); i++) {
            if (this.containsKey(i)) {
                list.add(this.get(i));
            }
        }
        return list;
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    private static int start;
    private static int end;
    private void yearHelper(TimeSeries ts) {
        if (ts.firstKey() < this.firstKey()) {
            start = ts.firstKey();
        } else {
            start = this.firstKey();
        }
        if (ts.lastKey() > this.lastKey()) {
            end = ts.lastKey();
        } else {
            end = this.lastKey();
        }
    }
    public TimeSeries plus(TimeSeries ts) {
        yearHelper(ts);
        TimeSeries user = new TimeSeries();
        for (int i = start; i <= ts.end; i++) {
            if (this.containsKey(i) && ts.containsKey(i)) {
                user.put(i, this.get(i) + ts.get(i));
            } else if (this.containsKey(i) && !ts.containsKey(i)) {
                user.put(i, this.get(i));
            } else if (ts.containsKey(i) && !this.containsKey(i)) {
                user.put(i, ts.get(i));
            }
        }
        return user;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        if (ts.size() == 0 && this.size() == 0) {
            return new TimeSeries();
        }
        if (ts.size() == 0 || this.size() == 0) {
            throw new IllegalArgumentException();
        }
        yearHelper(ts);
        TimeSeries user = new TimeSeries();
        for (int i = start; i <= end; i++) {
            if (this.containsKey(i) && !ts.containsKey(i)) {
                throw new IllegalArgumentException("not in ts");
            } else if (ts.containsKey(i) && this.containsKey(i)) {
                user.put(i, (double) (this.get(i).doubleValue() / ts.get(i).doubleValue()));
            }
        }
        return user;
    }
}
