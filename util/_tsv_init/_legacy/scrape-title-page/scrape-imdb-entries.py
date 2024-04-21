import pandas as pd
import requests
from bs4 import BeautifulSoup
import ujson
import csv
import os

def extract_id(url):
    # Split the URL by '/' and take the second last element (-2) which is the ID
    name_id = url.rstrip('/').split('/')[-1]
    return name_id


def scrape_imdb(url, movie_id):
    """Scrape IMDb page for specific movie data."""

    # User agent to avoid 403 Forbidden error
    headers = {"user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36"}
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        print(f"Failed to fetch MOVIE_ID: {movie_id} from IMDb. Status code: {response.status_code}")
        return {}, [], [], [], []

    soup = BeautifulSoup(response.content, 'html.parser')
    jsonData = soup.find('script', {"type": "application/ld+json"})
    data = ujson.loads(jsonData.string)

    # Default to 'Not Available' if not found (\N is used to represent NULL in TSV files)
    description = data.get('description', '\\N')  # Gets movie description
    coverURL = data.get('image', '\\N')   # Gets movie cover URL


    # Extracting directors's ID and name
    directors = data.get('director', '') 
    director_names = {}
    for d in directors: 
        id_ = extract_id(d['url'])
        director_names[id_] = d['name']


    # Extracting writers's and production companies ID and name
    writers = data.get('creator', '')
    companies = []
    writer_names = {}
    for w in writers:
        if w.get('@type') == 'Person':
            id_ = extract_id(w['url'])
            writer_names[id_] = w['name']

        if w.get('@type') == 'Organization':
           companies.append(extract_id(w['url']))

    # Extracting actor's ID and name
    stars  =  data.get('actor', '')  
    star_names = {}
    for s in stars:
        id_ = extract_id(s['url'])
        star_names[id_] = s['name']


    mpafRating = data.get('contentRating', '\\N')  # Gets mpaf rating
    imdbRating = data.get('aggregateRating', {}).get('ratingValue', '\\N')  # Gets IMDb rating
    votes = data.get('aggregateRating', {}).get('ratingCount', '\\N')  # D GEts number of votes

    # Extracting movie details. Entry will be written to csv file
    movie_details = {
        'description': description,
        'coverURL': coverURL,
        'directors': list(director_names.keys()) if director_names else '\\N',
        'creators': list(writer_names.keys()) if writer_names else '\\N',
        'stars': list(star_names.keys()) if star_names else '\\N',
        'productionCompanies': companies if companies else '\\N',
        'mpafRating': mpafRating, 
        'imdbRating': imdbRating,
        'votes': votes
    }

    # Packing the data into a list of dictionaries for each entity. They will each be written to a separate TSV file
    director_details = []
    for tconst, name in director_names.items(): 
        director_details.append({'tconst': tconst, 'name': name, 'medias': movie_id})

    writer_details = []
    for tconst, name in writer_names.items():
        writer_details.append({'tconst': tconst, 'name': name, 'medias': movie_id})

    star_details = []
    for tconst, name in star_names.items():
        star_details.append({'tconst': tconst, 'name': name, 'medias': movie_id})

    company_details = []
    for tconst in companies:
        company_details.append({'tconst': tconst, 'name': '\\N', 'medias': movie_id})

    return movie_details, director_details, writer_details, star_details, company_details

def main():

    base_path = 'output'  # Define the output directory
    os.makedirs(base_path, exist_ok=True)  # Ensure directory exists

    # Define file paths
    movie_path = os.path.join(base_path, 'movies.tsv')
    directors_path = os.path.join(base_path, 'directors.tsv')
    writers_path = os.path.join(base_path, 'writers.tsv')
    actors_path = os.path.join(base_path, 'actors.tsv')
    companies_path = os.path.join(base_path, 'production_companies.tsv')

    # Open files and write headers if files are new
    with open(movie_path, 'a', newline='', encoding='utf-8') as mf, \
         open(directors_path, 'a', newline='', encoding='utf-8') as df, \
         open(writers_path, 'a', newline='', encoding='utf-8') as wf, \
         open(actors_path, 'a', newline='', encoding='utf-8') as af, \
         open(companies_path, 'a', newline='', encoding='utf-8') as cf:

        # Check if files are empty and write headers
        if os.stat(movie_path).st_size == 0:
            mf.write('tconst\ttitleType\ttitle\tstartYear\truntimeMinutes\tgenres\tdescription\tcoverURL\tdirectors\tcreators\tstars\tproductionCompanies\tmpafRating\timdbRating\tvotes\n')
        if os.stat(directors_path).st_size == 0:
            df.write('tconst\tname\tmedias\n')
        if os.stat(writers_path).st_size == 0:
            wf.write('tconst\tname\tmedias\n')
        if os.stat(actors_path).st_size == 0:
            af.write('tconst\tname\tmedias\n')
        if os.stat(companies_path).st_size == 0:
            cf.write('tconst\tname\tmedias\n')

        # Load the dataset
        movies_df = pd.read_csv('title.filtered_movies.tsv', delimiter='\t', chunksize=1000)
        iteration = 0
        for chunk in movies_df: # Loop through the DataFrame in chunks
            for index, row in chunk.iterrows():  # Loop through each row in the chunk
                imdb_id = row['tconst'] # Get the IMDb ID
                imdb_url = f'https://www.imdb.com/title/{imdb_id}/' # IMDb URL
                imdb_data, directors, writers, stars, companies = scrape_imdb(imdb_url, imdb_id) # Scrape IMDb page and get the data
                combined_row = row.to_dict() # Convert the row to a dictionary 
                combined_row.update(imdb_data)


                # Output to movies/tv-shows TSV file 
                writer = csv.DictWriter(mf, combined_row.keys(), delimiter='\t')
                writer.writerow(combined_row)

                for d in directors:
                    writer = csv.DictWriter(df, d.keys(), delimiter='\t')
                    writer.writerow(d)
                
                for w in writers:
                    writer = csv.DictWriter(wf, w.keys(), delimiter='\t')
                    writer.writerow(w)
                
                for s in stars:
                    writer = csv.DictWriter(af, s.keys(), delimiter='\t')
                    writer.writerow(s)

                for c in companies:
                    writer = csv.DictWriter(cf, c.keys(), delimiter='\t')
                    writer.writerow(c)

                if iteration % 50 == 0:
                    print(f'Processed {iteration} rows')
                iteration += 1


if __name__ == "__main__":
    main()
