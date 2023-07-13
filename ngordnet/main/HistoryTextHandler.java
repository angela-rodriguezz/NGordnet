package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap reference;

    public HistoryTextHandler(NGramMap map) {
        reference = map;
    }

    public String handle(NgordnetQuery q) {
        String message = "";
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            if (!reference.weightHistory(word).isEmpty()) {
                message += word + ": ";
                TimeSeries value = reference.weightHistory(word, startYear, endYear);
                message += value + "\n";
            }
        }
        return message;
    }
}
