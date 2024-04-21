Filter Titles Python Script:

Overview
The filter_titles.py script filters data from TSV files containing information about movies and TV series. It performs the following tasks:

Reads data from input TSV files containing information about titles, ratings, principals (cast), and names.
Filters the data to keep only TV series and movies with a runtime and a start year.
Adds ratings for each movie from the IMDb ratings file.
Filters cast information for the movies.
Filters names of the cast members.
Finally, it saves the filtered data to output TSV files.

Files
filter_titles.py: Python script containing the filtering logic.
input/title.basics.tsv: Input TSV file containing information about titles.
input/title.ratings.tsv: Input TSV file containing IMDb ratings for titles.
input/title.principals.tsv: Input TSV file containing information about principals (cast).
input/name.basics.tsv: Input TSV file containing names of cast members.
output/media.tsv: Output TSV file containing filtered information about titles.
output/media-cast.tsv: Output TSV file containing filtered cast information for titles.
output/names.tsv: Output TSV file containing filtered names of cast members.
