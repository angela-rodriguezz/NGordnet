# NGordnet


  
![](https://github.com/angela-rodriguezz/NGordnet/blob/main/hyponyms%20gif.gif)


Collecting the historical frequencies of all observed English ngrams[^1] from [Google's Ngram dataset](http://storage.googleapis.com/books/ngrams/books/datasetsv3.html), I programmed the backend for a clone of [Google's Ngram Viewer](https://books.google.com/ngrams/graph?content=global+warming%2Cto+the+moon&year_start=1800&year_end=2019&corpus=en-2019&smoothing=0), which allows for viewers to visualize the relative popularity of words and phrases. Due to scope, this tool only handles 1gram [^2] words and a smaller subset than the full 1gram dataset.

## Data Structures

- **TimeSeries**: A class with a purpose similar to the TreeMap dataset to match each year with the numerical data point of that year. Contains methods to collect all years within the time series `years()` as a list and all the data `data()` as a list.
- **NGramMap**: A class which utilizes the data and organizes it using the TimeSeries we previously contructed. Some methods include `countHistory()` for returning the yearwise count of the word for all years, `totalCountHistory()` for returning the yearwise count of all words in all years, `weightHistory()` for the yearwise relative frequency of the word in all time, and `summedWeightHistory()` that returns the yearwise sum of all relative frequencies in certain words for all time.
- **HistoryTextHandler**: A class that takes in a data type NGordNetQuery which registers the data collection into the website tool and returns the history of the word that the user has typed in years and count respectively.
-  **HistoryHandler**: A class that creates a graph visual of the collected data and registers it as a String that contains a base-64 encoded image of the appropriate plot.

## Incorporating WordNet Dataset

WordNet groups words into sets of synonyms called synsets and describes the symantic relationships between these words together.

![.](https://sp23.datastructur.es/materials/proj/proj2b/wordnet-fig1.png)

Each node in the graph is a **synset** which is all groups of words with the same meaning. Words could belong to multiple synsets and therefore could belong to multiple different lists. In order to handle each of these conditions and incorporate them within our visualizer, we must include more data structures for us to gather these words into their proper hyponym datasets.

- **HyponymsHandler**: The implementation for the ___Hyponyms___ button in the visualizor.

## Conclusion
This project introduces software engineering techniques.



[^1]: Ngram: a sequence of words and phrases
[^2]: 1gram: individual words

