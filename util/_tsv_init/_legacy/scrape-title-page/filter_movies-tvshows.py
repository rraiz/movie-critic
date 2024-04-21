import pandas as pd

#Read the TSV file
df = pd.read_csv('title.basics.tsv', delimiter='\t')

#Filter the data, keeping only tvSeries and movie
filtered_movies = df[df['titleType'].isin(['movie'])]
filtered_tvSeries = df[df['titleType'].isin(['tvSeries'])]

# Drop the columns originalTitle and isAdult
filtered_movies = filtered_movies.drop(columns=['originalTitle', 'isAdult', 'endYear',])
filtered_tvSeries = filtered_tvSeries.drop(columns=['originalTitle', 'isAdult'])

# Drop the rows with runtimeMinutes = '\\N' or startYear = '\\N'
filtered_movies = filtered_movies[(filtered_movies['runtimeMinutes'] != '\\N') & (filtered_movies['startYear'] != '\\N')]
filtered_tvSeries = filtered_tvSeries[(filtered_tvSeries['startYear'] != '\\N')]

# rename the column primaryTitle to title
filtered_movies.rename(columns={'primaryTitle': 'title'}, inplace=True)
filtered_tvSeries.rename(columns={'primaryTitle': 'title'}, inplace=True)


#Save the filtered data to a new CSV file
filtered_movies.to_csv('title.filtered_movies.tsv', sep='\t', index=False)
filtered_tvSeries.to_csv('title.filtered_tvSeries.tsv', sep='\t', index=False)

