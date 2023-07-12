# NGordnet


  
![](https://github.com/angela-rodriguezz/NGordnet/blob/main/hyponyms%20gif.gif)


Collecting the historical frequencies of all observed English ngrams[^1] from [Google's Ngram dataset](http://storage.googleapis.com/books/ngrams/books/datasetsv3.html), I programmed the backend for a clone of [Google's Ngram Viewer](https://books.google.com/ngrams/graph?content=global+warming%2Cto+the+moon&year_start=1800&year_end=2019&corpus=en-2019&smoothing=0), which allows for viewers to visualize the relative popularity of words and phrases. Due to scope, this tool only handles 1gram [^2] words and a smaller subset than the full 1gram dataset.

## Data Structures

- **TimeSeries**: A class with a purpose similar to the TreeMap dataset to match each year with the numerical data point of that year. Contains methods to collect all years within the time series `years()` as a list and all the data `data()` as a list.
- **NGramMap**: A class which utilizes the data and organizes it using the TimeSeries we previously contructed. Some methods include `countHistory()` for returning the yearwise count of the word for all years, `totalCountHistory()` for returning the yearwise count of all words in all years, `weightHistory()` for the yearwise relative frequency of the word in all time, and `summedWeightHistory()` that returns the yearwise sum of all relative frequencies in certain words for all time.
- **HistoryTextHandler**: A class that takes in a data type NGordNetQuery which registers the data collection into the website tool and returns the history of the word that the user has typed in years and count respectively.
-  **HistoryHandler**: A class that creates a graph visual of the collected data and registers it as a String that contains a base-64 encoded image of the appropriate plot.


[^1]: Ngram: a sequence of words and phrases
[^2]: 1gram: individual words

