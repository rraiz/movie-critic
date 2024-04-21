import pandas as pd

"""
 Filter the data, keeping only tvSeries and keep only movies with a runtime and a start year. Adds the ratings for each movie.
"""
def filter_titles():
    #Read the TSV file
    media = pd.read_csv('input/title.basics.tsv', delimiter='\t')

    #Filter the data, keeping only tvSeries and  keep only movies with a runtime and a start year
    media = media[((media['titleType'] == 'tvSeries') | 
                        ((media['titleType'] == 'movie') & (media['runtimeMinutes']!= '\\N')  & (media['startYear'] != '\\N')))]

    media = media.drop(columns=['originalTitle', 'isAdult']) # Drop the columns originalTitle and isAdult
    media.rename(columns={'primaryTitle': 'title'}, inplace=True) # rename the column primaryTitle to title
    print("Finished filtering only tvSeries and movies.")

    # Open the ratings file, is the imdb ratings for each movie, and filter only the ratings for the movies in the filtered_media DataFrame
    media_ratings  = pd.read_csv('input/title.ratings.tsv', delimiter='\t') 
    media_ratings = media_ratings[media_ratings['tconst'].isin(media['tconst'])]
    media = media.merge(media_ratings, on='tconst', how='inner')
    print("Finished adding ratings to the movies.")


    # Open the principals file, is the cast for each movie, and filter only the cast for the movies in the filtered_media DataFrame
    title_p = pd.read_csv('input/title.principals.tsv', delimiter='\t')
    title_p = title_p.drop(columns=['ordering'])
    title_p = title_p[title_p['tconst'].isin(media['tconst'])]
    # Group by 'tconst' and 'nconst', and then join the characters
    title_p = title_p.groupby(['tconst', 'nconst', 'category', 'job']).agg({
        'characters': lambda x: ', '.join(x)
    }).reset_index()
    print("Finished filtering the cast for the movies.")


    # Open the names file, is the names of the cast, and filter only the names for the cast in the principles DataFrame
    names = pd.read_csv('input/name.basics.tsv', delimiter='\t')
    names = names[names['nconst'].isin(title_p['nconst'])]
    print("Finished filtering the names of the cast.")


    # Save the filtered data
    title_p.to_csv('output/media-cast.tsv', sep='\t', index=False)
    media.to_csv('output/media.tsv', sep='\t', index=False)
    names.to_csv('output/names.tsv', sep='\t', index=False)


if __name__ == '__main__':
    filter_titles()