In this folder, I retrieved IMDB data, specifically from https://developer.imdb.com/non-commercial-datasets/, and got title.basics.tsv



filtered_movies-tvshows.py:

I got the title.basics.tsv. dataset, and I did basic filtering and stored the movies and tv shows into two seperate TSV's.

title.filtered_movies.tsv and title.filtered_tvSeries.tsv respectively.



scrape-imdb-entries.py:
 
For each unique imdb entry denoted as tt#######, I went onto the imdb website for the unique entry: https://www.imdb.com/title/tt#######

and I scraped: Film ratings (R), IMDb ratings #/10, IMDb votes, description, director(s), writer(s), stars.

I put current known data + scraped data into a full TSV and output will be put into database


Idea worked, but was way too slow. HTTP requests were not feasable to do on 100k+ rows. 